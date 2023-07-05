package com.persistencia.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@Table(name="PERSONAS")
@Inheritance(strategy=InheritanceType.JOINED)
public class Persona implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO" )
	@SequenceGenerator(name = "SEQ_USUARIO", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(nullable=false,length=50,unique=true)
	private String nombreUsuario;
	
	@Column(nullable=false,length=50)
	private String contrasena;
	
	@Column(nullable=false,length=50)
	private String apellido1;
	
	@Column(length=50)
	private String apellido2;
	
	@Column(nullable=false,length=50)
	private String nombre1;
	
	@Column(length=50)
	private String nombre2;
	
	@Column(nullable=false)
	private Date fechaNacimiento;
	
	@Column(nullable=false,length=150)
	private String direccion;
	
	@Column(nullable=false,length=50)
	private String mail;
	
	@Column(nullable=false)
	private Boolean activo;
	
	public Persona() {
		super();
	}

	
	public Persona(Long id, String nombreUsuario, String contrasena, String apellido1, String apellido2, String nombre1,
			String nombre2, Date fechaNacimiento, String direccion, String mail, Boolean activo) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nombre1 = nombre1;
		this.nombre2 = nombre2;
		this.fechaNacimiento = fechaNacimiento;
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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
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


	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombreUsuario=" + nombreUsuario + ", contrasena=" + contrasena + ", apellido1="
				+ apellido1 + ", apellido2=" + apellido2 + ", nombre1=" + nombre1 + ", nombre2=" + nombre2
				+ ", fechaNacimiento=" + fechaNacimiento + ", direccion=" + direccion + ", mail=" + mail + ", activo="
				+ activo + "]";
	}
	
	
	
	
	
   
}