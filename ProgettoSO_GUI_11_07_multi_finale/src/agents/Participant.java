package agents;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import ontology.DutchOntology;
import ontology.Good;
import behaviours.BehaviourParticipant;
import behaviours.CyclicBehaviourParticipant;

/**
 * Classe che modella gli agenti partecipanti all'asta
 *
 */
public class Participant extends Agent {
	
	//bene iniziale
	private Good initialGood;
	//bene attuale
	private Good actualGood;
	//nome del bene desiderato
	private String goodName;
	//massimo prezzo a cui e' disponibile a effettuare l'acquisto
	private int maxPrice = 7;
	//quantita' che desidera acquistare
	private int quantity;
	//deve fermarsi/terminare se true
	private boolean stop = false;
	//ha fatto una proposta e aspetta la risposta se true
	private boolean propose = false;
	private Codec codec = new SLCodec();
	private Ontology ontology = DutchOntology.getInstance();
	
	/**
	 * Metodo che inizializza l'agente
	 */
	protected void setup(){
	    Object[] args = this.getArguments();
 		if (args != null)
 		{
 			this.maxPrice  = (int) args[0]; 	
 			this.quantity = (int) args[1];
 			this.goodName = (String) args[2];
 		}
				
		this.getContentManager().registerLanguage(codec);
		this.getContentManager().registerOntology(ontology);
				
        System.out.println("Participant " + this.getLocalName() + " avviato");

		//viene aggiunto l'apposito behaviour 
		addBehaviour(new CyclicBehaviourParticipant(this));
		
	}
	
	//metodi get e set
	public Good getInitialGood() {
		return initialGood;
	}

	public void setInitialGood(Good initialGood) {
		this.initialGood = initialGood;
	}

	public Good getActualGood() {
		return actualGood;
	}

	public void setActualGood(Good actualGood) {
		this.actualGood = actualGood;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Codec getCodec() {
		return codec;
	}

	public void setCodec(Codec codec) {
		this.codec = codec;
	}

	public Ontology getOntology() {
		return ontology;
	}

	public void setOntology(Ontology ontology) {
		this.ontology = ontology;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public boolean isPropose() {
		return propose;
	}

	public void setPropose(boolean propose) {
		this.propose = propose;
	}
	
}