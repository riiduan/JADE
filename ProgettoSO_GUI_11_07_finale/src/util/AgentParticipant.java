package util;

/**
 * Classe che modella i parametri di un partecipante all'asta
 *
 */
public class AgentParticipant {
    //nome del partecipante
    private String name;
    //massimo prezzo che e' disposto a pagare
    private int maxPrice;
    //quantita' che desidera acquistare
    private int quantity;

    /**
     * Costruttore
     * @param name Nome locale dell'agente
     * @param maxPrice massimo prezzo a cui e' disposto ad acquistare
     * @param quantity quantita' che desidera acquistare
     */
    public AgentParticipant(String name, int maxPrice, int quantity) {
        this.name = name;
        this.maxPrice = maxPrice;
        this.quantity = quantity;
    }

//	metodi get e set
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
