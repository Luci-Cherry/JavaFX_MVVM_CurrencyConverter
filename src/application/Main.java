package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.RateUpdate;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Model model = new Model();
			RateUpdate rateUpdate = new RateUpdate(model);
			rateUpdate.start();
			ViewModel viewModel = new ViewModel();
			viewModel.connectTo(model);
			
			FXMLLoader fxl = new FXMLLoader();
			Parent root = fxl.load(getClass().getResource("ViewStage.fxml").openStream());
			View view = fxl.getController();
			view.bind(viewModel);
			
			Scene scene = new Scene (root);
			
			primaryStage.setTitle("Currency Converter");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.maxWidthProperty().bind(primaryStage.widthProperty());
			primaryStage.minWidthProperty().bind(primaryStage.widthProperty());
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent event) {
	            	rateUpdate.interrupt();
	                System.exit(0);
	            }
	        });
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
