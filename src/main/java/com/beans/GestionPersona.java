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
	private Alumno alumnoLogeado;

	private String carreraSeleccionada;
	private String itrSeleccionado;

	private String carreraSeleccionadaLog;
	private String itrSeleccionadoLog;

	private String contrasenaModificar;

	private String toRegistro;
	private List<Carrera> carreras;
	private List<ITR> itrs;

	private java.util.Date fechaNacSel;
	private java.util.Date fechaNacLog;

	private boolean isAlumno;
	
	private boolean isModContraseña;

	@PostConstruct
	public void init() {
		persistenciaBean.initPersona();
		carreras = persistenciaBean.listarCarreras();

		itrs = persistenciaBean.listarITRs();

		personaSeleccionada = new Persona();
		fechaNacSel = new java.util.Date();
		isAlumno = true;
		isModContraseña=false;
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
			if (persistenciaBean.buscarAlumno(persona.getId()) != null) {
				alumnoLogeado = persistenciaBean.buscarAlumno(persona.getId());
			}
			personaLogeada = persona;

			return "/index.xhtml";
		} catch (Exception e) {
			e.printStackTrace();
			String msg1 = "Usuario o contraseña Incorrecta";
			// mensaje de actualizacion correcta
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

			return "";
		}
	}

	public String agregarPersona() {
		if (isAlumno) {
			parsePersona(personaSeleccionada, alumnoSeleccionado);
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

	public String modificarPersona() {
		if (alumnoLogeado != null) {
			parsePersona(personaLogeada, alumnoLogeado);
			persistenciaBean.modificarUsuario(alumnoLogeado);
		} else {
			persistenciaBean.modificarUsuario(personaLogeada);
		}

		String msg1 = "Se modifico correctamente el usuario";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "";
	}

	public String modificarContrasena() {
		Persona p = persistenciaBean.buscarPersona(personaLogeada.getId());
		p.setContrasena(contrasenaModificar);

		persistenciaBean.modificarUsuario(p);

		String msg1 = "Se modifico correctamente la Contraseña";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		isModContraseña=false;
		return "";
	}
	
	public String cerrarModificarContrasena() {
		String msg1 = "Se cancelo la modificacion de la Contraseña";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "";
	}

	private void reset() {
		personaSeleccionada = new Persona();
		personaLogeada = new Persona();
		fechaNacSel = null;
		alumnoSeleccionado = new Alumno();
		carreraSeleccionada = "";
		itrSeleccionado = "";
	}

	public void parsePersona(Persona p, Alumno a) {
		a.setNombre1(p.getNombre1());
		a.setNombre2(p.getApellido2());
		a.setApellido1(p.getApellido1());
		a.setApellido2(p.getApellido2());
		a.setNombreUsuario(p.getNombreUsuario());
		a.setContrasena(p.getContrasena());
		a.setMail(p.getMail());
		a.setDireccion(p.getDireccion());
		a.setFechaNacimiento(p.getFechaNacimiento());
	}
	public String toggleModContrasena() {
		isModContraseña=!isModContraseña;
		return "";
	}
	
	public Alumno esAlumnoLogeado() {
		return persistenciaBean.buscarAlumno(personaLogeada.getId());
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

	public java.util.Date getFechaNacLog() {
		fechaNacLog = personaLogeada.getFechaNacimiento();
		return fechaNacLog;
	}

	public void setFechaNacLog(java.util.Date fechaNacLog) {
		personaLogeada.setFechaNacimiento(new java.sql.Date(fechaNacLog.getTime()));

		this.fechaNacLog = fechaNacLog;
	}

	public String getCarreraSeleccionadaLog() {
		carreraSeleccionadaLog = alumnoLogeado.getCarrera().getNombre();
		return carreraSeleccionadaLog;
	}

	public void setCarreraSeleccionadaLog(String carreraSeleccionadaLog) {
		alumnoLogeado.setCarrera(persistenciaBean.buscarCarrera(carreraSeleccionadaLog));
		this.carreraSeleccionadaLog = carreraSeleccionadaLog;
	}

	public String getItrSeleccionadoLog() {
		itrSeleccionadoLog = alumnoLogeado.getItr().getNombre();
		return itrSeleccionadoLog;
	}

	public void setItrSeleccionadoLog(String itrSeleccionadoLog) {

		alumnoLogeado.setItr(persistenciaBean.buscarITR(itrSeleccionadoLog));

		this.itrSeleccionadoLog = itrSeleccionadoLog;
	}

	public Alumno getAlumnoLogeado() {
		return alumnoLogeado;
	}

	public void setAlumnoLogeado(Alumno alumnoLogeada) {
		this.alumnoLogeado = alumnoLogeada;
	}

	public String getContrasenaModificar() {
		return contrasenaModificar;
	}

	public void setContrasenaModificar(String contrasenaModificar) {
		this.contrasenaModificar = contrasenaModificar;
	}

	public boolean isModContraseña() {
		return isModContraseña;
	}

	public void setModContraseña(boolean isModContraseña) {
		this.isModContraseña = isModContraseña;
	}

}
