package application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SampleController {
	
	@FXML
	private TextField txtNombre;
	
	@FXML 
	private PasswordField txtPassword;
	
	@FXML
	private Button mostrarUser;
	
	@FXML
	private Label lblResult;
	
	@FXML 
	private ImageView foto;
	
	
	public void mostrarUser (ActionEvent event) {
		
		lblResult.setText("Bienvenid@"+txtNombre.getText());
		txtNombre.setText("");
		txtPassword.setText("");
	}
	
	
}
