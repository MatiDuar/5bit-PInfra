package com.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.logicaNegocio.GestionPersonaService;
import com.persistencia.entities.Alumno;
import com.persistencia.entities.Carrera;
import com.persistencia.entities.ITR;
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
	private Alumno alumnoSeleccionado;
	private String carreraSeleccionada;
	private String itrSeleccionado;

	private String toRegistro;
	private List<Carrera> carreras;
	private List<ITR> itrs;

	private java.util.Date fechaNacSel;

	private boolean isAlumno;

	@PostConstruct
	public void init() {
		persistenciaBean.initPersona();
		carreras = persistenciaBean.listarCarreras();

		itrs = persistenciaBean.listarITRs();

		personaSeleccionada = new Persona();
		fechaNacSel = new java.util.Date();
		isAlumno = true;
		toRegistro = "registro.xhtml";
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

			Persona persona = persistenciaBean.verificarUsuario(personaSeleccionada.getNombreUsuario(),
					personaSeleccionada.getContrasena());

			if (persona == null) {
				throw new Exception();
			}
			personaLogeada = persona;

			return "index.xhtml";
		} catch (Exception e) {
			String msg1 = "Usuario o contraseña Incorrecta";
			// mensaje de actualizacion correcta
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

			return "";
		}
	}

	public String agregarPersona() {
		if (isAlumno) {
			parsePersona(personaSeleccionada);
			persistenciaBean.agregarUsuario(alumnoSeleccionado);
		} else {
			persistenciaBean.agregarUsuario(personaSeleccionada);
		}
		reset();
		String msg1 = "Se creo correctamente el usuario";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "login.xhtml";
	}

	private void reset() {
		personaSeleccionada = new Persona();
		personaLogeada = new Persona();
		fechaNacSel = null;
		alumnoSeleccionado = new Alumno();
		carreraSeleccionada="";
		itrSeleccionado="";
	}

	public void parsePersona(Persona p) {
		alumnoSeleccionado.setNombre1(p.getNombre1());
		alumnoSeleccionado.setNombre2(p.getApellido2());
		alumnoSeleccionado.setApellido1(p.getApellido1());
		alumnoSeleccionado.setApellido2(p.getApellido2());
		alumnoSeleccionado.setNombreUsuario(p.getNombreUsuario());
		alumnoSeleccionado.setContrasena(p.getContrasena());
		alumnoSeleccionado.setMail(p.getMail());
		alumnoSeleccionado.setDireccion(p.getDireccion());
		alumnoSeleccionado.setFechaNacimiento(p.getFechaNacimiento());
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

	public Alumno getAlumnoSeleccionado() {
		return alumnoSeleccionado;
	}

	public void setAlumnoSeleccionado(Alumno alumnoSeleccionado) {
		this.alumnoSeleccionado = alumnoSeleccionado;
	}

	public String getCarreraSeleccionada() {
		return carreraSeleccionada;
	}

	public void setCarreraSeleccionada(String carreraSeleccionada) {
		alumnoSeleccionado.setCarrera(persistenciaBean.buscarCarrera(carreraSeleccionada));
		this.carreraSeleccionada = carreraSeleccionada;
	}

	public List<ITR> getItrs() {
		return itrs;
	}

	public void setItrs(List<ITR> itrs) {
		this.itrs = itrs;
	}

	public String getItrSeleccionado() {
		return itrSeleccionado;
	}

	public void setItrSeleccionado(String itrSeleccionado) {
		alumnoSeleccionado.setItr(persistenciaBean.buscarITR(itrSeleccionado));
		this.itrSeleccionado = itrSeleccionado;
	}
	
	

}
