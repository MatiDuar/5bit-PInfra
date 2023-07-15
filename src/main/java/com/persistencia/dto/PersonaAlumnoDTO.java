package com.persistencia.dto;

import java.util.Date;


import com.persistencia.entities.Alumno;
import com.persistencia.entities.Carrera;

import com.persistencia.entities.Persona;

public class PersonaAlumnoDTO {

	private Long id;

	private String nombreUsuario;

	private String apellido1;

	private String nombre1;

	private String fechaNacimiento;

	private String direccion;

	private String mail;

	private Boolean activo;

	private Long idEstudiantil;

	private String carrera;

	public PersonaAlumnoDTO() {
		super();
	}

	public PersonaAlumnoDTO(Persona p) {
		this.id = p.getId();
		this.activo = p.getActivo();
		this.nombreUsuario = p.getNombreUsuario();
		this.nombre1 = p.getNombre1();
		this.apellido1 = p.getApellido1();
		this.fechaNacimiento = p.getFechaNacimiento().toString();
		this.direccion = p.getDireccion();
		this.mail = p.getMail();
		
	}
	
	public PersonaAlumnoDTO(Alumno a) {
		this.id = a.getId();
		this.activo = a.getActivo();
		this.nombreUsuario = a.getNombreUsuario();
		this.nombre1 = a.getNombre1();
		this.apellido1 = a.getApellido1();
		this.fechaNacimiento = a.getFechaNacimiento().toString();
		this.direccion = a.getDireccion();
		this.mail = a.getMail();
		this.idEstudiantil=a.getIdEstudiantil();
		this.carrera=a.getCarrera().getNombre();
		
	}
	
	

	public PersonaAlumnoDTO(Long id, String nombreUsuario, String apellido1, String nombre1, java.util.Date fechaNacimiento,
			String direccion, String mail, Boolean activo, Long idEstudiantil, String carrera) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.apellido1 = apellido1;
		this.nombre1 = nombre1;
		this.fechaNacimiento = fechaNacimiento.toString();
		this.direccion = direccion;
		this.mail = mail;
		this.activo = activo;
		this.idEstudiantil = idEstudiantil;
		this.carrera = carrera;
	}
	
	public PersonaAlumnoDTO(Long id, String nombreUsuario, String apellido1, String nombre1, java.util.Date fechaNacimiento,
			String direccion, String mail, Boolean activo) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.apellido1 = apellido1;
		this.nombre1 = nombre1;
		this.fechaNacimiento = fechaNacimiento.toString();
		this.direccion = direccion;
		this.mail = mail;
		this.activo = activo;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Long getIdEstudiantil() {
		return idEstudiantil;
	}

	public void setIdEstudiantil(Long idEstudiantil) {
		this.idEstudiantil = idEstudiantil;
	}

	public String groupCarreraMod() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getCarrera() {
		return carrera;
	}

	

}
