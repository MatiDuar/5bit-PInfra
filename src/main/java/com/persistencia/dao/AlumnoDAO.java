package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Alumno;
import com.persistencia.entities.Persona;





@Stateless
@LocalBean

public class AlumnoDAO {

	public AlumnoDAO() {
		super();
	}

	@PersistenceContext
	private EntityManager em;

	
	/**
	 * Lista a todos los alumnos de la base de datos
	 * @return
	 */
	public List<Alumno> listarAlumnos() {
		try {
			String query = "select a from Alumno a";
			List<Alumno> resultList = (List<Alumno>) em.createQuery(query, Alumno.class).getResultList();
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Agrega alumno a al base de datos
	 * @param a Alumno a crear
	 */
	public void agregarAlumno(Alumno a) {
		
		try {
			em.merge(a);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo crear el Alumno");
		}
	}
	
	/**
	 * Modificar un alumno
	 * @param c Alumno a modificar
	 */
	public void modificarAlumno(Alumno c) {
		try {
			em.merge(c);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo modificar el Alumno");
		}
	}
	
	/**
	 * Borra a un Alumno de la base de datos
	 * @param id Id del alumno a eliminar
	 */
	public void borrarAlumno(long id) {
		
		try {
			Alumno a=em.find(Alumno.class, id);
			
			em.remove(a);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo borrar el Alumno");
		}
		
	}
	
	
	/** 
	 * Busca en la base de datos a un alumno con el id Ingresado
	 * @param id Id de alumno a buscar
	 * @return Si encuentra a un alumno con el id ingresado retorna el mismo sino retorna null
	 */
	public Alumno buscar(long id) {
		try {
			Alumno a=em.find(Alumno.class, id);
			return a;
		} catch (Exception e) {
			return null;
		}
	}
}
