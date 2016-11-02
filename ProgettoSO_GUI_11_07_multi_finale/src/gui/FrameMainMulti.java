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
import util.AgentInitiatorMulti;
import util.AgentParticipantMulti;

/**
 *	Classe utilizzata per implementare e lanciare la GUI
 *
 */
public class FrameMainMulti extends JFrame {

	private static final long serialVersionUID = 3000030586610341209L;
//	attributi relativi ai menu'
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem nuovo, save, load, esci;
//	utilizato per ripartire in 2 porzioni di dimensione variabile la GUI
	private JSplitPane splitPane;
//	pannelli per l'inserimento dei parametri della simulazione
	private PanelNumAgentsMulti panelNumPart;
	private PanelParamAgentsMulti panelParamPart;
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
					FrameMainMulti frame = new FrameMainMulti();
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
	public FrameMainMulti() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//schermo intero
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        //titolo della finestra principale
		this.setTitle("Dutch Auction");
		
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
		panelNumPart = new PanelNumAgentsMulti();
		panelParamPart = new PanelParamAgentsMulti();		
        //disabilitazione del bottone "Avvia Agenti"
		panelNumPart.getBtnAvviaAgenti().setEnabled(false);
        //inserimento dei due pannelli nelle rispettive due divisioni della GUI
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, new JScrollPane(panelNumPart), panelParamPart);
		splitPane.setPreferredSize(this.getSize());
		splitPane.setDividerLocation(0.1);
		splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
		this.getContentPane().add(splitPane);
        //inizializzazioni degli eventi per permettere l'interazione dell'utente con gli elementi della GUI
		inizializzaEventi();
	}
	
	/**
     * Inizializza gli eventi relativi ai componenti della GUI
     */
	private void inizializzaEventi(){
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
			if (returnF == JFileChooser.APPROVE_OPTION){
				String path = chooserSave.getSelectedFile().getAbsolutePath();
				
				try {
                    //file di output con percorso e nome stabiliti dall'utente ed estensione .json
					FileOutputStream fileText = new FileOutputStream(path + ".json");
					JSONArray jsonArray = new JSONArray();
					//inserisce i parametri relativi a ciascun iniziatore in un JSONObject,
                    //i quali sono poi aggiunti nel JSONArray
					for(int i=0; i<panelParamPart.getInitiatorAgents().size(); i++){
						JSONObject objInitiator = new JSONObject();
						objInitiator.put("Name", panelParamPart.getInitiatorAgents().get(i).getAgentName());
						objInitiator.put("GoodName", panelParamPart.getInitiatorAgents().get(i).getGoodName());
						objInitiator.put("Dif", panelParamPart.getInitiatorAgents().get(i).getDif());
						objInitiator.put("Quantity", panelParamPart.getInitiatorAgents().get(i).getQuantity());
						objInitiator.put("ReservePrice", panelParamPart.getInitiatorAgents().get(i).getReservePrice());
						objInitiator.put("StartPrice", panelParamPart.getInitiatorAgents().get(i).getStartPrice());
						objInitiator.put("Waiting", panelParamPart.getInitiatorAgents().get(i).getMsWait());
						jsonArray.add(objInitiator);
				}
					//inserisce i parametri relativi a ciascun partecipante in un JSONObject,
                    //i quali sono poi aggiunti nel JSONArray
						for(int i=0; i<panelParamPart.getParticipantAgents().size(); i++){
							JSONObject objParticipant = new JSONObject();
							objParticipant.put("Name", panelParamPart.getParticipantAgents().get(i).getName());
							objParticipant.put("Quantity", panelParamPart.getParticipantAgents().get(i).getQuantity());
							objParticipant.put("MaxPrice", panelParamPart.getParticipantAgents().get(i).getMaxPrice());
							objParticipant.put("GoodName", panelParamPart.getParticipantAgents().get(i).getGoodName());
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
			if (returnF == JFileChooser.APPROVE_OPTION){
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
			        //controlla che ci siano almeno i parametri relativi a due agenti
					if(jsonArray.size()>1){						
						int numInitiator = 0;
						//conta il numero di iniziatori
						for(int i=0; i<jsonArray.size(); i++){
							JSONObject objAgent = new JSONObject();
							objAgent = (JSONObject) jsonArray.get(i);
							if(objAgent.containsKey("ReservePrice")){
								numInitiator += 1;
							}
						}
						panelNumPart.getSpinnerNumInitiator().setValue(numInitiator);
						panelNumPart.getSpinnerNumParticipant().setValue(jsonArray.size()-numInitiator);
						//aggiorna il numero di iniziatori
						panelParamPart.addOrRemoveInitiator(numInitiator);
						//aggiorna il numero di partecipanti
						panelParamPart.addOrRemoveParticipant(jsonArray.size()-numInitiator);
						//indice degli iniziatori
						int indexIni=0;
						//indice dei partecipanti
						int indexPar=0;
						//scorre tutti gli elementi del jsonArray e quindi tutti gli agenti dell'asta
						for(int i=0; i<jsonArray.size(); i++){
							JSONObject objAgent = new JSONObject();
							objAgent = (JSONObject) jsonArray.get(i);
                            //carica i dati relativi all'Initiator
							if(objAgent.containsKey("ReservePrice")){
							//carica i dati relativi all'Initiator
								panelParamPart.getInitiatorAgents().get(indexIni).setGoodName((String) objAgent.get("GoodName"));
								panelParamPart.getInitiatorAgents().get(indexIni).setStartPrice(((Long)objAgent.get("StartPrice")).intValue());
								panelParamPart.getInitiatorAgents().get(indexIni).setReservePrice(((Long)objAgent.get("ReservePrice")).intValue());
								panelParamPart.getInitiatorAgents().get(indexIni).setQuantity(((Long)objAgent.get("Quantity")).intValue());
								panelParamPart.getInitiatorAgents().get(indexIni).setDif(((Long)objAgent.get("Dif")).intValue());
								panelParamPart.getInitiatorAgents().get(indexIni).setWaiting(((Long)objAgent.get("Waiting")).intValue());
								panelParamPart.getInitiatorAgents().get(indexIni).setAgentName((String) objAgent.get("Name"));
								indexIni++;
								panelParamPart.setComboBoxItem();
							}
							else{							
							//carica i dati relativi a tutti i partecipanti
							panelParamPart.getParticipantAgents().get(indexPar).setName((String) objAgent.get("Name"));
							panelParamPart.getParticipantAgents().get(indexPar).setQuantity(((Long) objAgent.get("Quantity")).intValue());
							panelParamPart.getParticipantAgents().get(indexPar).setMaxPrice(((Long) objAgent.get("MaxPrice")).intValue());
							panelParamPart.getParticipantAgents().get(indexPar).setGoodName((String) objAgent.get("GoodName"));
							indexPar++;
							}
						}
					}else{
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
			
	        //inizializza l'evento di cambiamento sullo spinner per la selezione del numero degli iniziatori
			panelNumPart.getSpinnerNumInitiator().addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					panelParamPart.addOrRemoveInitiator((Integer) panelNumPart.getSpinnerNumInitiator().getValue());
					revalidate();
					repaint();					
				}					
			});
			
			 //inizializza l'evento sul bottone Avvia Simulazione
	        //il quale comportera' l'avvio della simulazione
			panelNumPart.getBtnAvviaSimulazione().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {		
					boolean okGoodName = true;
					boolean okPrice = true;
					//controlla che i campi relativi ai nomi dei beni oggetto dell'asta siano compilati
					for(int k=0; k<panelParamPart.getInitiatorAgents().size(); k++){
						if(panelParamPart.getInitiatorAgents().get(k).getGoodName().trim().equals(""))
							okGoodName = false;
						if(panelParamPart.getInitiatorAgents().get(k).getStartPrice()
								<panelParamPart.getInitiatorAgents().get(k).getReservePrice())
							okPrice = false;
					}
					for(int k=0; k<panelParamPart.getParticipantAgents().size(); k++)
						if(panelParamPart.getParticipantAgents().get(k).getComboBoxGoodName().getSelectedIndex()==-1)
							okGoodName = false;
					
					if(okGoodName==false){
						JOptionPane.showMessageDialog(null, "Inserire un nome per ciascun bene oggetto dell'asta"
								, "Errore: nome bene mancante", JOptionPane.ERROR_MESSAGE);
					}
					else if(okPrice==false){
						JOptionPane.showMessageDialog(null, "Il prezzo di partenza dell'asta e' inferiore al prezzo di riserva, "
								+ "inserire un prezzo iniziale maggiore o un prezzo di riserva inferiore."
								, "Errore: Prezzo iniziale minore del prezzo di riserva", JOptionPane.ERROR_MESSAGE);
					}
						else{						
							panelNumPart.getBtnAvviaSimulazione().setEnabled(false);
						panelNumPart.getBtnAvviaAgenti().setEnabled(true);
	                    //disabilitazione dei componenti della GUI per non permettere modifiche
						panelNumPart.getSpinnerNumParticipant().setEnabled(false);
						panelNumPart.getSpinnerNumInitiator().setEnabled(false);
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
						int numInitiator = (int) panelNumPart.getSpinnerNumInitiator().getValue();
						int numParticipant = (int) panelNumPart.getSpinnerNumParticipant().getValue();
						arrayAgentController = new AgentController[numParticipant+numInitiator];								
						 
						//crea i partecipanti
						for(int i=0; i<numParticipant; i++){
							AgentParticipantMulti participantAgentTmp = panelParamPart.getParticipantAgents().get(i).getAgentParticipant();						
							Object[] agentPartArgs = new Object[3];
							//massimo prezzo
							agentPartArgs[0] = participantAgentTmp.getMaxPrice();
							
							//quantita'
							agentPartArgs[1] = participantAgentTmp.getQuantity();
							
							//nome del bene
							agentPartArgs[2] = participantAgentTmp.getGoodName();
							//crea gli  agenti partecipanti
							try {
								arrayAgentController[index] = container.createNewAgent(participantAgentTmp.getName(), "agents.Participant", agentPartArgs);
								index++;
							} catch (StaleProxyException e) {
								e.printStackTrace();									
							}
						}
						
						//crea gli iniziatori
						for(int i=0; i<numInitiator; i++){
							AgentInitiatorMulti initiatorAgentTmp = panelParamPart.getInitiatorAgents().get(i).getAgentInitiator();						
						//vettore degli oggetti contenente i parametri da passare all'iniziatore								
						Object[] agentArgs = new Object[7];
						//nome bene
						agentArgs[0] = initiatorAgentTmp.getGoodName();
						//prezzo iniziale
						agentArgs[1] = initiatorAgentTmp.getStartPrice();
						//prezzo di riserva
						agentArgs[2] = initiatorAgentTmp.getReservePrice();
						//decremento del prezzo se non ci sono offerte
						agentArgs[3] = initiatorAgentTmp.getDif();
						//quantita'
						agentArgs[4] = initiatorAgentTmp.getQuantity();
						//tempo di attesa per le offerte in msec
						agentArgs[5] = initiatorAgentTmp.getMsWait();
						//nome dell'iniziatore
						agentArgs[6] = initiatorAgentTmp.getAgentName();						
						//crea gli agenti iniziatori
						try {
							arrayAgentController[index] = container.createNewAgent(initiatorAgentTmp.getAgentName(), "agents.Initiator", agentArgs);
							index++;
						} catch (StaleProxyException e) {
							e.printStackTrace();
						}
						}
						//aggiunge l'agente di tipo sniffer se e' stata selezionata l'apposita opzione
						if(panelNumPart.getChckbxSniffer().isSelected()){
							
							/*argomento per lo sniffer che consiste in una stringa data dalla
							 *concatenazione dei nomi degli agenti separati da ; che serve
							 *al fine di sniffarli
							*/
							String agentsName = new String();
							for(int j=0; j<arrayAgentController.length; j++){
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
						System.out.println("Agenti avviati");
					}					
			});		
	}

}
