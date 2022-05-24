package util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("unchecked")
public class CurrenciesList {
	private static Map<String, String> CYmap = new HashMap<>();
	private static List<String> currenciesList = new ArrayList<>();
	
	static {
		Gson gson = new Gson();
		try (FileReader reader = new FileReader ("src\\util\\currencies.json")){
			CYmap = gson.fromJson(reader, Map.class);
			} catch (IOException e) {
			System.out.println("Parsing error" + e.getMessage());
			}
		
		for (Map.Entry<String, String> pair : CYmap.entrySet()) {
			currenciesList.add(pair.getKey() + " - " + pair.getValue());
		}
	}
	
	public static final ObservableList<String> FULL_list = FXCollections.observableList(CurrenciesList.currenciesList);
}
