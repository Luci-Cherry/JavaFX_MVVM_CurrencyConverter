package application;

import java.text.ParseException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewModel {

	private Model model;
	public StringProperty amount = new SimpleStringProperty(); 
	public StringProperty from = new SimpleStringProperty();		
	public StringProperty to = new SimpleStringProperty();			

	public StringProperty fromAmountlbl = new SimpleStringProperty();		
	public StringProperty resultAmount = new SimpleStringProperty();		
	public StringProperty convertationDate = new SimpleStringProperty();	
	public StringProperty currentRate = new SimpleStringProperty();			
	public StringProperty warningsForConverterTab = new SimpleStringProperty();	
	
	public BooleanProperty buttonDisabled = new SimpleBooleanProperty();	
	
	public StringProperty baseCurrency = new SimpleStringProperty();
	public StringProperty addedCurrency = new SimpleStringProperty();
	public StringProperty lastTableUpdate = new SimpleStringProperty();   
	public StringProperty updFrequency = new SimpleStringProperty();		
	public StringProperty warningsForRatesTab = new SimpleStringProperty();	 
		
	public void connectTo(Model m) {
		this.model = m;
		lastTableUpdate.bind(model.lastTableUpdate);
	}
	
	// check input in amountField (non letters, non symbols except single dot or comma)
	public void checkAmount() {
		if (amount.getValue().matches("^[0-9]*[.,]?[0-9]+$")){
			buttonDisabled.set(false);
			warningsForConverterTab.setValue("");
		} else {
			buttonDisabled.set(true);
			warningsForConverterTab.setValue("Positive whole or fractional numbers only!");
		}
	}	
	
	// check input in updateFrequencyField (only for positive whole numbers)
	public void checkUpdateTime() {
		if (updFrequency.getValue().matches("\\d+")){		
			warningsForRatesTab.setValue("");
		} else {		
			warningsForRatesTab.setValue("Positive whole numbers from only!");
		}
	}

	// converting currencies and result formatting 
	public void convert() {
		
		String fromCode = from.get().substring(0,3);
		String toCode = to.get().substring(0,3);
		
		model.convert(amount.getValue(), fromCode, toCode);
		
		fromAmountlbl.set(amount.get() + fromCode + " = ");	
		resultAmount.setValue(model.getResult() + " " + toCode);			
		currentRate.setValue(1 + fromCode + " = " + model.getRate() + toCode);
		try {
			convertationDate.set("Last update: " + model.getTime());
		} catch (ParseException e) {
			convertationDate.setValue("Last update uncknown");
			e.printStackTrace();
		}	
	}

	// changing the base conversion currency in the table of rates
	public void changeRateTable() {
		String value = baseCurrency.getValue().substring(0,3);
		model.setBase(value);
		model.update_rates_list();
	}
	
	// adding currency to the table of rates
	public void addToRateTable() {
		String value = addedCurrency.getValue().substring(0,3);
		model.add_to_rates_list(value);
	}
	
	// setting the frequency of updating
	public void setUpdateFrequency() {			
		long time = 60000;
		if (updFrequency.getValue().matches("\\d+")) {
			time = Long.parseLong(updFrequency.getValue())*1000;
		} else {
			updFrequency.setValue("60");  // set default value
		}
		model.setUpdateFrequency(time);	
	}	
}
