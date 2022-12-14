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

public class IndexController {

	@FXML
	private TextField txtTitulo;

	@FXML
	private ChoiceBox cbEditorial;

	@FXML
	private TextField txtAutor;

	@FXML
	private TextField txtPaginas;

	@FXML
	private TableView<Libro> tableLibros;

	@FXML
	private TableColumn<Libro, String> columnTitulo;

	@FXML
	private TableColumn<Libro, String> columnEditorial;

	@FXML
	private TableColumn<Libro, String> columnAutor;

	@FXML
	private TableColumn<Libro, Integer> columnPaginas;

	@FXML
	private Button btnAnadir;

	@FXML
	private Button btnBorrar;

	private ObservableList<Libro> listaLibros = FXCollections
			.observableArrayList();

	public ObservableList<String> listaEditoriales = FXCollections.observableArrayList("Planeta", "Altaya", "Kadokawa",
			"Penguin Libros");

	@FXML
	private void initialize() {

		cbEditorial.setItems(listaEditoriales);

		columnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		columnEditorial.setCellValueFactory(new PropertyValueFactory<>("editorial"));
		columnAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
		columnPaginas.setCellValueFactory(new PropertyValueFactory<>("paginas"));
		
		ObservableList listaLibrosBD = getLibrosBD();
		
		tableLibros.setItems(listaLibros);
		tableLibros.setItems(listaLibrosBD);
	}

	private ObservableList<Libro> getLibrosBD() {

		ObservableList<Libro> listaLibrosBD = FXCollections.observableArrayList();

		DatabaseConnection dbConnection = new DatabaseConnection();
		Connection connection = dbConnection.getConnection();

		String query = "select * from libros";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Libro libro = new Libro(rs.getInt("id"),rs.getString("titulo"), rs.getString("editorial"), rs.getString("autor"),
						rs.getInt("paginas"));

				listaLibrosBD.add(libro);
			}

			connection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return listaLibrosBD;
	}

	@FXML
	public void anadirLibro(ActionEvent event) {

		if (txtTitulo.getText().isEmpty() || cbEditorial.getSelectionModel().isEmpty() || txtAutor.getText().isEmpty()
				|| txtPaginas.getText().isEmpty()) {

			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setHeaderText("Faltan datos por insertar");
			alerta.setContentText("Completa todos los campos");
			alerta.showAndWait();
		} else {
			if (esNumero(txtPaginas.getText())) {
				Libro l = new Libro(txtTitulo.getText(), cbEditorial.getValue().toString(), txtAutor.getText(),
						Integer.parseInt(txtPaginas.getText()));

				listaLibros.add(l);
				txtTitulo.clear();
				cbEditorial.getSelectionModel().clearSelection();
				txtAutor.clear();
				txtPaginas.clear();
				
				
				DatabaseConnection dbConnection = new DatabaseConnection();
				Connection connection = dbConnection.getConnection();
				
				String query = "insert into libros ( titulo, editorial, autor, paginas)"+"values (?,?,?,?)";
				
				try {
					PreparedStatement ps = connection.prepareStatement(query);
					
					ps.setString(1, l.getTitulo());
					ps.setString(2, l.getEditorial());
					ps.setString(3, l.getAutor());
					ps.setInt(4, l.getPaginas());
					ps.executeUpdate();
					connection.close();
					ObservableList listaLibrosBD = getLibrosBD();
					tableLibros.setItems(listaLibrosBD);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error al insertar");
				alerta.setHeaderText("No se ha introducido un n??mero en las p??ginas");
				alerta.setContentText("Por favor introduzca un n??mero en las p??ginas");
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

	public void borrarLibro(ActionEvent event) {
		int indiceSeleccionado = tableLibros.getSelectionModel().getSelectedIndex();
		if (indiceSeleccionado < 0) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error al borrar");
			alerta.setHeaderText("No se puede borrar");
			alerta.setContentText("Por favor elige una fila");
			alerta.showAndWait();
		} else {
			//tableLibros.getItems().remove(indiceSeleccionado);
			
			DatabaseConnection dbConnection = new DatabaseConnection();
			Connection connection = dbConnection.getConnection();
						
			try {
				String query = "Delete from libros where id = ?";
				PreparedStatement ps = connection.prepareStatement(query);
				Libro libro = tableLibros.getSelectionModel().getSelectedItem();
				ps.setInt(1, libro.getId());
				ps.executeUpdate();
				
				tableLibros.getSelectionModel().clearSelection();
				ObservableList listaLibrosBD= getLibrosBD();
				tableLibros.setItems(listaLibrosBD);
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
