package io.pivotal.domain;

public class PriceQuote {

	private String symbol;
	private long quoteTime;
	private int lastPriceInPennies;

	public PriceQuote() {}
	
	public PriceQuote(String symbol, long quoteTime, int lastPriceInPennies) {
		super();
		this.symbol = symbol;
		this.quoteTime = quoteTime;
		this.lastPriceInPennies = lastPriceInPennies;
	}

	public long getQuoteTime() {
		return quoteTime;
	}

	public int getLastPrice() {
		return lastPriceInPennies;
	}

	public String getSymbol() {
		return symbol;
	}
	
	
	/*
	 * setters for serialization
	 */

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setQuoteTime(long quoteTime) {
		this.quoteTime = quoteTime;
	}

	public void setLastPriceInPennies(int lastPriceInPennies) {
		this.lastPriceInPennies = lastPriceInPennies;
	}
	
}
