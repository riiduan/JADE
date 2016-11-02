package ontology;

import jade.content.AgentAction;

/**
 * Azione dell'agente iniziatore relativa alla conferma che il partecipante
 * si e' aggiudicato il bene, cioe' ha vinto l'asta
 *
 */
public class YouBuy implements AgentAction {
    //nome del bene
    private String goodName;
    //prezzo attuale del bene
    private int goodPrice;
    //prezzo di riserva del bene
    private int goodReservePrice;
    //quantita' del bene
    private int quantity;

    //metodi get e set
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int newQty) {
        this.quantity = newQty;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getGoodPrice() {
        return this.goodPrice;
    }
    public void setGoodPrice(int goodPrice) {
        this.goodPrice = goodPrice;
    }

    public int getGoodReservePrice() {
        return goodReservePrice;
    }

    public void setGoodReservePrice(int goodReservePrice) {
        this.goodReservePrice = goodReservePrice;
    }
}
