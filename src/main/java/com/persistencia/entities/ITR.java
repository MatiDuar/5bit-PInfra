package com.persistencia.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="ITR")
public class ITR implements Serializable {

	public ITR() {
		super();
	} 
	
	
	public ITR(Long id, String nombre, Departamento departamento, Boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.departamento = departamento;
		this.activo = activo;
	}
	public ITR( String nombre, Departamento departamento, Boolean activo) {
		super();
		this.nombre = nombre;
		this.departamento = departamento;
		this.activo = activo;
	}


	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITR" )
	@SequenceGenerator(name = "SEQ_ITR", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(length=50,nullable=false,unique=true)
	private String nombre;
	
	@ManyToOne(optional=false)
	private Departamento departamento;
	
	@Column(nullable=false)
	private Boolean activo;

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

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "ITR [id=" + id + ", nombre=" + nombre + ", departamento=" + departamento + ", activo=" + activo + "]";
	}
	
	
	
	
   
}
