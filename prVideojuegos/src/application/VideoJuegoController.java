package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private TableView<Videojuego> tableJuego;
	@FXML
	private TableColumn<Videojuego, String> columNombre;
	@FXML
	private TableColumn<Videojuego, Integer> columPrecio;
	@FXML
	private TableColumn<Videojuego, String> columConsola;
	@FXML
	private TableColumn<Videojuego, String> columPegi;
	@FXML
	private Button btnAñadir;

	private ObservableList<Videojuego> listaVideojuegos = FXCollections
			.observableArrayList(new Videojuego("Fifa 23", 49, "PSP5", "PEGI 3"));

	public ObservableList<String> consolas = FXCollections.observableArrayList("PSP5", "PSP4", "Nintendo", "XBOX");
	public ObservableList<String> pegis = FXCollections.observableArrayList("PEGI 3", "PEGI 7", "PEGI 12", "PEGI 16",
			"PEGI 18");

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

	@FXML
	public void añadirJuego(ActionEvent event) {

		if (txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty()
				|| opcionConsola.getSelectionModel().isEmpty() || opcionPegi.getSelectionModel().isEmpty()) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setHeaderText("Faltan datos por insertar");
			alerta.setContentText("Completa todos los campos");
			alerta.showAndWait();
		} else {
			if (esNumero(txtPrecio.getText())) {
				Videojuego v = new Videojuego(txtNombre.getText(), Integer.parseInt(txtPrecio.getText()),
						opcionConsola.getValue().toString(), opcionPegi.getValue().toString());
				listaVideojuegos.add(v);

				txtNombre.clear();
				txtPrecio.clear();
				opcionConsola.getSelectionModel().clearSelection();
				opcionPegi.getSelectionModel().clearSelection();
			} else {
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error al insertar");
				alerta.setHeaderText("No se ha introducido un número en el precio");
				alerta.setContentText("Por favor introduzca un número en el precio");
				alerta.showAndWait();
			}
		}

	}

	public boolean esNumero(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	public void borrarVideojuego(ActionEvent event) {
		int indiceSeleccionado = tableJuego.getSelectionModel().getSelectedIndex();
		if(indiceSeleccionado<0) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error al borrar");
			alerta.setHeaderText("No se puede borrar");
			alerta.setContentText("Por favor elige una fila");
			alerta.showAndWait();
		}else {
		tableJuego.getItems().remove(indiceSeleccionado);
		}
	}

}
