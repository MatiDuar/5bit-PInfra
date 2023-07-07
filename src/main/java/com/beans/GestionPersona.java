package com.beans;

import java.io.Serializable;


import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.logicaNegocio.GestionPersonaService;
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

	@PostConstruct
	public void init() {
		persistenciaBean.initPersona();
		
		personaSeleccionada=new Persona();
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

	

	
	
	
	
	
	
	
}
