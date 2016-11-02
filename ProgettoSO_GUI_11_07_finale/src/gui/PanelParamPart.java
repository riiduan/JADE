package gui;

import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;

/**
 * JPanel per l'inserimento dei parametri relativi ai partecipanti tramite GUI
 *
 */
public class PanelParamPart extends JPanel {

    private static final long serialVersionUID = 1L;
//array relativo ai singoli record per l'inserimento dei partecipanti
    private ArrayList<PanelRecordParticipant> participantAgent;
    private JPanel panel;
    private JScrollPane scrollPane;
    //numero attuale dei partecipanti
    private int numCorr=1;

    /**
     * Costruttore
     */
    public PanelParamPart() {
        panel = new JPanel();
        //inserimento del pannello in uno JScrollPane
        scrollPane = new JScrollPane(panel);

        //impostazione del layout e posizionamento dei componenti
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        participantAgent = new ArrayList<PanelRecordParticipant>();
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                      .addGap(26)
                      .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, (int) this.getSize().getWidth(), Short.MAX_VALUE)
                      .addContainerGap())
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup()
                      .addGap(5)
                      .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, (int) this.getSize().getHeight(), Short.MAX_VALUE))
        );
        setLayout(groupLayout);

        for(int i=1; i <= numCorr; i++) {
            PanelRecordParticipant panelCostoArcoTmp = new PanelRecordParticipant(i);
            participantAgent.add(i-1 ,panelCostoArcoTmp);
            panel.add(panelCostoArcoTmp);
        }
    }
    
    /**
     * Metodo che aggiunge o rimuove in base al numero di partecipanti attuali
     * e al numero di partecipanti passato come parametro
     * i pannelli per l'inserimento dei parametri relativi ai partecipanti
     * @param numPart nuovo numero di partecipanti
     */
    public void addOrRemoveParticipant(int numPart) {
        if(numPart >= numCorr) {
            for(int i=numCorr+1; i <= numPart; i++) {
                PanelRecordParticipant panelRecordParticipantTmp = new PanelRecordParticipant(i);
                participantAgent.add(i-1 ,panelRecordParticipantTmp);
                panel.add(panelRecordParticipantTmp);
            }
        } else {
            for(int i=1; (numPart+i) <= numCorr; i++) {
                panel.remove(participantAgent.get(numCorr-i));
                participantAgent.remove(participantAgent.get(numCorr-i));
            }
        }
        numCorr=numPart;
    }

    /**
     * Metodo per abilitare o disabilitare in base al valore del booleano passato come parametro
     * la possibilita' da parte dell'utente di modificare di modificare i valori dei parametri
     * dei partecipanti
     * @param enable
     */
    public void setEnable(boolean enable) {
        for(int i=0; i<this.participantAgent.size(); i++) {
            this.participantAgent.get(i).setEnable(enable);
        }
    }

    /**
     * Metodo per ripristinare la GUI
     */
    public void reset() {
        this.addOrRemoveParticipant(0);
        this.participantAgent = new ArrayList<PanelRecordParticipant>();
        PanelRecordParticipant panelRecordParticipantTmp = new PanelRecordParticipant(1);
        participantAgent.add(0 ,panelRecordParticipantTmp);
        panel.add(panelRecordParticipantTmp);
        this.numCorr = 1;
    }

    //metodi get e set
    public ArrayList<PanelRecordParticipant> getParticipantAgent() {
        return participantAgent;
    }

    public void setParticipantAgent(ArrayList<PanelRecordParticipant> participantAgent) {
        this.participantAgent = participantAgent;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public int getNumCorr() {
        return numCorr;
    }

    public void setNumCorr(int numCorr) {
        this.numCorr = numCorr;
    }

}
