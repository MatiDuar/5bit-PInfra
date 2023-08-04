package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Carrera;
import com.persistencia.entities.Departamento;





@Stateless
@LocalBean

public class CarreraDAO {

	public CarreraDAO() {
		super();
	}

	@PersistenceContext
	private EntityManager em;

	
	/**
	 * Lista de carrera en la base de datos
	 * @return Lista de carreras
	 */
	public List<Carrera> listarCarreras() {
		try {
			String query = "select c from Carrera c";
			List<Carrera> resultList = (List<Carrera>) em.createQuery(query, Carrera.class).getResultList();
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Se agrega una carrera en la base de datos
	 * @param c Carrera a crear en la base
	 */
	public void agregarCarrera(Carrera c) {
		
		try {
			em.merge(c);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo crear la Carrera");
		}
	}
	/**
	 * modifica a una carrera en la base de datos
	 * @param c Carrera a modificar
	 */
	public void modificarCarrera(Carrera c) {
		try {
			em.merge(c);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo modificar la Carrera");
		}
	}
	/**
	 * Borra a una carrera en la base de datos
	 * @param id Id de la carrera a borrar
	 */
	public void borrarCarrera(long id) {
		
		try {
			Carrera c=em.find(Carrera.class, id);
			
			em.remove(c);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo borrar la Carrera");
		}
		
	}
	
	/**
	 * Busca una carrera en la base de datos a partir del nombre de la misma
	 * @param nombre Nombre de carrera a buscar
	 * @return Si encuentra devuelve un objeto Carrera sino devuelve Null
	 */
	public Carrera buscarCarrera(String nombre) {

		try {
			TypedQuery<Carrera> query = em
					.createQuery("select c from Carrera c where c.nombre=:nombre", Carrera.class)
					.setParameter("nombre", nombre);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}
	
	
	/**
	 * Busca una carrera en la base de datos a partir de un Id
	 * @param id Id de carrera a buscar
	 * @return Si encuentra devuelve un objeto Carrera sino devuelve Null
	 */
	public Carrera buscarCarrera(Long id) {

		try {
			
			return em.find(Carrera.class, id);
		} catch (Exception e) {
			return null;
		}

	}
}
