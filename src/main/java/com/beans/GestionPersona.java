package com.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.logicaNegocio.GestionPersonaService;
import com.persistencia.entities.Carrera;
import com.persistencia.entities.Persona;



@Named(value = "gestionPersona") // JEE8
@SessionScoped // JEE8
public class GestionPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id = 1;
	@Inject
	GestionPersonaService persistenciaBean;
	
	private Persona personaLogeada;
	private Persona personaSeleccionada;
	
	private String toRegistro;
	private List<Carrera>carreras;
	
	private java.util.Date fechaNacSel;
	
	private boolean isAlumno;

	@PostConstruct
	public void init() {
		persistenciaBean.initPersona();
		carreras=persistenciaBean.listarCarreras();
		personaSeleccionada=new Persona();
		fechaNacSel=new java.util.Date();
		isAlumno=true;
		toRegistro="registro.xhtml";
//		persistenciaBean.agregarUsuario();
//		try {
//			usuarios=persistenciaBean.listarUsuarios();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		usuarioSeleccionado = new Analista();
		
	}
	
	public String verificarPersona() {
		try {
			
			Persona persona=persistenciaBean.verificarUsuario(personaSeleccionada.getNombreUsuario(), personaSeleccionada.getContrasena());



			if(persona==null) {
				throw new Exception();
			}
			personaLogeada=persona;
			
			return "index.xhtml";
		}catch(Exception e) {
			String msg1="Usuario o contraseña Incorrecta";
			//mensaje de actualizacion correcta
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
			return "";
		}
	}
	
	public String agregarPersona() {
		persistenciaBean.agregarUsuario(personaSeleccionada);
		
		String msg1="Se creo correctamente el usuario";
		//mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "login.xhtml";
	}
	
	
	
	private void reset() {
		personaSeleccionada=new Persona();
		fechaNacSel=null;
	}
	public java.util.Date getFechaNacSel() {
		return fechaNacSel;
	}

	public void setFechaNacSel(java.util.Date fechaNacSel) {
		personaSeleccionada.setFechaNacimiento(new java.sql.Date(fechaNacSel.getTime()));
		this.fechaNacSel = fechaNacSel;
	}

	
	public String getToRegistro() {
		reset();
		return toRegistro;
	}

	public void setToRegistro(String toRegistro) {
		this.toRegistro = toRegistro;
	}

	public Persona getPersonaLogeada() {
		return personaLogeada;
	}

	public void setPersonaLogeada(Persona personaLogeada) {
		this.personaLogeada = personaLogeada;
	}

	public Persona getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(Persona personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public List<Carrera> getCarreras() {
		return carreras;
	}

	public void setCarreras(List<Carrera> carreras) {
		this.carreras = carreras;
	}

	public boolean isAlumno() {
		return isAlumno;
	}

	public void setAlumno(boolean isAlumno) {
		this.isAlumno = isAlumno;
	}

	

	
	
	
	
	
	
	
}
