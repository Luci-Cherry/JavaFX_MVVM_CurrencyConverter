package util;

import com.google.gson.JsonObject;

public class Currency extends Root {
	private String currency_name;	
	private String rate;
	private String rate_for_amount;
	
	public Currency(String name, String rate, String amount) {
		this.currency_name = name;
		this.rate = rate;
		this.rate_for_amount = amount;
	}
	
	public String getCurrency_name() {
		return currency_name;
	}

	public String getRate() {
		return rate;
	}

	public String getRate_for_amount() {
		return rate_for_amount;
	}
	
	public void parseCurrency(String to) {
		JsonObject rate_info = super.rates.get(to).getAsJsonObject();
		this.currency_name = rate_info.get("currency_name").getAsString();
		this.rate = rate_info.get("rate").getAsString();
		this.rate_for_amount = rate_info.get("rate_for_amount").getAsString();
	}
	
	@Override
	public String toString() {
		return ("base_currency_code = " + base_currency_code+ " - " + base_currency_name +
				"\ncurrency_name = " + currency_name +
				"\nresult = " + rate_for_amount);
	}
}
