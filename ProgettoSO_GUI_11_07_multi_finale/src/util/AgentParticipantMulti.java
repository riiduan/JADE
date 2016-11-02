package util;

/**
 * Classe che modella i parametri di un partecipante all'asta
 *
 */
public class AgentParticipantMulti {
	//nome del partecipante
	private String name;
	//massimo prezzo che e' disposto a pagare
	private int maxPrice;
	//quantita' che desidera acquistare
	private int quantity;
	//nome del bene desiderato
	private String goodName;
	
	/**
	 * 
	 * @param name Nome locale dell'agente
	 * @param maxPrice massimo prezzo a cui e' disposto ad acquistare
	 * @param quantity quantita' che desidera acquistare
	 */
	public AgentParticipantMulti(String name, int maxPrice, int quantity, String goodName){
		this.name = name;
		this.maxPrice = maxPrice;
		this.quantity = quantity;
		this.goodName = goodName;
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

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
}
