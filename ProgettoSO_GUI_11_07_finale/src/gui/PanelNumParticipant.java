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

import util.AgentInitiator;

import javax.swing.JTextField;
import javax.swing.JCheckBox;

/**
 * JPanel per l'inserimento dei parametri relativi all'iniziatore, al numero di partecipanti
 * con JTextArea per l'output della console sulla GUI
 *
 */
public class PanelNumParticipant extends JPanel {

    private static final long serialVersionUID = 1L;

    //massimo valore per gli spinner della GUI
    public static final int SPIN_MAX_VAL = 9999999;

    //componenti per l'inserimento di parametri e l'interazione con la GUI
    private JSpinner spinnerNumParticipant;
    private JTextField textField;
    private JLabel lblQuantitaBeneAllasta;
    private JSpinner spinnerStartPrice;
    private JLabel lblPrezzoDiPartenza;
    private JSpinner spinnerRisPrice;
    private JLabel lblPrezzoDiRiserva;
    private JButton btnAvviaSimulazione;
    private JButton btnAvviaAgenti;
    private JSpinner spinnerQuantity;
    private JSpinner spinnerDif;
    private JLabel lblRibasso;
    private JTextArea textArea;
    private JCheckBox chckbxSniffer;
    private JCheckBox chckbxManager;
    private JSpinner spinnerWaiting;

    /**
     * Costruttore
     */
    public PanelNumParticipant() {
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
        spinnerNumParticipant = new JSpinner();
        spinnerNumParticipant.setToolTipText("Inserire il numero di Participant all'asta");
        spinnerNumParticipant.setModel(new SpinnerNumberModel(1, 1, SPIN_MAX_VAL, 1));

        textField = new JTextField();
        textField.setToolTipText("Nome del bene messo all'asta dall'iniziatore");
        textField.setColumns(10);

        JLabel lblNomeBeneAllasta = new JLabel("Nome bene all'asta:");

        spinnerQuantity = new JSpinner();
        spinnerQuantity.setModel(new SpinnerNumberModel(1, 1, SPIN_MAX_VAL, 1));
        spinnerQuantity.setToolTipText("Quantita' del bene messa all'asta");

        lblQuantitaBeneAllasta = new JLabel("Quantita' bene all'asta:");

        spinnerStartPrice = new JSpinner();
        spinnerStartPrice.setModel(new SpinnerNumberModel(2, 1, SPIN_MAX_VAL, 1));
        spinnerStartPrice.setToolTipText("Prezzo di partenza per il bene messo all'asta.");

        lblPrezzoDiPartenza = new JLabel("Prezzo di partenza:");

        spinnerRisPrice = new JSpinner();
        spinnerRisPrice.setModel(new SpinnerNumberModel(1, 1, SPIN_MAX_VAL, 1));
        spinnerRisPrice.setToolTipText("Prezzo sotto il quale l'iniziatore non e' disposto a scendere");

        lblPrezzoDiRiserva = new JLabel("Prezzo di riserva:");

        btnAvviaSimulazione = new JButton("Avvia Simulazione");
        btnAvviaSimulazione.setToolTipText("Dopo aver impostato i parametri della simulazione cliccare su questo bottone per procedere all'inizializzazione degli agenti. \r\nNB Al fine di avviare la simulazione e' necessario premere il bottone 'Avvia Agenti'");

        btnAvviaAgenti = new JButton("Avvia Agenti");
        btnAvviaAgenti.setToolTipText("E' utilizzabile dopo aver avviato la simulazione, quando cliccato fa partire gli agenti.");

        spinnerDif = new JSpinner();
        spinnerDif.setToolTipText("Decremento del prezzo nel caso non vengano ricevute offerte da parte dei Partecipanti");
        spinnerDif.setModel(new SpinnerNumberModel(1, 1, SPIN_MAX_VAL, 1));

        lblRibasso = new JLabel("Ribasso:");

        chckbxSniffer = new JCheckBox("Sniffer");
        chckbxSniffer.setToolTipText("Selezionare prima di avviare la simulazione per abilitare l'agente di tipo sniffer");

        chckbxManager = new JCheckBox("Agent Management");
        chckbxManager.setToolTipText("Selezionare prima di avviare la simulazione per abilitare il Remote Agent Management GUI");

        JLabel lblTempoDiAttesa = new JLabel("Millisecondi di attesa:");

        spinnerWaiting = new JSpinner();
        spinnerWaiting.setToolTipText("Millisecondi di attesa per la ricezione dei messaggi da parte dei Partecipanti dopo una proposta dell'Iniziatore");
        spinnerWaiting.setModel(new SpinnerNumberModel(4000, 1, SPIN_MAX_VAL, 1));

        //impostazione del layout e posizionamento dei componenti
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup()
                      .addContainerGap()
                      .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblNomeBeneAllasta)
                                .addComponent(lblQuantitaBeneAllasta)
                                .addComponent(lblPrezzoDiPartenza)
                                .addComponent(lblPrezzoDiRiserva)
                                .addComponent(lblRibasso)
                                .addComponent(labelNumeroParticipant)
                                .addComponent(lblTempoDiAttesa))
                      .addGap(34)
                      .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
                                          .addComponent(spinnerDif, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                          .addComponent(spinnerQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                          .addComponent(spinnerRisPrice, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                                          .addComponent(spinnerStartPrice, Alignment.LEADING, 0, 0, Short.MAX_VALUE))
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
                                          .addComponent(spinnerWaiting, Alignment.LEADING)
                                          .addComponent(spinnerNumParticipant, Alignment.LEADING)))
                      .addPreferredGap(ComponentPlacement.RELATED)
                      .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                      .addPreferredGap(ComponentPlacement.RELATED)
                      .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                .addComponent(btnAvviaSimulazione)
                                .addComponent(btnAvviaAgenti)
                                .addComponent(chckbxSniffer)
                                .addComponent(chckbxManager))
                      .addContainerGap())
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup()
                      .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup()
                                          .addGap(23)
                                          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                  .addComponent(lblNomeBeneAllasta)
                                                  .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                  .addComponent(btnAvviaSimulazione))
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                  .addComponent(lblQuantitaBeneAllasta)
                                                  .addComponent(spinnerQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                  .addComponent(btnAvviaAgenti))
                                          .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                  .addGroup(groupLayout.createSequentialGroup()
                                                          .addPreferredGap(ComponentPlacement.RELATED)
                                                          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                                  .addComponent(spinnerStartPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                  .addComponent(lblPrezzoDiPartenza))
                                                          .addGap(12)
                                                          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                                  .addComponent(spinnerRisPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                  .addComponent(lblPrezzoDiRiserva))
                                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                                          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                                  .addComponent(spinnerDif, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                  .addComponent(lblRibasso)))
                                                  .addGroup(groupLayout.createSequentialGroup()
                                                          .addGap(18)
                                                          .addComponent(chckbxSniffer)
                                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                                          .addComponent(chckbxManager)))
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                  .addComponent(spinnerWaiting, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                  .addComponent(lblTempoDiAttesa))
                                          .addGap(11)
                                          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                  .addComponent(spinnerNumParticipant, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                  .addComponent(labelNumeroParticipant))
                                          .addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE))
                                .addGroup(groupLayout.createSequentialGroup()
                                          .addContainerGap()
                                          .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)))
                      .addGap(23))
        );
        setLayout(groupLayout);
    }

    /**
     *
     * @return l'agente iniziatore in base ai parametri inseriti nella GUI
     */
    public AgentInitiator getAgentInitiator() {
        AgentInitiator agent = new AgentInitiator(textField.getText(), (Integer) spinnerStartPrice.getValue(),
                (Integer) spinnerRisPrice.getValue(), (Integer) spinnerDif.getValue(),
                (Integer) spinnerQuantity.getValue(), (Integer) spinnerWaiting.getValue());
        return agent;
    }

    /**
     * Metodo per resettare la GUI
     */
    public void reset() {
        this.textArea.setText("");
        this.textField.setEditable(true);
        this.btnAvviaAgenti.setEnabled(false);
        this.btnAvviaSimulazione.setEnabled(true);
        this.spinnerDif.setEnabled(true);
        this.spinnerNumParticipant.setEnabled(true);
        this.spinnerQuantity.setEnabled(true);
        this.spinnerRisPrice.setEnabled(true);
        this.spinnerStartPrice.setEnabled(true);
        this.spinnerWaiting.setEnabled(true);
        this.chckbxManager.setEnabled(true);
        this.chckbxSniffer.setEnabled(true);
        this.setDif(1);
        this.setQuantity(1);
        this.setGoodName("");
        this.setStartPrice(1);
        this.setReservePrice(1);
        this.setNumParticipant(1);
    }

    //metodi get e set
    public void setWaiting(int msWait) {
        this.spinnerWaiting.setValue(msWait);
    }

    public void setGoodName(String goodName) {
        this.textField.setText(goodName);
    }

    public void setDif(int dif) {
        this.spinnerDif.setValue((int) dif);
    }

    public void setQuantity(int quantity) {
        this.spinnerQuantity.setValue(quantity);
    }

    public void setReservePrice(int reservePrice) {
        this.spinnerRisPrice.setValue((int) reservePrice);
    }

    public void setStartPrice(int startPrice) {
        this.spinnerStartPrice.setValue((int) startPrice);
    }

    public void setNumParticipant(int NumParticipant) {
        this.spinnerNumParticipant.setValue(NumParticipant);
    }

    public JSpinner getSpinnerNumParticipant() {
        return spinnerNumParticipant;
    }

    public void setSpinnerNumParticipant(JSpinner spinnerNumParticipant) {
        this.spinnerNumParticipant = spinnerNumParticipant;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public JLabel getLblQuantitaBeneAllasta() {
        return lblQuantitaBeneAllasta;
    }

    public void setLblQuantitaBeneAllasta(JLabel lblQuantitaBeneAllasta) {
        this.lblQuantitaBeneAllasta = lblQuantitaBeneAllasta;
    }

    public JLabel getLblPrezzoDiPartenza() {
        return lblPrezzoDiPartenza;
    }

    public void setLblPrezzoDiPartenza(JLabel lblPrezzoDiPartenza) {
        this.lblPrezzoDiPartenza = lblPrezzoDiPartenza;
    }

    public JLabel getLblPrezzoDiRiserva() {
        return lblPrezzoDiRiserva;
    }

    public void setLblPrezzoDiRiserva(JLabel lblPrezzoDiRiserva) {
        this.lblPrezzoDiRiserva = lblPrezzoDiRiserva;
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

    public JSpinner getSpinnerStartPrice() {
        return spinnerStartPrice;
    }

    public void setSpinnerStartPrice(JSpinner spinnerStartPrice) {
        this.spinnerStartPrice = spinnerStartPrice;
    }

    public JSpinner getSpinnerRisPrice() {
        return spinnerRisPrice;
    }

    public void setSpinnerRisPrice(JSpinner spinnerRisPrice) {
        this.spinnerRisPrice = spinnerRisPrice;
    }

    public JSpinner getSpinnerQuantity() {
        return spinnerQuantity;
    }

    public void setSpinnerQuantity(JSpinner spinnerQuantity) {
        this.spinnerQuantity = spinnerQuantity;
    }

    public JSpinner getSpinnerDif() {
        return spinnerDif;
    }

    public void setSpinnerDif(JSpinner spinnerDif) {
        this.spinnerDif = spinnerDif;
    }

    public JLabel getLblRibasso() {
        return lblRibasso;
    }

    public void setLblRibasso(JLabel lblRibasso) {
        this.lblRibasso = lblRibasso;
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

    public JSpinner getSpinnerWaiting() {
        return spinnerWaiting;
    }

    public void setSpinnerWaiting(JSpinner spinnerWaiting) {
        this.spinnerWaiting = spinnerWaiting;
    }

}
