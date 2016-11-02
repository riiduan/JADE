package util;

/**
 * Classe che modella i parametri dell'iniziatore del protocollo
 *
 */
public class AgentInitiatorMulti {
	//nome dell'agente iniziatore
	private String agentName;
	//nome del bene/oggetto dell'asta
	private String goodName;
	//prezzo di partenza dell'asta
	private int startPrice;
	//prezzo di riserva dell'asta
	private int reservePrice;
	//quantita' del bene all'asta
	private int quantity;
	//ribasso dell'asta ad ogni step
	private int dif;
	//tempo di attesa in milli secondi per la ricezioni di messaggi di proste
	private int msWait;
	
	/**
	 * 
	 * @param goodName Nome locale dell'agente
	 * @param startPrice Prezzo iniziale dell'asta
	 * @param reservePrice Prezzo di riserva dell'asta
	 * @param quantity Quantita' messa all'asta
	 */
	public AgentInitiatorMulti(String goodName, int startPrice, int reservePrice, int dif, int quantity, int msWait, String agentName){
		this.goodName = goodName;
		this.startPrice = startPrice;
		this.reservePrice = reservePrice;
		this.dif = dif;
		this.quantity = quantity;
		this.msWait = msWait;
		this.agentName = agentName;
	}

//	metodi get e set
	public int getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}

	public int getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(int reservePrice) {
		this.reservePrice = reservePrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getDif() {
		return dif;
	}

	public void setDif(int dif) {
		this.dif = dif;
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

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

}