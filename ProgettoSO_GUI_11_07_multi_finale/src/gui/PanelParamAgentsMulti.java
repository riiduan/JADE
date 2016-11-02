package gui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;

/**
 * JPanel per l'inserimento dei parametri relativi ai partecipanti e agli iniziatori tramite GUI
 *
 */
public class PanelParamAgentsMulti extends JPanel {

	private static final long serialVersionUID = 1L;
	//array relativo ai singoli record per l'inserimento degli iniziatori
	private ArrayList<PanelRecordInitiatorMulti> initiatorAgents;
	//array relativo ai singoli record per l'inserimento dei partecipanti
	private ArrayList<PanelRecordParticipantMulti> participantAgents;
	private JPanel panel;
	private JScrollPane scrollPane;
    //numero attuale dei partecipanti
	private int numCorrPar=1;
    //numero attuale degli iniziatori
	private int numCorrIni=1;
	
	/**
	 * Costruttore
	 */
	public PanelParamAgentsMulti() {
		panel = new JPanel();
        //inserimento del pannello in uno JScrollPane
		scrollPane = new JScrollPane(panel);
        //impostazione del layout e posizionamento dei componenti
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		initiatorAgents = new ArrayList<PanelRecordInitiatorMulti>();
		participantAgents = new ArrayList<PanelRecordParticipantMulti>();
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
		
			PanelRecordInitiatorMulti panelInitiatorTmp = new PanelRecordInitiatorMulti(1);
			initiatorAgents.add(0, panelInitiatorTmp);
			panel.add(panelInitiatorTmp);			
			PanelRecordParticipantMulti panelParticipantTmp = new PanelRecordParticipantMulti(1);
			participantAgents.add(0 ,panelParticipantTmp);
			panel.add(panelParticipantTmp);
			inizializzaEventi();
	}
	
    /**
     * Metodo che aggiunge o rimuove in base al numero di partecipanti attuali
     * e al numero di partecipanti passato come parametro
     * i pannelli per l'inserimento dei parametri relativi ai partecipanti
     * @param numPart nuovo numero di partecipanti
     */
	public void addOrRemoveParticipant(int numPart){
		if(numPart >= numCorrPar){
			for(int i=numCorrPar+1; i <= numPart;i++){
				PanelRecordParticipantMulti panelRecordParticipantTmp = new PanelRecordParticipantMulti(i);
				participantAgents.add(i-1 ,panelRecordParticipantTmp);
				panel.add(panelRecordParticipantTmp);
			}	
		}
		else{
			for(int i=1; (numPart+i) <= numCorrPar; i++){
				panel.remove(participantAgents.get(numCorrPar-i));
				participantAgents.remove(participantAgents.get(numCorrPar-i));
			}			
		}
		numCorrPar=numPart;
		setComboBoxItem();
	}

    /**
     * Metodo che aggiunge o rimuove in base al numero di iniziatori attuali
     * e al numero di iniziatori passato come parametro
     * i pannelli per l'inserimento dei parametri relativi agli iniziatori
     * @param numInitiators nuovo numero di iniziatori
     */
	public void addOrRemoveInitiator(int numInitiators){
		if(numInitiators >= numCorrIni){
			for(int i=numCorrIni+1; i <= numInitiators;i++){
				PanelRecordInitiatorMulti panelRecordInitiatorTmp = new PanelRecordInitiatorMulti(i);
				initiatorAgents.add(i-1 ,panelRecordInitiatorTmp);
			}	
		}
		else{
			for(int i=1; (numInitiators+i) <= numCorrIni; i++){
				panel.remove(initiatorAgents.get(numCorrIni-i));
				initiatorAgents.remove(initiatorAgents.get(numCorrIni-i));
			}			
		}
		numCorrIni=numInitiators;
		panel.removeAll();
		//aggiunge prima tutti gli iniziatori e poi tutti i partecipanti nella GUI
		for(int i=0; i<initiatorAgents.size(); i++)
			panel.add(initiatorAgents.get(i));
		for(int i=0; i<participantAgents.size(); i++)
			panel.add(participantAgents.get(i));
		inizializzaEventi();
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
		return numCorrPar;
	}

	public void setNumCorr(int numCorr) {
		this.numCorrPar = numCorr;
	}	
	
	public ArrayList<PanelRecordInitiatorMulti> getInitiatorAgents() {
		return initiatorAgents;
	}

	public void setInitiatorAgents(ArrayList<PanelRecordInitiatorMulti> initiatorAgents) {
		this.initiatorAgents = initiatorAgents;
	}

	public ArrayList<PanelRecordParticipantMulti> getParticipantAgents() {
		return participantAgents;
	}

	public void setParticipantAgents(ArrayList<PanelRecordParticipantMulti> participantAgents) {
		this.participantAgents = participantAgents;
	}

	public int getNumCorrPar() {
		return numCorrPar;
	}

	public void setNumCorrPar(int numCorrPar) {
		this.numCorrPar = numCorrPar;
	}

	public int getNumCorrIni() {
		return numCorrIni;
	}

	public void setNumCorrIni(int numCorrIni) {
		this.numCorrIni = numCorrIni;
	}

	 /**
     * Metodo per abilitare o disabilitare in base al valore del booleano passato come parametro
     * la possibilita' da parte dell'utente di modificare di modificare i valori dei parametri
     * dei partecipanti
     * @param enable
     */
	public void setEnable(boolean enable){
		for(int i=0; i<this.participantAgents.size(); i++){
			this.participantAgents.get(i).setEnable(enable);
		}
		for(int i=0; i<this.initiatorAgents.size(); i++){
			this.initiatorAgents.get(i).setEnable(enable);
		}
	}
	
	 /**
     * Metodo per ripristinare la GUI
     */
	public void reset(){
		this.addOrRemoveInitiator(0);
		this.addOrRemoveParticipant(0);
		this.initiatorAgents = new ArrayList<PanelRecordInitiatorMulti>();
		PanelRecordInitiatorMulti panelRecordInitiatorTmp = new PanelRecordInitiatorMulti(1);
		initiatorAgents.add(0, panelRecordInitiatorTmp);
		panel.add(panelRecordInitiatorTmp);
		this.numCorrIni = 1;
		this.participantAgents = new ArrayList<PanelRecordParticipantMulti>();
		PanelRecordParticipantMulti panelRecordParticipantTmp = new PanelRecordParticipantMulti(1);
		participantAgents.add(0 ,panelRecordParticipantTmp);
		panel.add(panelRecordParticipantTmp);	
		this.numCorrPar = 1;
		}
	
	public void inizializzaEventi(){
		for(int i=0; i<this.initiatorAgents.size(); i++){
			this.initiatorAgents.get(i).getTextFieldGoodName().addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent arg0) {
					setComboBoxItem();
					}

				@Override
				public void focusLost(FocusEvent arg0) {
					setComboBoxItem();
					}				
			});	
		}
	}
	
	public void setComboBoxItem(){
		for(int j=0; j<participantAgents.size(); j++)
			participantAgents.get(j).getComboBoxGoodName().removeAllItems();
		for(int i=0; i<initiatorAgents.size(); i++){
			for(int j=0; j<participantAgents.size(); j++){
				if(!initiatorAgents.get(i).getTextFieldGoodName().getText().trim().equals("")
						&& !participantAgents.get(j).comboBoxContain(initiatorAgents.get(i).getTextFieldGoodName().getText().trim())){
						participantAgents.get(j).getComboBoxGoodName().addItem((Object) initiatorAgents.get(i).getTextFieldGoodName().getText().trim());
				}
			}
		}
		revalidate();
		repaint();
	}
}
