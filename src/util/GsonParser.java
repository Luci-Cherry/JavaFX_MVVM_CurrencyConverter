package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;

public class GsonParser {
	
	public Currency parseCurrencyRoot(String url, String to) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));){	
			Currency root = new Gson().fromJson(reader, Currency.class);
			root.parseCurrency(to);
			return root;			
		} catch (IOException e) {
			System.out.println("Parsing error" + e.getMessage());
		}
		return null;
	}
}
