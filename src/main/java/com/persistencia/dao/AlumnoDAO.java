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
	
	
	public void agregarAlumno(Alumno a) {
		
		try {
			em.merge(a);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo crear el Alumno");
		}
	}
	
	public void modificarAlumno(Alumno c) {
		try {
			em.merge(c);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo modificar el Alumno");
		}
	}
	
	public void borrarAlumno(long id) {
		
		try {
			Alumno a=em.find(Alumno.class, id);
			
			em.remove(a);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo borrar el Alumno");
		}
		
	}
	
	public Alumno buscar(long id) {
		try {
			Alumno a=em.find(Alumno.class, id);
			return a;
		} catch (Exception e) {
			return null;
		}
	}
}
