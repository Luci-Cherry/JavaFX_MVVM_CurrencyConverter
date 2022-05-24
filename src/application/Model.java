package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.*;

public class Model {
	
	private static String API_KEY;
	static {
		try (BufferedReader br = new BufferedReader(new FileReader("src\\util\\API_key.txt"))){
			API_KEY = br.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static ObservableList<Currency> ratesList = FXCollections.observableList(new ArrayList<Currency>());
	private static Set<String> currency_set = new HashSet<>();	// set of default currencies for the table of rates
	static {
		currency_set.add("EUR");
		currency_set.add("GBP");
		currency_set.add("CHF");
	}
	
	Currency currency_rate;		// variable containing valid rate between two currencies, converting amount, etc.

	private String base = "USD";
	private long updateFrequency = 60000;
	public SimpleStringProperty lastTableUpdate = new SimpleStringProperty();
	
	public void setBase(String base) {
		this.base = base;
	}
	public String getBase() {
		return this.base;
	}
	
	public long getUpdateFrequency() {
		return updateFrequency;
	}
	public void setUpdateFrequency(long updateFrequency) {
		this.updateFrequency = updateFrequency;
	}
	
	public String getResult() {
		return currency_rate.getRate_for_amount();
	}	
	public String getRate() {
		return currency_rate.getRate();
	}
	
	public String getTime() throws ParseException {
		SimpleDateFormat previousFormat = new SimpleDateFormat ("yyyy-MM-dd", Locale.ENGLISH);
		Date date = previousFormat.parse(currency_rate.getUpdated_date());
		SimpleDateFormat newFormat = new SimpleDateFormat ("dd MMMM yyyy", Locale.ENGLISH);
		String newTime = newFormat.format(date);
		
		return newTime;
	}
	
	// parsing Currency object from API
	public void convert (String amount, String from, String to) {  
		String url = getUrl(amount, from, to);
		GsonParser parser = new GsonParser();
		this.currency_rate = parser.parseCurrencyRoot(url, to);	
	}
	
	public Currency convert (String from, String to) {  
		String url = getUrl("1", from, to);
		GsonParser parser = new GsonParser();
		Currency currency_rate = parser.parseCurrencyRoot(url, to);
		return currency_rate;
	}
	
	static String getUrl(String amount, String from, String to) {
		String url = String.format("https://api.getgeoapi.com/v2/currency/convert?api_key=%s&from=%s&to=%s&amount=%s&format=json", API_KEY, from, to, amount);
		return url;
	}
		
	// for completing the table of rates with the default currencies
	public void complete_rates_list(String base) {
		this.base = base;
		for (String s : currency_set) {
			ratesList.add(convert(base, s));
		}
	}
		
	// for adding the currency to the table of rates
	public void add_to_rates_list(String to) {
		ratesList.add(convert(base, to));
		currency_set.add(to);
	}
	
	// updating rates for the table of rates
	public void update_rates_list() {
		ratesList.clear();
		complete_rates_list(base);
		lastTableUpdate.setValue(new java.text.SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.ENGLISH).format(new Date()));
	}
}
