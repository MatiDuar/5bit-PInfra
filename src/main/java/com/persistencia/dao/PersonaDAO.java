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
		} catch (Exception e) {
			new Exception("No se pudo crear la Persona");
		}
	}

	public void modificarPersona(Persona p) {
		try {
			em.merge(p);
			em.flush();
		} catch (Exception e) {
			new Exception("No se pudo modificar la Persona");
		}
	}

	public void borrarPersona(long id) {

		try {
			Persona p = em.find(Persona.class, id);

			em.remove(p);
			em.flush();
		} catch (Exception e) {
			new Exception("No se pudo borrar la Persona");
		}

	}

//	public Usuario verificarUsuario(String nombreUsuario, String contra) {
//
//		try {
//			TypedQuery<Usuario> query = em.createQuery(
//					"select u from Usuario u where u.nombreUsuario=:nombreUsuario and u.contrasena=:contra",
//					Usuario.class).setParameter("nombreUsuario", nombreUsuario).setParameter("contra", contra);
//			return query.getSingleResult();
//		} catch (Exception e) {
//			return null;
//		}
	public Persona verificar(String nombreUsuario, String contrasena) {
		try {
			TypedQuery<Persona> query = em.createQuery(
					"select p from Persona p where p.nombreUsuario=:nombreUsuario and p.contrasena=:contra",
					Persona.class).setParameter("nombreUsuario", nombreUsuario).setParameter("contra", contrasena);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}

