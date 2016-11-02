package ontology;


import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;

/**
 * Classe singleton dell'ontologia
 *
 */
public class DutchOntology extends BeanOntology {

    //nome dell'ontologia
    public static final String ONTOLOGY_NAME = "dutch-auction";

    //istanza singleton della classe DutchOntology
    private static Ontology theInstance = new DutchOntology();

    /**
     *
     * @return oggetto singleton della classe DutchOntology
     */
    public static Ontology getInstance() {
        return theInstance;
    }

    /**
     * Costruttore privato
     */
    private DutchOntology() {
        super(ONTOLOGY_NAME);

        try {
            // agginge tutti i Concept, Predicate e AgentAction presenti nel package
            add(this.getClass().getPackage().getName());

        } catch(BeanOntologyException boe) {
            boe.printStackTrace();
        }
    }

}