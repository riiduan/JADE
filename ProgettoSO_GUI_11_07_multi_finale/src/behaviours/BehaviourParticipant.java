package behaviours;

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import ontology.Good;
import ontology.IBuy;
import ontology.NewGood;
import ontology.NewPrice;
import ontology.YouBuy;
import agents.Participant;

/**
 * Behaviour del partecipante utilizzato per contrattare con un iniziatore
 *
 */
public class BehaviourParticipant extends Behaviour {
	//serve per dire quando il behaviour ha completato l'esecuzione
	private MessageTemplate mexTemplate;
	private boolean stopBehaviour;
	
	/**
	 * Costruttore
     * @param a agente a cui e' assegnato il behaviour
	 * @param conversationID conversationID per cui il behaviour e'
	 * predisposto a riceve messaggi
	 */
	public BehaviourParticipant(Agent a, String conversationID){
		super(a);
		mexTemplate = MessageTemplate.MatchConversationId(conversationID);
		stopBehaviour=false;
	}
	  
	/**
     * Metodo eseguito sempre da quando viene assegnato all'agente e viene avviato l'agente
     * fino a che il metodo done() non ritorna true
     */
	public void action(){
			ACLMessage msg = this.myAgent.receive(mexTemplate);
			if(msg != null){
				if(msg.getContent().equals("inform 2")){
					System.out.println(this.myAgent.getLocalName() + " riceve MESSAGGIO [INFORM] 'fine asta'");	
					((Participant) this.myAgent).setPropose(false);
					stopBehaviour=true;
				}else if(msg.getPerformative() == ACLMessage.REJECT_PROPOSAL){
					((Participant) this.myAgent).setPropose(false);
					System.out.println(this.myAgent.getLocalName() + " riceve MESSAGGIO [REJECT_PROPOSAL] 'Reject'");	
				}
				else{
					try {
         				ContentElement content = this.myAgent.getContentManager().extractContent(msg);
         				Concept action = ((Action)content).getAction();
         				//ontologia corrispondente a una nuova asta
		  				if (action instanceof NewGood){
		  					//setta le caratteristiche (nome, prezzo e quantita') del bene di partenza
		  					((Participant) this.myAgent).setInitialGood(new Good(((NewGood)action).getGoodName(), ((NewGood)action).getGoodPrice(),
		  							((NewGood)action).getGoodReservePrice(),((NewGood)action).getQuantity()));
		  					//setta le caratteristiche (nome, prezzo e quantita') del bene attuale
		  					((Participant) this.myAgent).setActualGood(new Good(((NewGood)action).getGoodName(), ((NewGood)action).getGoodPrice(),
		  							((NewGood)action).getGoodReservePrice(),((NewGood)action).getQuantity()));
		  					System.out.println(this.myAgent.getLocalName() + " riceve MESSAGGIO [CALL_FOR_PROPOSAL] 'Nuova asta Bene:"
		  							+ ((Participant) this.myAgent).getActualGood().getName() 
		  							+ " Quantita':" + ((Participant) this.myAgent).getActualGood().getQuantity() 
		  							+ " Prezzo:" + ((Participant) this.myAgent).getActualGood().getPrice() 
		  							+ "' MITTENTE "	+ msg.getSender().getLocalName());
		  					
		  					//prezzo attuale minore o uguale a quello che e' disposto a pagare
		  					if(((Participant) this.myAgent).getActualGood().getPrice() <= ((Participant) this.myAgent).getMaxPrice()
		  							&& ((NewGood)action).getGoodName().equals(((Participant) this.myAgent).getGoodName())
		  							&& ((Participant) this.myAgent).getQuantity()>0
		  							&& !((Participant) this.myAgent).isPropose()){
			  					((Participant) this.myAgent).setPropose(true);
		  						ACLMessage answerMes = new ACLMessage(ACLMessage.PROPOSE);
			  					answerMes.setLanguage(((Participant) this.myAgent).getCodec().getName());
			  					answerMes.setOntology(((Participant) this.myAgent).getOntology().getName());
			  					ContentManager cm = this.myAgent.getContentManager();
			  					//ontologia che indica l'azione di voler acquistare
			  					IBuy buy = new IBuy();
			  					buy.setGoodName(((Participant) this.myAgent).getActualGood().getName());
			  					buy.setGoodPrice(((Participant) this.myAgent).getActualGood().getPrice());
			  					//adatta la quantita' richiesta in base alla disponibilita' dell'asta
			  					if(((Participant) this.myAgent).getQuantity() 
			  							<= ((Participant) this.myAgent).getActualGood().getQuantity())
			  						buy.setQuantity(((Participant) this.myAgent).getQuantity());
			  					else
			  						buy.setQuantity(((Participant) this.myAgent).getActualGood().getQuantity());
			  					Action a = new Action(msg.getSender(), buy);
			  					cm.fillContent(answerMes, a);
			  					//aggiunge il destinatario
			  					answerMes.addReceiver(msg.getSender());
			  					this.myAgent.send(answerMes);
			  					System.out.println(this.myAgent.getLocalName() + " invia MESSAGGIO [PROPOSE] 'Bene:"+ buy.getGoodName() 
			  					+ " Quantita':" + buy.getQuantity()
			  					+ " Prezzo:" + buy.getGoodPrice() + "' DESTINATARIO " 
			  							+ msg.getSender().getLocalName());
			  				}  	
		  					
		  				//ontologia corrispondente a un ribasso
		  				} else if ( action instanceof NewPrice) {
		  					//setta le caratteristiche (nome, prezzo e quantita') del bene attuale
		  					((Participant) this.myAgent).setActualGood(new Good(((NewPrice)action).getGoodName(), ((NewPrice)action).getGoodPrice(),
		  							((NewPrice)action).getGoodReservePrice(),((NewPrice)action).getQty()));
		  					System.out.println(this.myAgent.getLocalName() + " riceve MESSAGGIO [CALL_FOR_PROPOSAL] 'Nuovo prezzo Bene:"
		  							+ ((Participant) this.myAgent).getActualGood().getName() + 
		  							" Quantita':" + ((Participant) this.myAgent).getActualGood().getQuantity() 
		  							+ " Prezzo:" + ((Participant) this.myAgent).getActualGood().getPrice() 
		  							+ "' MITTENTE "	+ msg.getSender().getLocalName());
		  					
		  					//prezzo attuale minore o uguale a quello che e' disposto a pagare	
		  					if(((Participant) this.myAgent).getActualGood().getPrice() <= ((Participant) this.myAgent).getMaxPrice()
	  							&& ((NewPrice)action).getGoodName().equals(((Participant) this.myAgent).getGoodName())
	  							&& ((Participant) this.myAgent).getQuantity()>0
	  							&& !((Participant) this.myAgent).isPropose()){
			  					//System.out.println(this.myAgent.getLocalName()+" quantita' propose: "+((Participant) this.myAgent).getQuantity());
			  					((Participant) this.myAgent).setPropose(true);
			  					ACLMessage answerMes = new ACLMessage(ACLMessage.PROPOSE);
			  					answerMes.setLanguage(((Participant) this.myAgent).getCodec().getName());
			  					answerMes.setOntology(((Participant) this.myAgent).getOntology().getName());
			  					ContentManager cm = this.myAgent.getContentManager();
			  					//ontologia che indica l'interesse di acquistare
			  					IBuy buy = new IBuy();
			  					buy.setGoodName(((Participant) this.myAgent).getActualGood().getName());
			  					buy.setGoodPrice(((Participant) this.myAgent).getActualGood().getPrice());
			  					//adatta la quantita' richiesta in base alla disponibilita' dell'asta
			  					if(((Participant) this.myAgent).getQuantity() 
			  							<= ((Participant) this.myAgent).getActualGood().getQuantity())
			  						buy.setQuantity(((Participant) this.myAgent).getQuantity());
			  					else
			  						buy.setQuantity(((Participant) this.myAgent).getActualGood().getQuantity());
			  					Action a = new Action(msg.getSender(), buy);
			  					cm.fillContent(answerMes, a);
			  					//aggiunge il destinatario
			  					answerMes.addReceiver(msg.getSender());			  					
			  					this.myAgent.send(answerMes);
			  					System.out.println(this.myAgent.getLocalName() + " invia MESSAGGIO [PROPOSE] 'Bene:"+ buy.getGoodName() 
			  					+ " Quantita':" + buy.getQuantity()
			  					+ " Prezzo:" + buy.getGoodPrice() + "' DESTINATARIO " 
			  							+ msg.getSender().getLocalName());
			  				}  	
		  				
		  				}
		  				//ontologia corrispondente al fatto che acquista il bene, cioe' vince l'asta
		  				else if (action instanceof YouBuy) {
		  					((Participant) this.myAgent).setQuantity(((Participant) this.myAgent).getQuantity()-((YouBuy)action).getQuantity());
		  					((Participant) this.myAgent).setPropose(false);
		  					//System.out.println(this.myAgent.getLocalName()+" quantita' dopo acquisto: "+((Participant) this.myAgent).getQuantity());
		  					((Participant) this.myAgent).setActualGood(new Good(((YouBuy)action).getGoodName(), ((YouBuy)action).getGoodPrice(),
		  							((YouBuy)action).getGoodReservePrice(),((YouBuy)action).getQuantity()));		  		
		  					System.out.println(this.myAgent.getLocalName() + " riceve MESSAGGIO [ACCEPT_PROPOSAL] 'Asta vinta Bene:"
		  							+ ((Participant) this.myAgent).getActualGood().getName() + 
		  							" Quantita':" + ((Participant) this.myAgent).getActualGood().getQuantity() 
		  							+ " Prezzo:" + ((Participant) this.myAgent).getActualGood().getPrice() 
		  							+ "' MITTENTE "	+ msg.getSender().getLocalName());
		  					this.stopBehaviour = true;		  					
		  				}					
		  				}
        catch(Exception ex) { 
        	ex.printStackTrace(); 
        	}
				}
				
			}
			//se non ci sono messaggi
			else{
				// il behaviour si sospende fino alla ricezione di nuovi messaggi
				this.block();
			}
			if(((Participant) this.myAgent).getQuantity()==0)
					//termina l'agente participante e quindi tutti i suoi behaviours
				((Participant) this.myAgent).setStop(true);
	}
	
	/**
     * Determina la fine dell'esecuzione del Behaviour, quindi del metodo action()
     * quando ritorna true, altrimenti se il valore di ritorno e' false
     * viene sempre eseguito il metodo action()
     * @return true quando il booleano stop e' true
     */
	public boolean done(){
			return (((Participant) this.myAgent).isStop() || this.stopBehaviour);		
		}
}
