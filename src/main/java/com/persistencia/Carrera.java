package com.persistencia;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Carrera
 *
 */
@Entity
@Table(name="CARRERAS")

public class Carrera implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CARRERA" )
	@SequenceGenerator(name = "SEQ_CARRERA", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(nullable=false,length=50,unique=true)
	private String nombre;
	
	@Column(nullable=false)
	private Boolean activo;
	
	public Carrera() {
		super();
	}

	public Carrera(Long id, String nombre, Boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Carrera [id=" + id + ", nombre=" + nombre + ", activo=" + activo + "]";
	}
	
	
   
}
