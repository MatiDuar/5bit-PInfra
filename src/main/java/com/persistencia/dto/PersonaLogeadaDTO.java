package com.persistencia.dto;

import java.sql.Date;

import javax.persistence.Column;

public class PersonaLogeadaDTO {

    private Long id;
	private String nombreUsuario;
	private String contrasena;
	private String apellido1;
	private String apellido2;
	private String nombre1;
	private String nombre2;
	private Date fechaNacimiento;
	private String direccion;
	private String mail;
	private Boolean activo;
	private Boolean admin;
	private String token;
	
	public PersonaLogeadaDTO(Long id, String nombreUsuario, String contrasena, String apellido1, String apellido2,
			String nombre1, String nombre2, Date fechaNacimiento, String direccion, String mail, Boolean activo,
			Boolean admin, String token) {
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
		this.admin = admin;
		this.token = token;
	}

	public PersonaLogeadaDTO() {
		super();
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
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "personaLogeadaDTO [id=" + id + ", nombreUsuario=" + nombreUsuario + ", contrasena=" + contrasena
				+ ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", nombre1=" + nombre1 + ", nombre2="
				+ nombre2 + ", fechaNacimiento=" + fechaNacimiento + ", direccion=" + direccion + ", mail=" + mail
				+ ", activo=" + activo + ", admin=" + admin + ", token=" + token + "]";
	}
		
}
