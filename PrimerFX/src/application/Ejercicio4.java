package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ejercicio4 extends Application {
	public void start(Stage primaryStage) {
		try {
			
			VBox panel = new VBox(15);
			panel.setPadding(new Insets(15));
			
			Label nombre = new Label ("Nombre");
			TextField lbltxt = new TextField();
			Label password = new Label ("Contraseña");
			PasswordField pass = new PasswordField();
			
			Label result = new Label();
			
			
			Button entrar = new Button("Entrar");
			entrar.setOnAction(new EventHandler<ActionEvent>(){				
				public void handle(ActionEvent event) {
					String nombre = lbltxt.getText();
					result.setText("Hola, bienvenid@"+nombre);
				}
				
				
			});
			
			panel.getChildren().addAll(nombre,lbltxt,password,pass,entrar,result);
			
	

			Scene scene = new Scene(panel, 200, 200);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}