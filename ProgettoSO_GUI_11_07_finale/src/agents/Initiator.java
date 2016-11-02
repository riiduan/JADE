package agents;

import java.util.ArrayList;

import ontology.DutchOntology;
import ontology.Good;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;
import behaviours.BehaviourInitiator;

/**
 * classe che modella l'iniziatore dell'asta
 */
public class Initiator extends Agent {

    private ContentManager manager = (ContentManager) getContentManager();
    private Codec codec = new SLCodec();
    private Ontology ontology = DutchOntology.getInstance();
    //stato inizializzato allo stato iniziale
    private int state = BehaviourInitiator.INFORM_START_OF_AUCTION;
    //ArrayList dei partecipanti
    private ArrayList<AMSAgentDescription> agents = null;
    //bene all'asta
    private Good good;
    //ribasso ad ogni step
    private int dif = 1;
    //quantita' del bene
    private int quantity = 15;
    //prezzo attuale del bene
    private int price;
    //prezzo di riserva del bene
    private int reservePrice;
    //tempo di attesa in msec
    private int msWait;
    private String goodName = "tulipani-rossi";
    private ACLMessage bid = null;

    /**
     * metodo per inizializzare l'agente
     */
    protected void setup() {
        //inizializza gli attributi dell'iniziatore in base ai valori dei parametri
        Object[] args = this.getArguments();
        if (args != null) {
            this.goodName  = (String) args[0];
            this.price = (int) args[1];
            this.reservePrice = (int) args[2];
            this.dif = (int) args[3];
            this.quantity = (int) args[4];
            this.msWait = (int) args[5];
        }

        manager.registerLanguage(codec);
        manager.registerOntology(ontology);

        //inizializza il bene oggetto dell'asta
        good = new Good(goodName, price, reservePrice, quantity);

        //cerca ed inserisce nell'ArrayList gli eventuali partecipanti
        findParticipants();

        //viene aggiunto l'apposito behaviour
        addBehaviour(new BehaviourInitiator(this));

        System.out.println("Initiator " + this.getLocalName() + " avviato");
    }

    /**
     * Cerca i potenziali partecipanti all'asta
     * @return il numero dei potenziali partecipanti trovati
     */
    private int findParticipants() {
        try {
            agents = new ArrayList<AMSAgentDescription>();
            //definisce i vincoli di ricerca, in questo caso tutti i risultati
            SearchConstraints searchConstraints = new SearchConstraints();
            searchConstraints.setMaxResults(Long.MAX_VALUE);
            //tutti gli agenti dell'AMS vengono messi in un array
            AMSAgentDescription [] allAgents = AMSService.search(this, new AMSAgentDescription(), searchConstraints);
            //scorre la lista degli agenti trovati al fine di filtrare solo quelli di interesse
            for (AMSAgentDescription a: allAgents) {
                //aggiunge l'agente partecipante all'ArrayList se e' un partecipante
                if(a.getName().getLocalName().contains("Participant")) {
                    agents.add(a);
                }
            }
        } catch (Exception e) {
            System.out.println( "Problema di ricerca dell'AMS: " + e );
            e.printStackTrace();
        }
        return agents.size();
    }

//    metodi get e set
    public ContentManager getManager() {
        return manager;
    }

    public void setManager(ContentManager manager) {
        this.manager = manager;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ArrayList<AMSAgentDescription> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<AMSAgentDescription> agents) {
        this.agents = agents;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public int getDif() {
        return dif;
    }

    public void setDif(int dif) {
        this.dif = dif;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ACLMessage getBid() {
        return bid;
    }

    public void setBid(ACLMessage bid) {
        this.bid = bid;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(int reservePrice) {
        this.reservePrice = reservePrice;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getMsWait() {
        return msWait;
    }

    public void setMsWait(int msWait) {
        this.msWait = msWait;
    }
}
