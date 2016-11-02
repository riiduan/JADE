package behaviours;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Behaviour del partecipante che istanzia un BehaviourParticipant 
 * per ogni iniziatore che informa di voler iniziare un'asta
 */
public class CyclicBehaviourParticipant extends CyclicBehaviour{
	//MessageTemplate per il matching, al fine di ricevere solo messaggi con performative INFORM
	// e contenuto "inform start of auction"
	private MessageTemplate mexTemplate = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), 
				MessageTemplate.MatchContent("inform start of auction"));
		
		/**
		 * Costruttore del Behaviour
	     * @param a agente a cui viene assegnato il behaviour
		 */
		public CyclicBehaviourParticipant(Agent a){
			super(a);			
		}

		/**
	     * Metodo eseguito sempre
	     */
	public void action() {
		ACLMessage msg = this.myAgent.receive(mexTemplate);		
		//se riceve messaggi che soddisfano il MessageTemplate matching 
		//aggiunge un behaviour all'agente partecipante di tipo BehaviourParticipant
		if (msg != null)
		{
			System.out.println(this.myAgent.getLocalName() 
					+ " riceve MESSAGGIO [INFORM] 'inizio di una nuova asta' MITTENTE " 
					+ msg.getSender().getLocalName());
			this.myAgent.addBehaviour(new BehaviourParticipant(this.myAgent, msg.getConversationId()));
		}
		else{
			//si blocca fino a quando non riceve messaggi
			this.block();
		}
		
	}

}
