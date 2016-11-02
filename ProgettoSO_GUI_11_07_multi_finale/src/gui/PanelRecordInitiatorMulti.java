package gui;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;

import util.AgentInitiatorMulti;
import java.awt.Dimension;

/**
 * JPanel per l'inserimento dei parametri relativi ai singoli iniziatori
 *
 */
public class PanelRecordInitiatorMulti extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
    //massimo valore per gli spinner della GUI
	public static final int SPIN_MAX_VAL = 9999999;
    //componenti per l'inserimento di parametri e l'interazione con la GUI
	private JTextField textFieldName;
	private JTextField textFieldGoodName;
	private JSpinner spinnerReservePrice;
	private JSpinner spinnerQuantity;
	private JSpinner spinnerMsWait;
	private JSpinner spinnerStartPrice;
	private JSpinner spinnerDif;

	 /**
     * Costruttore che prevede come parametro l'indice dell'iniziatore
     * @param numInitiator indice dell'iniziatore
     */
	public PanelRecordInitiatorMulti(int numInitiator) {
		textFieldName = new JTextField("Initiator" + numInitiator);
		textFieldName.setToolTipText("Nome dell'iniziatore");
		textFieldName.setEditable(false);
		textFieldName.setColumns(10);
		
		JLabel lblName = new JLabel("Nome:");
		
		JLabel lblNomeBene = new JLabel("Nome bene:");
		
		textFieldGoodName = new JTextField();
		textFieldGoodName.setToolTipText("Nome del bene messo all'asta dall'iniziatore");
		textFieldGoodName.setColumns(10);
		
		JLabel lblPrezzoIniziale = new JLabel("Prezzo iniziale:");
		
		spinnerStartPrice = new JSpinner();
		spinnerStartPrice.setToolTipText("Prezzo di partenza per il bene messo all'asta.");
		spinnerStartPrice.setModel(new SpinnerNumberModel(2, 1, SPIN_MAX_VAL, 1));
		spinnerStartPrice.setMinimumSize(new Dimension(28, 20));
		spinnerStartPrice.setPreferredSize(new Dimension(28, 20));
		
		JLabel lblPrezzoDiRiserva = new JLabel("Prezzo di riserva:");
		
		spinnerReservePrice = new JSpinner();
		spinnerReservePrice.setToolTipText("Prezzo sotto il quale l'iniziatore non e' disposto a scendere");
		spinnerReservePrice.setModel(new SpinnerNumberModel(1, 1, SPIN_MAX_VAL, 1));
		spinnerReservePrice.setMinimumSize(new Dimension(28, 20));
		spinnerReservePrice.setPreferredSize(new Dimension(28, 20));
		
		JLabel lblQuantita = new JLabel("Quantita':");
		
		spinnerQuantity = new JSpinner();
		spinnerQuantity.setToolTipText("Quantita' del bene messa all'asta");
		spinnerQuantity.setModel(new SpinnerNumberModel(1, 1, SPIN_MAX_VAL, 1));
		
		JLabel lblMillisecondiAttesa = new JLabel("Millisecondi attesa:");
		
		spinnerMsWait = new JSpinner();
		spinnerMsWait.setToolTipText("Millisecondi di attesa per la ricezione dei messaggi da parte dei Partecipanti dopo una proposta dell'Iniziatore");
		spinnerMsWait.setModel(new SpinnerNumberModel(4000, 0, SPIN_MAX_VAL, 1));
		
		JLabel lblRibasso = new JLabel("Ribasso:");
		
		spinnerDif = new JSpinner();
		spinnerDif.setToolTipText("Decremento del prezzo nel caso non vengano ricevute offerte da parte dei Partecipanti");
		spinnerDif.setModel(new SpinnerNumberModel(1, 1, SPIN_MAX_VAL, 1));
        
		//impostazione del layout e posizionamento dei componenti
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(8)
					.addComponent(lblName)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNomeBene)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldGoodName, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPrezzoIniziale)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinnerStartPrice, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPrezzoDiRiserva)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinnerReservePrice, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblQuantita)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(spinnerQuantity, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblMillisecondiAttesa)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(spinnerMsWait, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblRibasso)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinnerDif, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(35, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblName)
						.addComponent(lblNomeBene)
						.addComponent(textFieldGoodName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPrezzoIniziale)
						.addComponent(spinnerStartPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPrezzoDiRiserva)
						.addComponent(spinnerReservePrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblQuantita)
						.addComponent(spinnerQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMillisecondiAttesa)
						.addComponent(spinnerMsWait, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRibasso)
						.addComponent(spinnerDif, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5))
		);
		setLayout(groupLayout);

	}
		
	public AgentInitiatorMulti getAgentInitiator(){
		AgentInitiatorMulti agent = new AgentInitiatorMulti(this.textFieldGoodName.getText().trim(), (int) this.spinnerStartPrice.getValue(), 
				(int) this.spinnerReservePrice.getValue(), (int) this.spinnerDif.getValue(), 
				(int) this.spinnerQuantity.getValue(), (int) this.spinnerMsWait.getValue(),
				this.textFieldName.getText().trim());
		return agent;
	}
	
	 /**
     * Abilita o disabilita la modifica dei componenti mediante l'interazione dell'utente con la GUI
     * @param enable
     */
	public void setEnable(boolean enable){
		this.spinnerDif.setEnabled(enable);
		this.spinnerStartPrice.setEnabled(enable);
		this.spinnerMsWait.setEnabled(enable);
		this.spinnerQuantity.setEnabled(enable);
		this.spinnerReservePrice.setEnabled(enable);
		this.textFieldGoodName.setEditable(enable);
		this.textFieldName.setEditable(enable);
	}

	public JTextField getTextFieldName() {
		return textFieldName;
	}

	public void setTextFieldName(JTextField textFieldName) {
		this.textFieldName = textFieldName;
	}

	public JTextField getTextFieldGoodName() {
		return textFieldGoodName;
	}

	public void setTextFieldGoodName(JTextField textFieldGoodName) {
		this.textFieldGoodName = textFieldGoodName;
	}

	public JSpinner getSpinnerReservePrice() {
		return spinnerReservePrice;
	}

	public void setSpinnerReservePrice(JSpinner spinnerReservePrice) {
		this.spinnerReservePrice = spinnerReservePrice;
	}

	public JSpinner getSpinnerQuantity() {
		return spinnerQuantity;
	}

	public void setSpinnerQuantity(JSpinner spinnerQuantity) {
		this.spinnerQuantity = spinnerQuantity;
	}

	public JSpinner getSpinnerMsWait() {
		return spinnerMsWait;
	}

	public void setSpinnerMsWait(JSpinner spinnerMsWait) {
		this.spinnerMsWait = spinnerMsWait;
	}

	public JSpinner getSpinnerStartPrice() {
		return spinnerStartPrice;
	}

	public void setSpinnerStartPrice(JSpinner spinnerStartPrice) {
		this.spinnerStartPrice = spinnerStartPrice;
	}

	public JSpinner getSpinnerDif() {
		return spinnerDif;
	}

	public void setSpinnerDif(JSpinner spinnerDif) {
		this.spinnerDif = spinnerDif;
	}
	
	public String getAgentName(){
		return this.textFieldName.getText().trim();
	}
	
	public String getGoodName(){
		return this.textFieldGoodName.getText().trim();
	}
	
	public int getDif(){
		return ((Integer) this.spinnerDif.getValue()).intValue();
	}
	
	public int getQuantity(){
		return ((Integer) this.spinnerQuantity.getValue()).intValue();
	}
	
	public int getReservePrice(){
		return ((Integer) this.spinnerReservePrice.getValue()).intValue();
	}
	
	public int getStartPrice(){
		return ((Integer) this.spinnerStartPrice.getValue()).intValue();
	}
	
	public int getMsWait(){
		return ((Integer) this.spinnerMsWait.getValue()).intValue();
	}
	
	public void setGoodName(String goodName){
		this.textFieldGoodName.setText(goodName);
	}
	
	public void setStartPrice(int startPrice){
		this.spinnerStartPrice.setValue(startPrice);
	}
	
	public void setReservePrice(int reservePrice){
		this.spinnerReservePrice.setValue(reservePrice);
	}
	
	public void setQuantity(int quantity){
		this.spinnerQuantity.setValue(quantity);
	}
	
	public void setDif(int dif){
		this.spinnerDif.setValue(dif);
	}
	
	public void setWaiting(int msWait){
		this.spinnerMsWait.setValue(msWait);
	}
	
	public void setAgentName(String agentName){
		this.textFieldName.setText(agentName);
	}	
}
