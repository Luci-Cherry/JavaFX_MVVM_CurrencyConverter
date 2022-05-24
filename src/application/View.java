package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import util.CurrenciesList;
import util.Currency;

public class View implements Initializable{
	
	ViewModel viewModel;
	
	@FXML
	private TextField amountField;
	@FXML
	private ComboBox<String> fromComboBox;
	@FXML
	private ComboBox<String> toComboBox;
	@FXML
	private Button invertButton;
	@FXML
	private Button convertButton;
	@FXML
	private Label fromAmountLabel;
	@FXML
	private Label resultAmountLabel;
	@FXML
	private Label currentRateLabel;
	@FXML
	private Label lastUpdLabel;
	@FXML
	private ComboBox<String> baseCurComboBox;
	@FXML
	private ComboBox<String> addCurComboBox;
	@FXML
	private TableView<Currency> ratesTable;
	@FXML
	private TableColumn<Currency, String> currencyColumn;
	@FXML
	private TableColumn<Currency, String> rateColumn;
	@FXML
    private TextField updFrequencyTextField;  
	@FXML
    private Label lastTableUpdate;		
    @FXML
    private Label warningsForConverterTab;
    @FXML
    private Label warningsForRatesTab;
    @FXML
	private Button setButton;
	   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		fromComboBox.setItems(CurrenciesList.FULL_list); 
		toComboBox.setItems(CurrenciesList.FULL_list);
		baseCurComboBox.setItems(CurrenciesList.FULL_list);
		addCurComboBox.setItems(CurrenciesList.FULL_list);
		
		fromComboBox.setValue("USD - United States Dollar");
		toComboBox.setValue("EUR - Euro");
		
		amountField.textProperty().addListener((v, oldText, newText) -> {
			viewModel.checkAmount();
		});	
		
		updFrequencyTextField.textProperty().addListener((v, oldText, newText) -> {
			viewModel.checkUpdateTime();
		});
		
		currencyColumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("currency_name"));
		rateColumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("rate"));
		ratesTable.setItems(Model.ratesList);
	}
	
	@FXML
	void onInvertBtn(ActionEvent event) {
		String temp = fromComboBox.getValue();
		fromComboBox.setValue(toComboBox.getValue());;
		toComboBox.setValue(temp);;
	}

	@FXML
	void onConvertBtn(ActionEvent event) {		
		if (amountField.getText().contains(",")) {
			amountField.setText(amountField.getText().replace(",", "."));
		} 
		viewModel.convert();
	}

	@FXML
	void onBaseCurComboBox(ActionEvent event) {
		viewModel.changeRateTable(); 
	}
	
	@FXML
	void onAddCurComboBox(ActionEvent event) {
		viewModel.addToRateTable();   
	}
	
    @FXML
    void onSetUpdateTime(ActionEvent event) { 
    	viewModel.setUpdateFrequency();
    }

	public void bind (ViewModel vm) {
		this.viewModel = vm;
		
		viewModel.amount.bind(amountField.textProperty());
		warningsForConverterTab.textProperty().bind(viewModel.warningsForConverterTab);
		convertButton.disableProperty().bind(viewModel.buttonDisabled);
		
		viewModel.from.bind(fromComboBox.valueProperty());
		viewModel.to.bind(toComboBox.valueProperty());
		
		fromAmountLabel.textProperty().bind(viewModel.fromAmountlbl);
		resultAmountLabel.textProperty().bind(viewModel.resultAmount);
		currentRateLabel.textProperty().bind(viewModel.currentRate);
		lastUpdLabel.textProperty().bind(viewModel.convertationDate);
		
		viewModel.baseCurrency.bind(baseCurComboBox.valueProperty());
		viewModel.addedCurrency.bind(addCurComboBox.valueProperty());
		
		viewModel.updFrequency.bindBidirectional(updFrequencyTextField.textProperty());
		lastTableUpdate.textProperty().bindBidirectional(viewModel.lastTableUpdate);
		warningsForRatesTab.textProperty().bind(viewModel.warningsForRatesTab);
	}
}
