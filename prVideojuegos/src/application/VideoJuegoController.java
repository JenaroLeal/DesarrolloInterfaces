package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class VideoJuegoController {
	
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtPrecio;
	@FXML
	private ChoiceBox opcionConsola;
	@FXML
	private ChoiceBox opcionPegi;
	@FXML
	private Button btnAñadir;
	@FXML
	private TableView <Videojuego>tableJuego;
	@FXML
	private TableColumn <Videojuego, String> columNombre;
	@FXML
	private TableColumn <Videojuego, Integer> columPrecio;
	@FXML
	private TableColumn <Videojuego, String> columConsola;
	@FXML
	private TableColumn <Videojuego, String> columPegi;
	
	private ObservableList<Videojuego> listaVideojuegos =FXCollections.observableArrayList(new Videojuego("Fifa 23", 49, "PSP5", "PEGI 3"));

	public 	ObservableList<String> consolas = FXCollections.observableArrayList("PSP5","PSP4", "Nintendo", "XBOX");
	public 	ObservableList<String> pegis= FXCollections.observableArrayList("PEGI 3","PEGI 7", "PEGI 12", "PEGI 16", "PEGI 18");
	
	@FXML
	private void initialize() {
		opcionConsola.setItems(consolas);
		opcionPegi.setItems(pegis);
		columNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		columPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		columConsola.setCellValueFactory(new PropertyValueFactory<>("consola"));
		columPegi.setCellValueFactory(new PropertyValueFactory<>("pegi"));
		
		tableJuego.setItems(listaVideojuegos);
		
		
	}
	
}
