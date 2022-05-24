package util;

import com.google.gson.JsonObject;

// Root are using for initial parsing from json-file

public class Root {
	protected String base_currency_code;
	protected String base_currency_name;
	private	String amount;
	private	String updated_date;
	protected JsonObject rates;
	private	String status;
	
	public String getBase_currency_code() {
		return base_currency_code;
	}

	public String getBase_currency_name() {
		return base_currency_name;
	}

	public String getAmount() {
		return amount;
	}

	public String getUpdated_date() {
		return updated_date;
	}

	public JsonObject getRatesObject() {
		return rates;
	}

	public String getStatus() {
		return status;
	}
	
	@Override
	public String toString () {
		return ("updated_date = " + updated_date + "; base_currency_code = " + base_currency_code + "; amount = " + amount + "; JsonObject = " + rates);
	}
}
