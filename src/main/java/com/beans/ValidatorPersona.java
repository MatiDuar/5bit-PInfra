package com.beans;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import com.logicaNegocio.GestionPersonaService;

@Named(value = "validatorPersona") // JEE8
@SessionScoped // JEE8
public class ValidatorPersona implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	@Inject
	GestionPersonaService persistenciaBean;

	@Inject
	GestionPersona gestionPersona;

	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		
		String patternEmail = "^(?!\\s*$).+";
		if (persistenciaBean.existeNombreUsuario((String) arg2)
				&& !gestionPersona.getPersonaLogeada().getNombreUsuario().equals((String) arg2)) {
			throw new ValidatorException(new FacesMessage("Ya existe un usuario con ese nombre de usuario "));
		}

		if (!((String) arg2).matches(patternEmail)) {
			throw new ValidatorException(new FacesMessage("No puede contener solo espacios vacios "));
		}

	}

	public void validateOnLista(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {

		String patternEmail = "^(?!\\s*$).+";
	

		if (!((String) arg2).matches(patternEmail)) {
			throw new ValidatorException(new FacesMessage("No puede contener solo espacios vacios "));
		}

	}

}
