package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Pelicula {
	
	private SimpleStringProperty nombre;
	private SimpleIntegerProperty duracion;
	private SimpleStringProperty genero;
	private SimpleStringProperty pegi;
	
	public Pelicula() {
		
	}
	
	
	
	public Pelicula (String nombre, int duracion, String genero, String pegi) {
		super();
		this.nombre=new SimpleStringProperty(nombre);
		this.duracion=new SimpleIntegerProperty(duracion);
		this.genero=new SimpleStringProperty(genero);
		this.pegi=new SimpleStringProperty(pegi);
	}


	public String getNombre() {
		return nombre.get();
	}


	public void setNombre(String nombre) {
		this.nombre = new SimpleStringProperty(nombre);
	}


	public int getDuracion() {
		return duracion.get();
	}


	public void setDuracion(int duracion) {
		this.duracion = new SimpleIntegerProperty(duracion);
	}


	public String getGenero() {
		return genero.get();
	}


	public void setGenero(String genero) {
		this.genero = new SimpleStringProperty(genero);
	}


	public String getPegi() {
		return pegi.get();
	}


	public void setPegi(String pegi) {
		this.pegi = new SimpleStringProperty(pegi);
	}
	
	

}
