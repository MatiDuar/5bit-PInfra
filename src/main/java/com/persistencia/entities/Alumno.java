package com.persistencia.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;


/**
 * Entity implementation class for Entity: Alumno
 *
 */
@Entity
@Table(name="ALUMNOS")
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Alumno extends Persona implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Column(nullable=false,length=10,unique=true)
	private Long idEstudiantil;
	
	@ManyToOne(optional=false)
	private Carrera carrera;
	
	@ManyToOne(optional=false)
	private ITR itr;
	
	public Alumno() {
		super();
	}

	public Alumno(Long id, String nombreUsuario, String contrasena, String apellido1, String apellido2, String nombre1,
			String nombre2, Date fechaNacimiento, String direccion, String mail, Boolean activo, Long idEstudiantil, Carrera carrera, ITR itr, boolean admin) {
		super(id, nombreUsuario, contrasena, apellido1, apellido2, nombre1, nombre2, fechaNacimiento, direccion, mail, activo, admin);
		this.idEstudiantil = idEstudiantil;
		this.carrera = carrera;
		this.itr = itr;
	}


	public Long getIdEstudiantil() {
		return idEstudiantil;
	}

	public void setIdEstudiantil(Long idEstudiantil) {
		this.idEstudiantil = idEstudiantil;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public ITR getItr() {
		return itr;
	}

	public void setItr(ITR itr) {
		this.itr = itr;
	}

	
	
	
   
}
