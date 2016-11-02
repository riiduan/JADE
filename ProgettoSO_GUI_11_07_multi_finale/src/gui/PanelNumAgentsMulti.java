package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.io.PrintStream;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;

/**
 * JPanel per l'inserimento del numero di iniziatori e di partecipanti
 * con JTextArea per l'output della console sulla GUI, bottoni per per avviare la simulazione
 * e gli agenti, check box per selezionare modalità della simulazione 
 *
 */
public class PanelNumAgentsMulti extends JPanel {

	private static final long serialVersionUID = 1L;
	
    //massimo valore per gli spinner della GUI
	public static final int SPIN_MAX_VAL = 9999999;
	
    //componenti per l'inserimento di parametri e l'interazione con la GUI
	private JSpinner spinnerNumInitiator;
	private JLabel lblNumeroIniziatori;
	private JButton btnAvviaSimulazione;
	private JButton btnAvviaAgenti;
	private JSpinner spinnerNumParticipant;
	private JTextArea textArea;
	private JCheckBox chckbxSniffer;
	private JCheckBox chckbxManager;

	/**
	 * Costruttore
	 */
	public PanelNumAgentsMulti() {
        //JTextArea su cui viene reindirizzato l'output della console
		textArea = new JTextArea(20, 10);
		textArea.setEditable(false);
		PrintStream printStream = new PrintStream(new JTextAreaOutputStream(textArea));
        //reindirizzamento dell'output e degli errori sulla JTextAreaOutputStream
		System.setOut(printStream);
		System.setErr(printStream);
        //aggiunge lo scroll alla JTextArea
		JScrollPane scrollPane = new JScrollPane(textArea);
				
        //componenti per l'inserimento dei parametri relativi alla simulazione
		JLabel labelNumeroParticipant = new JLabel("Numero Participant:");		
		spinnerNumInitiator = new JSpinner();
		spinnerNumInitiator.setToolTipText("Inserire il numero di Iniziatori desiderato");
		spinnerNumInitiator.setModel(new SpinnerNumberModel(1, 1, SPIN_MAX_VAL, 1));
		
		spinnerNumParticipant = new JSpinner();
		spinnerNumParticipant.setModel(new SpinnerNumberModel(1, 1, SPIN_MAX_VAL, 1));
		spinnerNumParticipant.setToolTipText("Inserire il numero di Partecipanti desiderato");
		
		lblNumeroIniziatori = new JLabel("Numero Iniziatori:");
		
		btnAvviaSimulazione = new JButton("Avvia Simulazione");
		btnAvviaSimulazione.setToolTipText("Dopo aver impostato i parametri della simulazione cliccare su questo bottone per procedere all'inizializzazione degli agenti. \r\nNB Al fine di avviare la simulazione e' necessario premere il bottone 'Avvia Agenti'");
		
		btnAvviaAgenti = new JButton("Avvia Agenti");
		btnAvviaAgenti.setToolTipText("E' utilizzabile dopo aver avviato la simulazione, quando cliccato fa partire gli agenti.");
		
		chckbxSniffer = new JCheckBox("Sniffer");
		chckbxSniffer.setToolTipText("Selezionare prima di avviare la simulazione per abilitare l'agente di tipo sniffer");
		
		chckbxManager = new JCheckBox("Agent Management");
		chckbxManager.setToolTipText("Selezionare prima di avviare la simulazione per abilitare il Remote Agent Management GUI");
		
        //impostazione del layout e posizionamento dei componenti
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNumeroIniziatori)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(spinnerNumInitiator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(labelNumeroParticipant)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(spinnerNumParticipant, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(chckbxSniffer)
							.addGap(18)
							.addComponent(chckbxManager))
						.addComponent(btnAvviaSimulazione)
						.addComponent(btnAvviaAgenti))
					.addGap(53)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNumeroIniziatori)
								.addComponent(spinnerNumInitiator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(labelNumeroParticipant)
								.addComponent(spinnerNumParticipant, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(21)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(chckbxSniffer)
								.addComponent(chckbxManager))
							.addGap(23)
							.addComponent(btnAvviaSimulazione)
							.addGap(15)
							.addComponent(btnAvviaAgenti)))
					.addGap(23))
		);
		setLayout(groupLayout);
	}

	public JSpinner getSpinnerNumParticipant() {
		return this.spinnerNumParticipant;
	}

	public void setSpinnerNumParticipant(JSpinner spinnerNumParticipant) {
		this.spinnerNumParticipant = spinnerNumParticipant;
	}

	public JLabel getLblNumeroIniziatori() {
		return lblNumeroIniziatori;
	}

	public void setLblNumeroIniziatori(JLabel lblNumeroIniziatori) {
		this.lblNumeroIniziatori = lblNumeroIniziatori;
	}

	public JButton getBtnAvviaSimulazione() {
		return btnAvviaSimulazione;
	}

	public void setBtnAvviaSimulazione(JButton btnAvviaSimulazione) {
		this.btnAvviaSimulazione = btnAvviaSimulazione;
	}

	public JButton getBtnAvviaAgenti() {
		return btnAvviaAgenti;
	}

	public void setBtnAvviaAgenti(JButton btnAvviaAgenti) {
		this.btnAvviaAgenti = btnAvviaAgenti;
	}

	public JSpinner getSpinnerNumInitiator() {
		return this.spinnerNumInitiator;
	}

	public void setSpinnerNumInitiator(JSpinner spinnerNumInitiator) {
		this.spinnerNumInitiator = spinnerNumInitiator;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public JCheckBox getChckbxSniffer() {
		return chckbxSniffer;
	}

	public void setChckbxSniffer(JCheckBox chckbxSniffer) {
		this.chckbxSniffer = chckbxSniffer;
	}

	public JCheckBox getChckbxManager() {
		return chckbxManager;
	}

	public void setChckbxManager(JCheckBox chckbxManager) {
		this.chckbxManager = chckbxManager;
	}

	 /**
     * Metodo per resettare la GUI
     */
	public void reset(){
		this.textArea.setText("");
		this.btnAvviaAgenti.setEnabled(false);
		this.btnAvviaSimulazione.setEnabled(true);
		this.spinnerNumInitiator.setEnabled(true);
		this.spinnerNumParticipant.setEnabled(true);
		this.chckbxManager.setEnabled(true);
		this.chckbxSniffer.setEnabled(true);
		this.spinnerNumInitiator.setValue(1);
		this.spinnerNumParticipant.setValue(1);
	}
}
