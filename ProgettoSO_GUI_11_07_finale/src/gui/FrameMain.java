package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import util.AgentParticipant;

/**
 *	Classe utilizzata per implementare e lanciare la GUI
 *
 */
public class FrameMain extends JFrame {

    private static final long serialVersionUID = 3000030586610341209L;
//	attributi relativi ai menu'
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem nuovo, save, load, esci;
//	utilizato per ripartire in 2 porzioni di dimensione variabile la GUI
    private JSplitPane splitPane;
//	pannelli per l'inserimento dei parametri della simulazione
    private PanelNumParticipant panelNumPart;
    private PanelParamPart panelParamPart;
//	array utilizzato per contenere gli agenti che verranno istanziati nella simulazione
    AgentController[] arrayAgentController = null;

    /**
     * Metodo main
     * @param args al momento non sono utilizzati
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrameMain frame = new FrameMain();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Costruttore del fram principale della GUI
     */
    public FrameMain() {
        //titolo della finestra principale
        setTitle("Dutch Auction");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        //schermo intero
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //impostazione dei menu'
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        menuFile = new JMenu("File");
        menuBar.add(menuFile);
        nuovo = new JMenuItem("Nuova Simulazione");
        save = new JMenuItem("Salva Simulazione");
        load = new JMenuItem("Carica Simulazione");
        esci = new JMenuItem("Esci");
        menuFile.add(nuovo);
        menuFile.add(save);
        menuFile.add(load);
        menuFile.add(esci);
        //istanziazione dei due pannelli che compongono la GUI
        panelNumPart = new PanelNumParticipant();
        panelParamPart = new PanelParamPart();
        //disabilitazione del bottone "Avvia Agenti"
        panelNumPart.getBtnAvviaAgenti().setEnabled(false);
        //inserimento dei due pannelli nelle rispettive due divisioni della GUI
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, new JScrollPane(panelNumPart), panelParamPart);
        splitPane.setPreferredSize(this.getSize());
        splitPane.setDividerLocation(0.1);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
        //aggiunta al frame principale
        this.getContentPane().add(splitPane);
        //inizializzazioni degli eventi per permettere l'interazione dell'utente con gli elementi della GUI
        inizializzaEventi();
    }

    /**
     * Inizializza gli eventi relativi ai componenti della GUI
     */
    private void inizializzaEventi() {
//		quando si preme su esci viene chiuso il programma
        esci.addMouseListener(new MouseAdapter() {
            // chiude il programma
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });

        //per creare una nuova simulazione
        nuovo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panelNumPart.reset();
                panelParamPart.reset();
                revalidate();
                repaint();
            }
        });

        //salva il file relativo alla configurazione della simulazione
        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFileChooser chooserSave = new JFileChooser();
                //i parametri della configurazione vengono salvati in file di tipo JSON con estensione .json
                //pertanto durante il salvataggio saranno visibili solo le cartelle e file con estensione .json
                chooserSave.setFileFilter(new FileNameExtensionFilter(".json", "json"));
                int returnF = chooserSave.showSaveDialog(splitPane);
                //se l'utente dopo aver scelto percorso e nome da l'OK
                if (returnF == JFileChooser.APPROVE_OPTION) {
                    String path = chooserSave.getSelectedFile().getAbsolutePath();

                    try {
                        //file di output con percorso e nome stabiliti dall'utente ed estensione .json
                        FileOutputStream fileText = new FileOutputStream(path + ".json");

                        JSONArray jsonArray = new JSONArray();
                        JSONObject objInitiator = new JSONObject();
                        //inserisce i parametri relativi all'iniziatore in un JSONObject
                        objInitiator.put("GoodName", panelNumPart.getAgentInitiator().getGoodName());
                        objInitiator.put("Dif", panelNumPart.getAgentInitiator().getDif());
                        objInitiator.put("Quantity", panelNumPart.getAgentInitiator().getQuantity());
                        objInitiator.put("ReservePrice", panelNumPart.getAgentInitiator().getReservePrice());
                        objInitiator.put("StartPrice", panelNumPart.getAgentInitiator().getStartPrice());
                        objInitiator.put("Waiting", panelNumPart.getAgentInitiator().getMsWait());
                        //aggiunge il JSONObject al JSONArray
                        jsonArray.add(objInitiator);

                        //inserisce i parametri relativi a ciascun partecipante in un JSONObject,
                        //i quali sono poi aggiunti nel JSONArray
                        for(int i=0; i<panelParamPart.getParticipantAgent().size(); i++) {
                            JSONObject objParticipant = new JSONObject();
                            objParticipant.put("Name", panelParamPart.getParticipantAgent().get(i).getName());
                            objParticipant.put("Quantity", panelParamPart.getParticipantAgent().get(i).getQuantity());
                            objParticipant.put("MaxPrice", panelParamPart.getParticipantAgent().get(i).getMaxPrice());
                            jsonArray.add(objParticipant);
                        }
                        //viene scritto su file il JSONArray contenente i JSONObject relativi
                        //a tutti i partecipanti e iniziatori
                        PrintStream printStream = new PrintStream(fileText);
                        printStream.print(jsonArray.toJSONString());
                        printStream.close();
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

//	carica file relativo alla configurazione di una simulazione
        load.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //Durante la selezione del file da cui caricare la simulazione
                //saranno visibili solo le cartelle e file con estensione .json
                JFileChooser chooserLoad = new JFileChooser();
                chooserLoad.setFileFilter(new FileNameExtensionFilter(".json", "json"));
                int returnF = chooserLoad.showOpenDialog(splitPane);
                //l'utente dopo aver selezionato percorso e nome da l'OK
                if (returnF == JFileChooser.APPROVE_OPTION) {
                    //file che viene caricato
                    File readFile = chooserLoad.getSelectedFile();
                    BufferedReader input;
                    //utilizzati per estrarre e mantenere il contenuto estratto dal file
                    JSONArray jsonArray = new JSONArray();
                    JSONParser parser = new JSONParser();

                    try {
                        //viene letto il contenuto del file e messo in una stringa
                        input = new BufferedReader(new FileReader(readFile));
                        StringBuffer buffer = new StringBuffer();
                        String text;
                        while ((text = input.readLine()) != null)
                            buffer.append(text + "\n");
                        input.close();
                        text = buffer.toString();
                        //dalla stringa viene estratto il corrispondente JSONArray contenuto
                        jsonArray = (JSONArray) parser.parse(text);
                        //controlla che ci siano almeno i parametri relativi a due agenti di cui il primo e'
                        //l'iniziatore, mentre il secondo un partecipante
                        if(jsonArray.size()>1 && ((JSONObject) jsonArray.get(0)).containsKey("ReservePrice")
                        && ((JSONObject) jsonArray.get(1)).containsKey("MaxPrice")) {

                            //carica i dati relativi all'Initiator
                            JSONObject objInitiator = new JSONObject();
                            objInitiator = (JSONObject) jsonArray.get(0);
                            panelNumPart.setGoodName((String) objInitiator.get("GoodName"));
                            panelNumPart.setStartPrice(((Long)objInitiator.get("StartPrice")).intValue());
                            panelNumPart.setReservePrice(((Long)objInitiator.get("ReservePrice")).intValue());
                            panelNumPart.setQuantity(((Long)objInitiator.get("Quantity")).intValue());
                            panelNumPart.setDif(((Long)objInitiator.get("Dif")).intValue());
                            panelNumPart.setWaiting(((Long)objInitiator.get("Waiting")).intValue());
                            panelNumPart.setNumParticipant(jsonArray.size()-1);
                            //aggiorna il numero di partecipanti
                            panelParamPart.addOrRemoveParticipant(jsonArray.size()-1);
                            //carica i dati relativi a tutti i partecipanti
                            for(int i=1; i<jsonArray.size(); i++) {
                                JSONObject objParticipant = new JSONObject();
                                objParticipant =(JSONObject) jsonArray.get(i);
                                panelParamPart.getParticipantAgent().get(i-1).setName((String) objParticipant.get("Name"));
                                panelParamPart.getParticipantAgent().get(i-1).setQuantity(((Long) objParticipant.get("Quantity")).intValue());
                                panelParamPart.getParticipantAgent().get(i-1).setMaxPrice(((Long) objParticipant.get("MaxPrice")).intValue());
                            }
                        } else {
                            //file non valido
                            JOptionPane.showMessageDialog(null, "Il file di input non e' valido."
                                                          , "Errore: File non valido", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (ParseException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        //inizializza l'evento di cambiamento sullo spinner per la selezione del numero dei partecipanti
        panelNumPart.getSpinnerNumParticipant().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                panelParamPart.addOrRemoveParticipant((Integer) panelNumPart.getSpinnerNumParticipant().getValue());
                revalidate();
                repaint();
            }
        });

        //inizializza l'evento sul bottone Avvia Simulazione
        //il quale comportera' l'avvio della simulazione
        panelNumPart.getBtnAvviaSimulazione().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(panelNumPart.getTextField().getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Dare un nome al bene oggetto dell'asta"
                                                  , "Errore: nome bene mancante", JOptionPane.ERROR_MESSAGE);
                } else if(panelNumPart.getAgentInitiator().getStartPrice() < panelNumPart.getAgentInitiator().getReservePrice()) {
                    JOptionPane.showMessageDialog(null, "Il prezzo di partenza dell'asta e' inferiore al prezzo di riserva, "
                                                  + "inserire un prezzo iniziale maggiore o un prezzo di riserva inferiore."
                                                  , "Errore: Prezzo iniziale minore del prezzo di riserva", JOptionPane.ERROR_MESSAGE);
                } else {
                    panelNumPart.getBtnAvviaSimulazione().setEnabled(false);
                    panelNumPart.getBtnAvviaAgenti().setEnabled(true);
                    //disabilitazione dei componenti della GUI per non permettere modifiche
                    panelNumPart.getSpinnerNumParticipant().setEnabled(false);
                    panelNumPart.getSpinnerQuantity().setEnabled(false);
                    panelNumPart.getSpinnerRisPrice().setEnabled(false);
                    panelNumPart.getSpinnerStartPrice().setEnabled(false);
                    panelNumPart.getSpinnerDif().setEnabled(false);
                    panelNumPart.getTextField().setEditable(false);
                    panelNumPart.getSpinnerWaiting().setEnabled(false);
                    panelNumPart.getChckbxManager().setEnabled(false);
                    panelNumPart.getChckbxSniffer().setEnabled(false);
                    panelParamPart.setEnable(false);
                    revalidate();
                    repaint();

                    System.out.println("Simulazione avviata");

                    /*JADE*/
                    //viene avviata la simulazione vera e propria
                    Runtime rt = Runtime.instance();
                    Profile p1 = new ProfileImpl();
                    p1.setParameter("host","127.0.0.1");
                    ContainerController mainContainer = rt.createMainContainer(p1);

                    Profile p2 = new ProfileImpl();
                    if(panelNumPart.getChckbxManager().isSelected())
                        p2.setParameter("gui", "true");

                    ContainerController container = rt.createAgentContainer(p2);

                    int index = 0;
                    int numParticipant = (int) panelNumPart.getSpinnerNumParticipant().getValue();
                    arrayAgentController = new AgentController[numParticipant+1];

                    //crea i partecipanti
                    for(int i=0; i<numParticipant; i++) {
                        AgentParticipant participantAgentTmp = panelParamPart.getParticipantAgent().get(i).getAgentParticipant();
                        Object[] agentPartArgs = new Object[2];
                        //prezzo massimo che il partecipante e' disposto a pagare
                        agentPartArgs[0] = participantAgentTmp.getMaxPrice();

                        //quantita' del bene desidrata
                        agentPartArgs[1] = participantAgentTmp.getQuantity();
                        //crea i partecipanti
                        try {
                            arrayAgentController[index] = container.createNewAgent(participantAgentTmp.getName(), "agents.Participant", agentPartArgs);
                            index++;
                        } catch (StaleProxyException e) {
                            e.printStackTrace();
                        }
                    }
                    //vettore degli oggetti contenente i parametri da passare all'iniziatore
                    Object[] agentArgs = new Object[6];
                    //nome bene
                    agentArgs[0] = panelNumPart.getAgentInitiator().getGoodName();
                    //prezzo di partenza
                    agentArgs[1] = panelNumPart.getAgentInitiator().getStartPrice();
                    //prezzo di riserva
                    agentArgs[2] = panelNumPart.getAgentInitiator().getReservePrice();
                    //decremento del prezzo in caso non siano state ricevute offerte
                    agentArgs[3] = panelNumPart.getAgentInitiator().getDif();
                    //quantita' del bene all'asta
                    agentArgs[4] = panelNumPart.getAgentInitiator().getQuantity();
                    //tempo di attesa per la ricezione di risposte all'offerta da parte dei partecipanti in msec
                    agentArgs[5] = panelNumPart.getAgentInitiator().getMsWait();
                    //crea l'agente iniziatore
                    try {
                        arrayAgentController[index] = container.createNewAgent("Initiator", "agents.Initiator", agentArgs);
                    } catch (StaleProxyException e) {
                        e.printStackTrace();
                    }

                    //aggiunge l'agente di tipo sniffer se e' stata selezionata l'apposita opzione
                    if(panelNumPart.getChckbxSniffer().isSelected()) {

                        /*argomento per lo sniffer che consiste in una stringa data dalla
                         *concatenazione dei nomi degli agenti separati da ; che serve
                         *al fine di sniffarli
                        */
                        String agentsName = new String();
                        for(int j=0; j<arrayAgentController.length; j++) {
                            try {
                                if(j==0)
                                    agentsName = arrayAgentController[j].getName();

                                else
                                    agentsName += ";" + arrayAgentController[j].getName();

                            } catch (StaleProxyException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        Object[] agentsNameObj = new Object[1];
                        agentsNameObj[0] = (Object) agentsName;

                        //istanzia e avvia lo sniffer
                        try {
                            AgentController a = container.createNewAgent("sniffer", "jade.tools.sniffer.Sniffer", agentsNameObj);
                            a.start();
                        } catch (StaleProxyException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        //end sniffer
                    }
                }
            }
        });

        //inizializza l'evento sul bottone Avvia Agenti
        //il quale quando viene premuto avviera' tutti gli agenti
        panelNumPart.getBtnAvviaAgenti().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                panelNumPart.getBtnAvviaAgenti().setEnabled(false);
                panelNumPart.getBtnAvviaSimulazione().setEnabled(true);
                for(int i=0; i<arrayAgentController.length; i++)
                    try {
                        arrayAgentController[i].start();
                    } catch (StaleProxyException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }
        });
    }

}
