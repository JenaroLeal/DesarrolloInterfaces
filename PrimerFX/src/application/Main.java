package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			/*Button btn = new Button ("Click aqui");
			btn.setOnAction(new EventHandler<ActionEvent>(){
				
				public void handle(ActionEvent event) {
					System.out.println("Hola mundo");
				}
				
			});
			
			Label lbl = new Label("Hola mundo");
			StackPane panel = new StackPane();
			
			panel.setAlignment(lbl,Pos.TOP_CENTER);
			panel.setAlignment(btn,Pos.CENTER);
			
			panel.getChildren().addAll(lbl,btn);
			*/
			
			Button b1 = new Button("Botón 1");
			Button b2 = new Button("Botón 2");
			Button b3 = new Button("Botón 3");
			
			VBox panel =new VBox();
			panel.getChildren().addAll(b1,b2,b3);
			Scene scene = new Scene(panel,400,500);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
