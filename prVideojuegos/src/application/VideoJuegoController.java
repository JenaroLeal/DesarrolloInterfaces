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
	@FXML
	private Button btnBorrar;

	private ObservableList<Videojuego> listaVideojuegos = FXCollections
			.observableArrayList();

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

		ObservableList listaVideojuegosBD=getVideojuegosBD();
		
		
		tableJuego.setItems(listaVideojuegos);
		tableJuego.setItems(listaVideojuegosBD);

	}
	
	
	private ObservableList<Videojuego> getVideojuegosBD(){
		
		ObservableList<Videojuego> listaVideojuegosBD = FXCollections.observableArrayList();
		DatabaseConnection dbConnection = new DatabaseConnection();
		Connection connection = dbConnection.getConnection();
		
		String query ="select * from videojuegos";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Videojuego v = new Videojuego(rs.getInt("id"),rs.getString("nombre"),rs.getInt("precio"),rs.getString("consola"),rs.getString("pegi"));
				listaVideojuegosBD.add(v);
			}
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return listaVideojuegosBD;
		
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
				
				DatabaseConnection dbConnection = new DatabaseConnection();
				Connection connection = dbConnection.getConnection();
				
				String query = "insert into videojuegos (nombre, precio, consola, pegi)"+"values(?,?,?,?)";
				
				try {
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setString(1, v.getNombre());
					ps.setInt(2, v.getPrecio());
					ps.setString(3, v.getConsola());
					ps.setString(4, v.getPegi());
					ps.executeUpdate();
					connection.close();
					ObservableList listaVideojuegosBD = getVideojuegosBD();
					tableJuego.setItems(listaVideojuegosBD);
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
	public void borrarVideojuego(ActionEvent event) {
		int indiceSeleccionado = tableJuego.getSelectionModel().getSelectedIndex();
		if(indiceSeleccionado<0) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error al borrar");
			alerta.setHeaderText("No se puede borrar");
			alerta.setContentText("Por favor elige una fila");
			alerta.showAndWait();
		}else {
			DatabaseConnection dbConnection = new DatabaseConnection();
			Connection connection = dbConnection.getConnection();
			
			try {
				
				String query = "Delete from videojuegos where id = ?";
				PreparedStatement ps = connection.prepareStatement(query);
				Videojuego videojuego = tableJuego.getSelectionModel().getSelectedItem();
				ps.setInt(1, videojuego.getId());
				ps.executeUpdate();
				
				tableJuego.getSelectionModel().clearSelection();
				ObservableList listaJuegosBD=getVideojuegosBD();
				tableJuego.setItems(listaJuegosBD);
				connection.close();
				
				
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
	}

}
