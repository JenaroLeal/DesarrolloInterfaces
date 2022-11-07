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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ejercicio2 extends Application{
	public void start(Stage primaryStage) {
		try {
			
			BorderPane panel = new BorderPane();
			
			Rectangle centro = new Rectangle (300,300,Color.DARKBLUE);
			Rectangle arriba = new Rectangle (400,50,Color.AQUA);
			Rectangle abajo = new Rectangle (400,50,Color.AQUA);
			Rectangle izquierda = new Rectangle (50,300,Color.WHITE);
			Rectangle derecha = new Rectangle (50,300,Color.WHITE);
			
			panel.setCenter(centro);
			panel.setTop(arriba);
			panel.setBottom(abajo);
			panel.setRight(derecha);
			panel.setLeft(izquierda);
			
		
			
			
			Scene scene = new Scene (panel,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
