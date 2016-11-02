package ontology;

import jade.content.Concept;

/**
 * Classe che implementa il concetto del bene
 *
 */
public class Good implements Concept {
    //nome del bene
    private String name;
    //prezzo attuale
    private int price;
    //prezzo di riserva
    private int reservePrice;
    //quantita'
    private int quantity;

    /**
     * Costruttore
     * @param name nome del bene
     * @param price prezzo attuale
     * @param reserve prezzo di riserva
     * @param qty quantita'
     */
    public Good(String name, int price, int reserve, int qty) {
        this.name = name;
        this.price = price;
        this.reservePrice = reserve;
        this.quantity = qty;
    }

    //metodi get e set
    public int getQuantity() {
        return quantity;
    }
    public void setQuanyity(int newQty) {
        this.quantity = newQty;
    }
    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}