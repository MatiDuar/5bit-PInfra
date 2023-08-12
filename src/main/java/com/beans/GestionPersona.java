package com.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.LinkedList;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import com.logicaNegocio.GestionPersonaService;
import com.persistencia.dto.PersonaAlumnoDTO;
import com.persistencia.dto.PersonaLogeadaDTO;
import com.persistencia.entities.Alumno;
import com.persistencia.entities.Carrera;
import com.persistencia.entities.ITR;
import com.persistencia.entities.Persona;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.Keys;

@Named(value = "gestionPersona") // JEE8
@SessionScoped // JEE8
public class GestionPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id = 1;
	@Inject
	GestionPersonaService persistenciaBean;
	@Inject
	LoginBeanJWT jwt;

	private List<Alumno> personasMod;
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
	
	private List<String> bottonesMenu;

	private java.util.Date fechaNacSel;
	private java.util.Date fechaNacLog;

	private boolean isAlumno;

	private boolean isModContraseña;

	// Para saber si entro sin logearse al sistema
	private boolean isKicked;

	// Token de jwt
	private String token;

	private Claims datosToken;

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
		bottonesMenu=new LinkedList<>();

	}

	/**
	 * este es el metodo que verifica si el usuario esta en la BD y si esta activo,
	 * si esta activo se redirije a index
	 * 
	 * @return devuelve un sting con el path hacia la pagina donde hay que dirigirse
	 */
	public String verificarPersona() {
		try {

			// Generar JSON Web Token
			token = jwt.generarToken(personaSeleccionada.getNombreUsuario(), personaSeleccionada.getContrasena());

			datosToken = jwt.obtenerClaim(token);

			// traemos los datos de la persona logeada a partir del Id Persona del token
			// generado
			Long idPersona = ((Double) datosToken.get("id")).longValue();
			if (persistenciaBean.buscarAlumno(idPersona) != null) {
				alumnoLogeado = persistenciaBean.buscarAlumno(idPersona);
			}
			personaLogeada = persistenciaBean.buscarPersona(idPersona);
			if (!(Boolean) datosToken.get("activo")) {
				// Mensaje si el usuario esta inactivo
				String msg1 = "Usuario dado de baja del sistema";
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, "");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				return "";
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");

			return "";

		} catch (Exception e) {
			e.printStackTrace();
			String msg1 = "Usuario o Contrseña errónea";
			// mensaje autenticación incorrecta
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

			return "";
		}
	}

	/**
	 * este metodo se encarga de crear una persona en la base de datos
	 * 
	 * @return devuelve la pagian en la que se tiene que redirigir
	 */
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

		String url = "login.xhtml";
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Esta funcion modifica al usuario logeado con los cambios realizados
	 * 
	 * @return
	 */
	public String modificarPersona() {
		if (alumnoLogeado != null) {
			parsePersona(personaLogeada, alumnoLogeado);
			persistenciaBean.modificarUsuario(alumnoLogeado);
		} else {
			System.out.println(personaLogeada);
			persistenciaBean.modificarUsuario(personaLogeada);
		}

		String msg1 = "Se modifico correctamente el usuario";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "";
	}

	/**
	 * La funcion se encarga de modificar una persona en la lista
	 * 
	 * @param p recibe la persona a modificar
	 * @return
	 */

	public String modificarPersonaOnLista(Persona p) {

		persistenciaBean.modificarUsuario(p);

		String msg1 = "Se modifico correctamente el usuario";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "";
	}

	/**
	 * La funcion se encarga de dar de baja a una persona en la base de datos
	 * 
	 * @param pDto Objeto que almacena la tabla
	 * @return
	 */

	public String bajaPersonaOnLista(PersonaAlumnoDTO pDto) {
		Persona p = parsePersonaFromDTO(pDto);
		p.setActivo(false);
		persistenciaBean.modificarUsuario(p);
		String msg1 = "Se elimino correctamente el usuario";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "";
	}

	/**
	 * La funcion se encarga de activar a una persona en la base de datos
	 * 
	 * @param pDto Objeto que almacena la tabla
	 * @return
	 */

	public String activarPersonaOnLista(PersonaAlumnoDTO pDto) {
		Persona p = parsePersonaFromDTO(pDto);
		p.setActivo(true);
		persistenciaBean.modificarUsuario(p);
		String msg1 = "Se activo correctamente el usuario";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "";
	}

	/**
	 * La funcion modifica la contraseña del usuario logeado
	 * 
	 * @return
	 */
	public String modificarContrasena() {
		
		
		System.out.println(personaLogeada);
		personaLogeada.setContrasena(contrasenaModificar);

		persistenciaBean.modificarUsuario(personaLogeada);

		String msg1 = "Se modifico correctamente la Contraseña";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		isModContraseña = false;
		return "";
	}

	/**
	 * Mensaje cuando se cierra el modificar contraseña
	 * 
	 * @return
	 */
	public String cerrarModificarContrasena() {
		String msg1 = "Se cancelo la modificacion de la Contraseña";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "";
	}

	/**
	 * Reinicia todos la mayoria de datos en el bean
	 */
	private void reset() {
		personaSeleccionada = new Persona();
		personaLogeada = new Persona();
		fechaNacSel = null;
		alumnoSeleccionado = new Alumno();
		carreraSeleccionada = "";
		itrSeleccionado = "";
		token = "";
		datosToken = null;
	}

	/**
	 * Copia todos los datos de la Persona al Alumno
	 * 
	 * @param p Persona con los datos originales
	 * @param a Alumno a modificar los datos
	 */
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

	/**
	 * Funcion que al terminar de editar la persona en la tabla realiza los cambios
	 * realizados
	 * 
	 * @param persona Persona con los datos a modificar
	 */
	public void onRowEdit(RowEditEvent<PersonaAlumnoDTO> persona) {

		if (persona.getObject().getCarrera() == null) {
			Persona modPersona = parsePersonaFromDTO(persona.getObject());
			modificarPersonaOnLista(modPersona);
		} else {
			Alumno modAlumno = parseAlumnoFromDTO(persona.getObject());
			modificarPersonaOnLista(modAlumno);
		}

	}

	/**
	 * Funcion que permite parsear un objeto PersonaAlumnoDTO a un objeto Persona
	 * 
	 * @param pdto Objeto que tiene los datos
	 * @return Persona con todos los datos de pdto
	 */
	public Persona parsePersonaFromDTO(PersonaAlumnoDTO pdto) {
		Persona p = persistenciaBean.buscarPersona(pdto.getId());
		p.setActivo(pdto.getActivo());
		p.setNombre1(pdto.getNombre1());
		p.setApellido1(pdto.getApellido1());
		p.setMail(pdto.getMail());
		p.setNombreUsuario(pdto.getNombreUsuario());
		p.setDireccion(pdto.getDireccion());

		return p;

	}

	/**
	 * Funcion que permite parsear un objeto PersonaAlumnoDTO a un objeto Alumno
	 * 
	 * @param pdto Objeto que tiene los datos
	 * @return Alumno con todos los datos de pdto
	 */
	public Alumno parseAlumnoFromDTO(PersonaAlumnoDTO pdto) {
		Alumno a = persistenciaBean.buscarAlumno(pdto.getId());
		a.setActivo(pdto.getActivo());
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

	/**
	 * Funcion que da de baja al usuario Logeado
	 * 
	 * @return
	 */
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

	/**
	 * Este metodo valida si el usuario esta logeado
	 */
	public void checkUserIsLogin() {
		if (personaLogeada.getId() == null || personaLogeada == null) {
			try {

				isKicked = true;
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	public void msjKick() {
		if (isKicked) {
			String msg1 = "Debes estar ingresado para esa funcionalidad";
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			isKicked = false;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<String> getBottonesMenu() {


		// Obtener la ruta de la vista actual
		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();

		if(viewId.equals("/index.xhtml") || viewId.equals("/listarPersonas.xhtml") || viewId.equals("/editarPerfil.xhtml")) {
			bottonesMenu=new LinkedList<>();
		}else{
			bottonesMenu=new LinkedList<>();
			bottonesMenu.add("Boton 1");
			bottonesMenu.add("Boton 2");
			bottonesMenu.add("Boton 3");
			bottonesMenu.add("Boton 4");			
		}
		return bottonesMenu;
	}

	public void setBottonesMenu(List<String> bottonesMenu) {
		this.bottonesMenu = bottonesMenu;
	}

}
