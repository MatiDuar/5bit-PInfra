package com.logicaNegocio;

import java.io.Serializable;
import java.sql.Date;

import java.util.List;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.persistencia.dao.AlumnoDAO;
import com.persistencia.dao.CarreraDAO;
import com.persistencia.dao.DepartamentoDAO;
import com.persistencia.dao.ItrDAO;
import com.persistencia.dao.PersonaDAO;
import com.persistencia.dto.PersonaAlumnoDTO;
import com.persistencia.entities.*;

@Stateless
@LocalBean

public class GestionPersonaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	PersonaDAO personaDAO;

	@EJB
	CarreraDAO carreraDAO;

	@EJB
	DepartamentoDAO depDAO;

	@EJB
	ItrDAO itrDAO;
	@EJB
	AlumnoDAO alumnoDAO;

	/**
	 * Lista de todas las personas en la base de datos
	 * 
	 * @return Lista de Personas
	 * @throws Exception
	 */
	public List<Persona> listarPersonas() throws Exception {

		List<Persona> listaPersonas = personaDAO.listarPersonas();

		return listaPersonas;
	}

	/**
	 * Lista de todas las personas y alumnos en la base de datos
	 * 
	 * @return Lista de PersonasAlumnoDTO
	 * @throws Exception
	 */
	public List<PersonaAlumnoDTO> listarPersonasDTO() throws Exception {

		List<PersonaAlumnoDTO> listaPersonas = personaDAO.listarPersonasDTO();

		return listaPersonas;
	}

	/**
	 * Lista de todos los ITRs
	 * 
	 * @return lista de ITR
	 */
	public List<ITR> listarITRs() {

		List<ITR> listaPItrs = itrDAO.listarITRs();

		return listaPItrs;
	}

	/**
	 * Verifica si el nombreUsuario y contraseña coinciden en la base de datos
	 * 
	 * @param nombreUsuario
	 * @param contra
	 * @return El objeto de la persona encontrada en la base de datos
	 */
	public Persona verificarUsuario(String nombreUsuario, String contra) {
		return personaDAO.verificar(nombreUsuario, contra);
	}

	/**
	 * se agrega Persona en la base de datos
	 * 
	 * @param p
	 */
	public void agregarUsuario(Persona p) {
		p.setActivo(true);
		p.setAdmin(false);
		personaDAO.agregarPersona(p);
	}

	/**
	 * Modifica los de datos en la base de datos
	 * 
	 * @param p Persona a modificar los datos
	 */
	public void modificarUsuario(Persona p) {
		personaDAO.modificarPersona(p);
	}

	/**
	 * Borra a un Usuario en la base de datos
	 * 
	 * @param id Id de el usuario a borrar
	 */
	public void borrarUsuario(long id) {
		personaDAO.borrarPersona(id);
	}

	/**
	 * Lista de Carreras en la base de datos
	 * 
	 * @return Lista de Carrera
	 */
	public List<Carrera> listarCarreras() {
		return carreraDAO.listarCarreras();
	}

	/**
	 * retorna true si existe un usuario con el nombre ingresado
	 * @param nombre
	 * @return
	 */
	public Boolean existeNombreUsuario(String nombre) {

		return personaDAO.existeNombreUsuario(nombre);
	}

	public void initPersona() {

		initDepartamentos();
		initItrs();
		initCarreras();
		Persona p = new Persona();

		p.setActivo(true);
		p.setAdmin(true);
		p.setApellido1("demo");
		p.setNombre1("demo");
		p.setNombreUsuario("demo");
		p.setContrasena("demo");
		p.setDireccion("demo");
		p.setFechaNacimiento(new Date(2002 - 1900, 02, 04));
		p.setMail("demo@demo.com");

		personaDAO.agregarPersona(p);

		Alumno a = new Alumno();
		a.setAdmin(false);
		a.setActivo(true);
		a.setApellido1("Marshall");
		a.setNombre1("Jean");
		a.setNombreUsuario("jean.marshall");
		a.setContrasena("demo");
		a.setDireccion("demo");
		a.setFechaNacimiento(new Date(2002 - 1900, 02, 04));
		a.setMail("jean.marshall@estudiantes.utec.edu.uy");
		a.setIdEstudiantil((long) 2222);

		a.setItr(itrDAO.buscarITR("Centro-sur"));
		a.setCarrera(carreraDAO.buscarCarrera("LTI"));
		a.setIdEstudiantil((long) 2222);
		personaDAO.agregarPersona(a);
	}

	private void initDepartamentos() {
		ArrayList<Departamento> deps = new ArrayList<>();
		deps.add(new Departamento("Artigas", true));
		deps.add(new Departamento("Canelones", true));
		deps.add(new Departamento("Cerro Largo", true));
		deps.add(new Departamento("Colonia", true));
		deps.add(new Departamento("Durazno", true));
		deps.add(new Departamento("Flores", true));
		deps.add(new Departamento("Florida", true));
		deps.add(new Departamento("Lavalleja", true));
		deps.add(new Departamento("Maldonado", true));
		deps.add(new Departamento("Montevideo", true));
		deps.add(new Departamento("Paysandú", true));
		deps.add(new Departamento("Rio Negro", true));
		deps.add(new Departamento("Rivera", true));
		deps.add(new Departamento("Rocha", true));
		deps.add(new Departamento("Salto", true));
		deps.add(new Departamento("San josé", true));
		deps.add(new Departamento("Soriano", true));
		deps.add(new Departamento("Tacuarembó", true));
		deps.add(new Departamento("Treinta y Tres", true));

		depDAO.agregarDepartamento(deps);
	}

	private void initItrs() {
		itrDAO.agregarITR(new ITR("Centro-sur", depDAO.buscarDepartamento("Durazno"), true));
		itrDAO.agregarITR(new ITR("Norte", depDAO.buscarDepartamento("Rivera"), true));
		itrDAO.agregarITR(new ITR("Suroeste", depDAO.buscarDepartamento("Paysandú"), true));

	}

	private void initCarreras() {
		carreraDAO.agregarCarrera(new Carrera("LTI", true));
		carreraDAO.agregarCarrera(new Carrera("Mecatronica", true));
	}

	public Alumno buscarAlumno(long id) {
		return alumnoDAO.buscar(id);
	}

	public Carrera buscarCarrera(String nombre) {
		return carreraDAO.buscarCarrera(nombre);
	}

	public Carrera buscarCarrera(Long id) {
		return carreraDAO.buscarCarrera(id);
	}

	public ITR buscarITR(String nombre) {
		return itrDAO.buscarITR(nombre);
	}

	public Persona buscarPersona(long id) {
		return personaDAO.buscarPersona(id);
	}
}
