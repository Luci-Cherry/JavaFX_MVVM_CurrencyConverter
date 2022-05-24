package util;

import java.util.Date;
import java.util.Locale;

import application.Model;

//use for recurring updating the table of rates

// Has bag: "Exception in thread "Thread-3" java.lang.IllegalStateException: Not on FX application thread; currentThread = Thread-3"

public class RateUpdate extends Thread {
	Model model;
	public RateUpdate(Model model) {
		this.model = model;
	}
	int i = 0;
	@Override
	public void run() {	
		try {	
			while (!isInterrupted()) {
				model.update_rates_list();
				model.lastTableUpdate.setValue(new java.text.SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.ENGLISH).format(new Date()));
				Thread.sleep(model.getUpdateFrequency());	
			}	
		} catch (InterruptedException e) {		
				e.printStackTrace();
		}		
	}
}
