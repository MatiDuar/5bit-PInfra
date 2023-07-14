package com.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.LinkedList;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import com.logicaNegocio.GestionPersonaService;
import com.persistencia.dto.PersonaAlumnoDTO;
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

	private List<Alumno> personasMod;
	private Persona personaLogeada;
	private Persona personaSeleccionada;

	private Alumno alumnoSeleccionado;
	private Alumno alumnoLogeado;
	private Alumno alumnoMod;

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

	//Para saber si entro sin logearse al sistema
	private boolean isKicked;
	@PostConstruct
	public void init() {
		persistenciaBean.initPersona();
		carreras = persistenciaBean.listarCarreras();

		itrs = persistenciaBean.listarITRs();

		personaSeleccionada = new Persona();
		fechaNacSel = new java.util.Date();
		isAlumno = true;
		isModContraseña = false;
		toRegistro = "registro.xhtml?facesRedirect=true";
		personasMod = new LinkedList<>();

	}

	public String verificarPersona() {
		try {

			Persona persona = persistenciaBean.verificarUsuario(personaSeleccionada.getNombreUsuario(),
					personaSeleccionada.getContrasena());

			if (persona == null) {
				throw new Exception();
			}
			if(persona.getActivo()) {
				if (persistenciaBean.buscarAlumno(persona.getId()) != null) {
					alumnoLogeado = persistenciaBean.buscarAlumno(persona.getId());
				}
				personaLogeada = persona;

				return "/index.xhtml?facesRedirect=true";
			}
			
			String msg1 = "Usuario dado de baja del sistema";
			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "";
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
		
		String url="login.xhtml";
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
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

	public String modificarPersonaOnLista(Persona p) {

		persistenciaBean.modificarUsuario(p);

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
		isModContraseña = false;
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
		a.setId(p.getId());
		a.setActivo(p.getActivo());
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

	public void onRowEdit(RowEditEvent<PersonaAlumnoDTO> persona) {

		if (persona.getObject().getCarrera() == null) {
			Persona modPersona = parsePersonaFromDTO(persona.getObject());
			modificarPersonaOnLista(modPersona);
		} else {
			Alumno modAlumno = parseAlumnoFromDTO(persona.getObject());
			modificarPersonaOnLista(modAlumno);
		}

	}

	public Persona parsePersonaFromDTO(PersonaAlumnoDTO pdto) {
		Persona p = persistenciaBean.buscarPersona(pdto.getId());
		p.setNombre1(pdto.getNombre1());
		p.setApellido1(pdto.getApellido1());
		p.setMail(pdto.getMail());
		p.setNombreUsuario(pdto.getNombreUsuario());
		p.setDireccion(pdto.getDireccion());

		return p;

	}

	public Alumno parseAlumnoFromDTO(PersonaAlumnoDTO pdto) {
		Alumno a = persistenciaBean.buscarAlumno(pdto.getId());

		a.setNombre1(pdto.getNombre1());
		a.setApellido1(pdto.getApellido1());
		a.setMail(pdto.getMail());
		a.setNombreUsuario(pdto.getNombreUsuario());
		a.setDireccion(pdto.getDireccion());
		a.setIdEstudiantil(pdto.getIdEstudiantil());
		a.setCarrera(persistenciaBean.buscarCarrera(pdto.getCarrera()));

		return a;

	}

	public void onRowCancel(RowEditEvent<PersonaAlumnoDTO> persona) {

		FacesMessage msg = new FacesMessage("Edit Cancelled", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String toggleModContrasena() {
		isModContraseña = !isModContraseña;
		return "";
	}

	public Alumno esAlumnoLogeado() {
		return persistenciaBean.buscarAlumno(personaLogeada.getId());
	}

	public java.util.Date getFechaNacSel() {
		return fechaNacSel;
	}
	
	public String darDeBaja() {
		personaLogeada.setActivo(false);
		persistenciaBean.modificarUsuario(personaLogeada);
		reset();
		String msg1 = "Se dio de baja al Usuario";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "login.xhtml?facesRedirect=true";
	}
	
	public String cerrarSesion() {
		reset();
		return "login.xhtml?facesRedirect=true";
		
	}
	
	public void checkUserIsLogin() {
		if(personaLogeada.getId()==null || personaLogeada==null) {
			try {
				
				isKicked=true;
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void msjKick() {
		if(isKicked) {
			String msg1 = "Debes estar ingresado para esa funcionalidad";
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			isKicked=false;
		}
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

	public Alumno getAlumnoMod() {
		return alumnoMod;
	}

	public void setAlumnoMod(Alumno alumnoMod) {
		this.alumnoMod = alumnoMod;
	}

	public List<Alumno> getPersonasMod() {
		return personasMod;
	}

	public void setPersonasMod(List<Alumno> personasMod) {
		this.personasMod = personasMod;
	}

	public boolean isKicked() {
		return isKicked;
	}

	public void setKicked(boolean isKicked) {
		this.isKicked = isKicked;
	}

}
