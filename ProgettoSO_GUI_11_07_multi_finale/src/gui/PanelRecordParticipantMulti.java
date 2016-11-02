package gui;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import util.AgentParticipantMulti;
import javax.swing.JComboBox;

/**
 * JPanel per l'inserimento dei parametri relativi ai singoli partecipanti
 *
 */
public class PanelRecordParticipantMulti extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
    //massimo valore per gli spinner della GUI
	public static final int SPIN_MAX_VAL = 9999999;
    //componenti per l'inserimento di parametri e l'interazione con la GUI
	private JTextField textFieldName;
	private JSpinner spinnerPrice;
	private JSpinner spinnerQuantity;
	private JComboBox comboBoxGoodName;

	 /**
     * Costruttore che prevede come parametro l'indice del partecipante
     * @param numParticipant indice del partecipante
     */
	public PanelRecordParticipantMulti(int numParticipant) {
		
		spinnerPrice = new JSpinner();		
		spinnerPrice.setToolTipText("Prezzo massimo a cui e' disposto a pagare il bene all'asta");
		spinnerPrice.setModel(new SpinnerNumberModel(1, 1, SPIN_MAX_VAL, 1));
		textFieldName = new JTextField("Participant"+numParticipant);
		textFieldName.setToolTipText("Nome del Partecipante all'asta");
		textFieldName.setEditable(false);
		textFieldName.setColumns(10);
		
		JLabel lblName = new JLabel("Nome:");
		
		JLabel lblMaxPrice = new JLabel("Prezzo Massimo:");
		
		JLabel lblQuantity = new JLabel("Quantita':");
		
		spinnerQuantity = new JSpinner();
		spinnerQuantity.setToolTipText("Quantita' del bene desiderata");
		spinnerQuantity.setModel(new SpinnerNumberModel(1, 1, SPIN_MAX_VAL, 1));
		
		comboBoxGoodName = new JComboBox();
		comboBoxGoodName.setToolTipText("Nome del bene desiderato");
		
		JLabel lblNomeBene = new JLabel("Nome bene:");
		
        //impostazione del layout e posizionamento dei componenti
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(8)
					.addComponent(lblName)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblMaxPrice)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(spinnerPrice, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblQuantity)
					.addGap(18)
					.addComponent(spinnerQuantity, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNomeBene)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBoxGoodName, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(41, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMaxPrice)
						.addComponent(spinnerQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblName)
						.addComponent(spinnerPrice, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblQuantity)
						.addComponent(lblNomeBene)
						.addComponent(comboBoxGoodName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5))
		);
		setLayout(groupLayout);

	}

	public int getQuantity(){
		return (int) this.spinnerQuantity.getValue();
	}
	
	public void setQuantity(int quantity){
		this.spinnerQuantity.setValue(quantity);
	}
	
	public String getName(){
		return this.textFieldName.getText();
	}
	
	public void setName(String name){
		this.textFieldName.setText(name);;
	}
	
	public int getMaxPrice(){
		return (int) this.spinnerPrice.getValue();
	}
	
	public void setMaxPrice(int maxPrice){
		this.spinnerPrice.setValue(maxPrice);
	}
	
    /**
    *
    * @return l'agente di tipo partecipante con gli atributi inizializzati ai valori specificati
    * dall'utente mediante la GUI
    */
	public AgentParticipantMulti getAgentParticipant(){
		AgentParticipantMulti agent = new AgentParticipantMulti(textFieldName.getText(), 
				(Integer) spinnerPrice.getValue(), (Integer) this.spinnerQuantity.getValue(), 
				(String) this.comboBoxGoodName.getSelectedItem());
		return agent;
	}
	
	public JTextField getTextField() {
		return textFieldName;
	}

	public void setTextField(JTextField textField) {
		this.textFieldName = textField;
	}

	public JSpinner getSpinner() {
		return spinnerPrice;
	}

	public void setSpinner(JSpinner spinner) {
		this.spinnerPrice = spinner;
	}

	public JTextField getTextFieldName() {
		return textFieldName;
	}

	public void setTextFieldName(JTextField textFieldName) {
		this.textFieldName = textFieldName;
	}

	public JSpinner getSpinnerPrice() {
		return spinnerPrice;
	}

	public void setSpinnerPrice(JSpinner spinnerPrice) {
		this.spinnerPrice = spinnerPrice;
	}

	public JSpinner getSpinnerQuantity() {
		return spinnerQuantity;
	}

	public void setSpinnerQuantity(JSpinner spinnerQuantity) {
		this.spinnerQuantity = spinnerQuantity;
	}
	
	 /**
     * Abilita o disabilita la modifica dei componenti mediante l'interazione dell'utente con la GUI
     * @param enable
     */
	public void setEnable(boolean enable){
		this.spinnerPrice.setEnabled(enable);
		this.spinnerQuantity.setEnabled(enable);
		this.textFieldName.setEditable(enable);
		this.comboBoxGoodName.setEnabled(enable);
	}

	public JComboBox getComboBoxGoodName() {
		return comboBoxGoodName;
	}

	public void setComboBoxGoodName(JComboBox comboBoxGoodName) {
		this.comboBoxGoodName = comboBoxGoodName;
	}
	
	public void setGoodName(String goodName){
		this.comboBoxGoodName.setSelectedItem(goodName);
	}
	
	public String getGoodName(){
		return (String) this.comboBoxGoodName.getSelectedItem();
	}
	
	/**
	 * Verifica se e' presente o meno il parametro presente nella comboBox relativa al nome del bene
	 * @param value il valore da controllare se presente o meno nella comboBox relativa al nome del bene
	 * @return true se il valore e' presente nella comboBox relativa al nome del bene
	 */
	public boolean comboBoxContain(String value){
		boolean bool = false;
		for(int i=0; i<this.comboBoxGoodName.getItemCount(); i++){
			if(this.comboBoxGoodName.getItemAt(i).equals(value))
				bool = true;
		}
		return bool;
	}
}
