package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class PeliculasController {

	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtDuracion;
	@FXML
	private ChoiceBox opcionGenero;
	@FXML
	private ChoiceBox opcionPegi;
	@FXML
	private TableView<Pelicula> tablePeliculas;
	@FXML
	private TableColumn<Pelicula, String> columNombre;
	@FXML
	private TableColumn<Pelicula, Integer> columDuracion;
	@FXML
	private TableColumn<Pelicula, String> columGenero;
	@FXML
	private TableColumn<Pelicula, String> columPegi;
	@FXML
	private Button btnAñadir;
	@FXML
	private Button btnBorrar;

	private ObservableList<Pelicula> listaPeliculas = FXCollections
			.observableArrayList(new Pelicula("Tarzán", 120, "Aventura", "+3"));
	public ObservableList<String> generos = FXCollections.observableArrayList("Accion", "Aventura", "Drama", "Comedia",
			"Sci-Fi");
	public ObservableList<String> pegis = FXCollections.observableArrayList("+3", "+7", "+12", "+18");

	@FXML
	private void initialize() {
		opcionGenero.setItems(generos);
		opcionPegi.setItems(pegis);
		columNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		columDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
		columGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
		columPegi.setCellValueFactory(new PropertyValueFactory<>("pegi"));
		
		ObservableList listaPeliculasBD=getPeliculasBD();
		
		tablePeliculas.setItems(listaPeliculas);
		tablePeliculas.setItems(listaPeliculasBD);
	}

	private ObservableList<Pelicula> getPeliculasBD() {

		ObservableList<Pelicula> listaPeliculasBD = FXCollections.observableArrayList();

		DatabaseConnection dbConnection = new DatabaseConnection();
		Connection connection = dbConnection.getConnection();
		String query = "select * from peliculas";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Pelicula p = new Pelicula(rs.getString("nombre"), rs.getInt("duracion"), rs.getString("genero"),
						rs.getString("pegi"));
				listaPeliculasBD.add(p);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaPeliculasBD;

	}

	@FXML
	public void añadirPelicula(ActionEvent event) {

		if (txtNombre.getText().isEmpty() || txtDuracion.getText().isEmpty()
				|| opcionGenero.getSelectionModel().isEmpty() || opcionPegi.getSelectionModel().isEmpty()) {

			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setHeaderText("Faltan datos por insertar");
			alerta.setContentText("Completa todos los campos");
			alerta.showAndWait();
		} else {
			if (esNumero(txtDuracion.getText())) {
				Pelicula p = new Pelicula(txtNombre.getText(), Integer.parseInt(txtDuracion.getText()),
						opcionGenero.getValue().toString(), opcionPegi.getValue().toString());
				listaPeliculas.add(p);

				txtNombre.clear();
				txtDuracion.clear();
				opcionGenero.getSelectionModel().clearSelection();
				opcionPegi.getSelectionModel().clearSelection();

				DatabaseConnection dbConnection = new DatabaseConnection();
				Connection connection = dbConnection.getConnection();
				String query = "insert into peliculas (nombre, duracion, genero, pegi)" + "values (?,?,?,?)";

				try {
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setString(1, p.getNombre());
					ps.setInt(2, p.getDuracion());
					ps.setString(3, p.getGenero());
					ps.setString(4, p.getPegi());
					ps.executeUpdate();
					connection.close();
					ObservableList listaPeliculasBd = getPeliculasBD();
					tablePeliculas.setItems(listaPeliculasBd);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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

	public void borrarPelicula(ActionEvent event) {
		int indice = tablePeliculas.getSelectionModel().getSelectedIndex();
		if (indice < 0) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error al borrar");
			alerta.setHeaderText("No se puede borrar");
			alerta.setContentText("Por favor elige una fila");
			alerta.showAndWait();
		} else {
			tablePeliculas.getItems().remove(indice);
		}
	}

}
