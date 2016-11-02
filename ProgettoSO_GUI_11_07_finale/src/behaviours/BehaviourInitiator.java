package behaviours;

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import ontology.IBuy;
import ontology.NewGood;
import ontology.NewPrice;
import ontology.YouBuy;
import agents.Initiator;

/**
 * Classe che implementa il Behaviour dell'agente iniziatore dell'asta
 *
 */
public class BehaviourInitiator extends Behaviour {
    //stati del behaviour dell'iniziatore
    public final static int INFORM_START_OF_AUCTION = 0;
    public final static int CALL_FOR_PROPOSAL_1 = 1;
    public final static int WAITING_MESSAGE = 2;
    public final static int CALL_FOR_PROPOSAL_2 = 3;
    public final static int INFORM_2 = 4;
    public final static int END = 5;

    /**
     * Costruttore del Behaviour
     * @param a agente a cui viene assegnato il behaviour
     */
    public BehaviourInitiator(Agent a) {
        super(a);
    }

    /**
     * Metodo eseguito sempre da quando viene assegnato all'agente e viene avviato l'agente
     * fino a che il metodo done() non ritorna true
     */
    public void action() {

        ContentManager cm = this.myAgent.getContentManager();

        //in base allo stato esegue le apposite istruzioni
        switch (((Initiator) this.myAgent).getState()) {
        case INFORM_START_OF_AUCTION: { //Inizia una nuova asta

            //Invia il/i messaggio/i di informazione dell'intenzione di iniziare una nuova asta
            //se ha trovato almeno un agente
            if(((Initiator) this.myAgent).getAgents().size() > 0) {
                // Crea il messaggio da inviare con l'opportuna Performative
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setContent("start of auction");
                msg.setConversationId("DutchAuctionProtocol-" + this.myAgent.getLocalName());

                for(int i = 0; i< ((Initiator) this.myAgent).getAgents().size(); i++) {
                    // aggiunta dei destinatari del messaggio
                    msg.addReceiver(((Initiator) this.myAgent).getAgents().get(i).getName());
                    System.out.println(this.myAgent.getLocalName()
                                       + " invia MESSAGGIO [INFORM] 'start of auction' DESTINATARIO: " +
                                       ((Initiator) this.myAgent).getAgents().get(i).getName().getLocalName());
                }
                this.myAgent.send(msg);
                //aggiorna lo stato
                ((Initiator) this.myAgent).setState(CALL_FOR_PROPOSAL_1);
            } else {
                System.out.println(this.myAgent.getLocalName() + ": 'Asta terminata: non ci sono agenti.'");
                //aggiorna lo stato a quello finale
                ((Initiator) this.myAgent).setState(END);
            }
        }
        break;

        //manda a tutti i possibili acquirenti un messaggio contenente:
        //il nome, il prezzo e la quantita' del bene
        case CALL_FOR_PROPOSAL_1: {
            // c'e' almeno un agente
            if(((Initiator) this.myAgent).getAgents().size() > 0) {
                // Crea il messaggio da inviare con l'opportuna Performative
                ACLMessage msg = new ACLMessage(ACLMessage.CFP);
                // Aggiunta del contenuto
                msg.setContent("call for proposal 1");
                msg.setConversationId("DutchAuctionProtocol-" + this.myAgent.getLocalName());
                msg.setLanguage(((Initiator) this.myAgent).getCodec().getName());
                msg.setOntology(((Initiator) this.myAgent).getOntology().getName());
                //crea un'istanza del bene oggetto della nuova asta
                NewGood newGood = new NewGood();
                newGood.setGoodName(((Initiator) this.myAgent).getGood().getName());
                newGood.setGoodPrice(((Initiator) this.myAgent).getGood().getPrice());
                newGood.setGoodReservePrice(((Initiator) this.myAgent).getGood().getReservePrice());
                newGood.setQuantity(((Initiator) this.myAgent).getGood().getQuantity());
                Action a = new Action(this.myAgent.getAID(), newGood);
                try {
                    cm.fillContent(msg, a);
                } catch (CodecException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (OntologyException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //aggiunge i destinatari dei messaggi
                for(int i=0; i<((Initiator) this.myAgent).getAgents().size(); i++) {

                    msg.addReceiver(((Initiator) this.myAgent).getAgents().get(i).getName());
                    System.out.println(this.myAgent.getLocalName() + " invia MESSAGGIO [CALL_FOR_PROPOSAL] 'call for proposal 1 Bene: "
                                       + ((Initiator) this.myAgent).getGood().getName()
                                       + " Quantita':" + ((Initiator) this.myAgent).getGood().getQuantity()
                                       + " Prezzo:"+((Initiator) this.myAgent).getGood().getPrice()
                                       + "' DESTINATARIO: " + ((Initiator) this.myAgent).getAgents().get(i).getName().getLocalName());
                }
//invia il messaggio a tutti i destinatari
                this.myAgent.send(msg);

                //aggiorna lo stato
                ((Initiator) this.myAgent).setState(WAITING_MESSAGE);
            } else {
                System.out.println(this.myAgent.getLocalName() + ": 'Asta terminata: non ci sono agenti.'");
                //aggiorna lo stato
                ((Initiator) this.myAgent).setState(END);
            }
        }
        break;

        case WAITING_MESSAGE: {
            //aspetta un certo numero di millisecondi (deciso dall'utente) prima di leggere i messaggi 
        	//e quindi andare avanti
            ((Initiator) this.myAgent).setBid(this.myAgent.blockingReceive(((Initiator) this.myAgent).getMsWait()));
            //non ha ricevuto risposte e quindi nessuna offerta
            if(((Initiator) this.myAgent).getBid() == null) {
                System.out.println(this.myAgent.getLocalName() + ": Nessun offerta ricevuta");
                //aggiorna il prezzo solo se maggiore al prezzo di riserva
                if(((Initiator) this.myAgent).getGood().getPrice() > ((Initiator) this.myAgent).getGood().getReservePrice()) {
                    System.out.print(((Initiator) this.myAgent).getLocalName()
                                     + " RIBASSO\tPrezzo di partenza: " + ((Initiator) this.myAgent).getGood().getPrice());
                    //prezzo attuale maggiore del prezzo di riserva
                    if(((Initiator) this.myAgent).getGood().getPrice()-((Initiator) this.myAgent).getDif()
                            > ((Initiator) this.myAgent).getGood().getReservePrice()) {
                        ((Initiator) this.myAgent).getGood().setPrice(((Initiator) this.myAgent).getGood().getPrice()-((Initiator) this.myAgent).getDif());
                    } else {
                        ((Initiator) this.myAgent).getGood().setPrice(((Initiator) this.myAgent).getGood().getReservePrice());
                    }
                    System.out.print("\tPrezzo aggiornato: " + ((Initiator) this.myAgent).getGood().getPrice()+"\n");
                    //aggiorna lo stato
                    ((Initiator) this.myAgent).setState(CALL_FOR_PROPOSAL_2);
                }
                //termina l'asta in quanto e' stato raggiunto il prezzo di riserva gia' negli step precedenti
                else {
                    System.out.println(this.myAgent.getLocalName()
                                       + ": 'Asta terminata: e' stato raggiunto il prezzo di riserva.");
                    //aggiorna lo stato
                    ((Initiator) this.myAgent).setState(INFORM_2);
                }
            }
            //ha ricevuto messaggi, quindi offerte
            else {
                ContentElement content = null;
                try {
                    content = this.myAgent.getContentManager().extractContent(((Initiator) this.myAgent).getBid());
                } catch (UngroundedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (CodecException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (OntologyException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Concept action = ((Action)content).getAction();
                //viene ricevuto un messaggio che qualcuno vuole acquistare, cioe' ha fatto un offerta
                if(action instanceof IBuy) {
                    if(((IBuy)action).getGoodName().equals(((Initiator) this.myAgent).getGood().getName())) {
                        //controlla che la quantita' richiesta non sia superiore a quella disponibile
                        if(((IBuy)action).getQuantity() <= ((Initiator) this.myAgent).getGood().getQuantity()) {
                            ACLMessage msg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                            msg.setContent("accept proposal");
                            msg.setConversationId("DutchAuctionProtocol-" + this.myAgent.getLocalName());
                            msg.setLanguage(((Initiator) this.myAgent).getCodec().getName());
                            msg.setOntology(((Initiator) this.myAgent).getOntology().getName());
                            YouBuy bought = new YouBuy();
                            bought.setGoodName(((Initiator) this.myAgent).getGood().getName());
                            bought.setGoodPrice(((Initiator) this.myAgent).getGood().getPrice());
                            bought.setGoodReservePrice(((Initiator) this.myAgent).getGood().getReservePrice());
                            bought.setQuantity(((IBuy)action).getQuantity());
                            Action a = new Action(((Initiator) this.myAgent).getBid().getSender(), bought);
                            try {
                                cm.fillContent(msg, a);
                            } catch (CodecException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (OntologyException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            msg.addReceiver(((Initiator) this.myAgent).getBid().getSender());
                            this.myAgent.send(msg);
                            System.out.println(this.myAgent.getLocalName() + " invia MESSAGGIO [ACCEPT_PROPOSAL] 'Accept Propose "
                                               + "Bene:" + ((IBuy)action).getGoodName()
                                               + " Quantita':" + ((IBuy)action).getQuantity()
                                               + " Prezzo: " + ((IBuy)action).getGoodPrice() + "' DESTINATARIO "
                                               + ((Initiator) this.myAgent).getBid().getSender().getLocalName() );

                            //rimuove l'agente che ha acquistato dalla lista degli agenti
                            for(int i=0; i<((Initiator) this.myAgent).getAgents().size(); i++) {
                                if(((Initiator) this.myAgent).getAgents().get(i).getName().getLocalName().equals(((Initiator) this.myAgent).getBid().getSender().getLocalName()))
                                    ((Initiator) this.myAgent).getAgents().remove(i);
                            }
                            //aggiorna la quantita del bene a seguito dell'acquisto da parte di un agente
                            ((Initiator) this.myAgent).getGood().setQuanyity(((Initiator) this.myAgent).getGood().getQuantity()-((IBuy)action).getQuantity());
                        }
                        //quantita' richiesta maggiore della disponibile
                        //puo' essere dovuta all'acquisto da parte di altri partecipanti
                        else {
                            ACLMessage msg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                            msg.setContent("reject proposal: quantita' richiesta maggiore della disponibile");
                            msg.setConversationId("DutchAuctionProtocol-" + this.myAgent.getLocalName());
                            msg.addReceiver(((Initiator) this.myAgent).getBid().getSender());
                            this.myAgent.send(msg);
                            System.out.println(this.myAgent.getLocalName() + " invia MESSAGGIO [REJECT_PROPOSAL] 'reject proposal: e' stata richiesta una quantita' "
                                               + "maggiore della disponibile' DESTINATARIO:" + ((Initiator) this.myAgent).getBid().getSender().getLocalName());
                        }
                        //il bene e' stato completamente venduto
                        if(((Initiator) this.myAgent).getGood().getQuantity()==0) {
                            //aggiorna lo stato
                            ((Initiator) this.myAgent).setState(INFORM_2);
                        } else {
                            ((Initiator) this.myAgent).setState(WAITING_MESSAGE);
                        }
                    }
                }
            }
        }
        break;

        case INFORM_2 : {
            //c'e' almeno un agente
            if(((Initiator) this.myAgent).getAgents().size()> 0) {
                //invia a tutti gli altri l'avvertimento che il bene e' stato venduto
                //quindi non ci sono offerte
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                // Aggiunta del contenuto
                msg.setContent("inform 2");
                msg.setConversationId("DutchAuctionProtocol-" + this.myAgent.getLocalName());
                msg.setLanguage(((Initiator) this.myAgent).getCodec().getName());
                msg.setOntology(((Initiator) this.myAgent).getOntology().getName());

                for(int i=0; i < ((Initiator) this.myAgent).getAgents().size(); i++) {
                    //e' stato venduto tutto il bene
                    if(((Initiator) this.myAgent).getGood().getQuantity()==0) {
                        System.out.println(this.myAgent.getLocalName() + " invia MESSAGGIO [INFORM] 'inform 2: il bene e' stato completamente venduto, "
                                           + "non ci sono offerte' "+ ((Initiator) this.myAgent).getGood().getName() + " DESTINATARIO "
                                           + ((Initiator) this.myAgent).getAgents().get(i).getName().getLocalName());
                    }
                    //e' stato raggiunto il prezzo di riserva
                    else if(((Initiator) this.myAgent).getGood().getPrice()==((Initiator) this.myAgent).getGood().getReservePrice()) {
                        System.out.println(this.myAgent.getLocalName() + " invia MESSAGGIO [INFORM] 'inform 2: e' stato raggiunto il prezzo di riserva, "
                                           + "non ci sono offerte' "+ ((Initiator) this.myAgent).getGood().getName() + " DESTINATARIO "
                                           + ((Initiator) this.myAgent).getAgents().get(i).getName().getLocalName());
                    } else {
                        System.out.println(this.myAgent.getLocalName() + " invia MESSAGGIO [INFORM] 'inform 2: il bene e' ancora presente e "
                                           + "non e' stato raggiunto il prezzo di riserva, ma l'asta termina perche' "
                                           + "non ci sono offerte' "+ ((Initiator) this.myAgent).getGood().getName() + " DESTINATARIO "
                                           + ((Initiator) this.myAgent).getAgents().get(i).getName().getLocalName());
                    }
                    msg.addReceiver(((Initiator) this.myAgent).getAgents().get(i).getName());
                }
                this.myAgent.send(msg);
            } else {
                System.out.println(this.myAgent.getLocalName() + ": 'Asta terminata: non ci sono agenti.'");
            }
            ((Initiator) this.myAgent).setState(END);
        }
        break;

        case CALL_FOR_PROPOSAL_2: {
            //prezzo attuale maggiore o uguale al prezzo di riserva
            if(((Initiator) this.myAgent).getGood().getPrice() >= ((Initiator) this.myAgent).getGood().getReservePrice()) {
                //ci sono agente che partecipano all'asta
                if(((Initiator) this.myAgent).getAgents().size() > 0) {
                    // Crea il messaggio da inviare con l'opportuna Performative
                    ACLMessage msg = new ACLMessage(ACLMessage.CFP);
                    // Aggiunta del contenuto
                    msg.setContent("call for proposal 2");
                    msg.setConversationId("DutchAuctionProtocol-" + this.myAgent.getLocalName());
                    msg.setLanguage(((Initiator) this.myAgent).getCodec().getName());
                    msg.setOntology(((Initiator) this.myAgent).getOntology().getName());
                    NewPrice newPrice = new NewPrice();
                    newPrice.setGoodName(((Initiator) this.myAgent).getGood().getName());
                    newPrice.setGoodPrice(((Initiator) this.myAgent).getGood().getPrice());
                    newPrice.setGoodReservePrice(((Initiator) this.myAgent).getGood().getReservePrice());
                    newPrice.setQty(((Initiator) this.myAgent).getGood().getQuantity());
                    Action a = new Action(this.myAgent.getAID(), newPrice);
                    try {
                        cm.fillContent(msg, a);
                    } catch (CodecException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (OntologyException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    for(int i=0; i < ((Initiator) this.myAgent).getAgents().size(); i++) {
                        System.out.println(this.myAgent.getLocalName() + " invia MESSAGGIO [CALL_FOR_PROPOSAL] 'call for proposal 2 "
                                           + "Bene:" + ((Initiator) this.myAgent).getGood().getName()
                                           + " Quantita': " + ((Initiator) this.myAgent).getGood().getQuantity()
                                           + " Prezzo:" + ((Initiator) this.myAgent).getGood().getPrice()
                                           + "' DESTINATARIO " + ((Initiator) this.myAgent).getAgents().get(i).getName().getLocalName());
                        msg.addReceiver(((Initiator) this.myAgent).getAgents().get(i).getName());
                    }
                    this.myAgent.send(msg);
                    ((Initiator) this.myAgent).setState(WAITING_MESSAGE);
                }
                //non ci sono agenti
                else {
                    System.out.println(this.myAgent.getLocalName() + ": 'ASTA TERMINATA: non ci sono agenti.'");
                    //aggiorna lo stato
                    ((Initiator) this.myAgent).setState(END);
                }

            }
            //e' stato raggiunto il prezzo di riserva
            else {
                System.out.println(this.myAgent.getLocalName() + ": 'ASTA TERMINATA: e' stato raggiunto il prezzo di riserva.'");
                //aggiorna lo stato
                ((Initiator) this.myAgent).setState(INFORM_2);
            }
        }
        break;
        }
    }

    /**
     * Determina la fine dell'esecuzione del Behaviour, quindi del metodo action()
     * quando ritorna true, altrimenti se il valore di ritorno e' false
     * viene sempre eseguito il metodo action()
     * @return true quando l'agente si trova nello stato END
     */
    public boolean done() {
        return ((Initiator) this.myAgent).getState()==END;
    }
}
