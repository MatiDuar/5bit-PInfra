package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import com.persistencia.entities.Persona;





@Stateless
@LocalBean

public class PersonaDAO {

	public PersonaDAO() {
		super();
	}

	@PersistenceContext
	private EntityManager em;

	public List<Persona> listarPersonas() {
		try {
			String query = "select p from Persona p";
			List<Persona> resultList = (List<Persona>) em.createQuery(query, Persona.class).getResultList();
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void agregarPersona(Persona p) {
		
		try {
			em.merge(p);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo crear la Persona");
		}
	}
	
	public void modificarUsuario(Persona p) {
		try {
			em.merge(p);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo modificar la Persona");
		}
	}
	
	public void borrarUsuario(long id) {
		
		try {
			Persona p=em.find(Persona.class, id);
			
			em.remove(p);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo borrar la Persona");
		}
		
	}
}
