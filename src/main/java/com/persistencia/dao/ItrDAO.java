package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Departamento;
import com.persistencia.entities.ITR;





@Stateless
@LocalBean

public class ItrDAO {

	public ItrDAO() {
		super();
	}

	@PersistenceContext
	private EntityManager em;
	/**
	 * Listado de ITR en la base de datos
	 * 
	 * @return Lista de ITR
	 */
	public List<ITR> listarITRs() {
		try {
			String query = "select i from ITR i";
			List<ITR> resultList = (List<ITR>) em.createQuery(query, ITR.class).getResultList();
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Se agrega un ITR en la base de datos
	 * 
	 * @param i ITR a crear en la base
	 */
	public void agregarITR(ITR i) {
		
		try {
			em.merge(i);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo crear el ITR");
		}
	}
	
	/**
	 * modifica a un ITR en la base de datos
	 * 
	 * @param i ITR a modificar
	 */
	public void modificarITR(ITR i) {
		try {
			em.merge(i);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo modificar el ITR");
		}
	}
	
	/**
	 * Borra a un ITR en la base de datos
	 * 
	 * @param id Id del ITR a borrar
	 */
	public void borrarITR(long id) {
		
		try {
			ITR i=em.find(ITR.class, id);
			
			em.remove(i);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo borrar el ITR");
		}
		
	}
	/**
	 * Busca un ITR en la base de datos a partir del nombre de la misma
	 * 
	 * @param nombre Nombre de ITR a buscar
	 * @return Si encuentra devuelve un objeto ITR sino devuelve Null
	 */
	public ITR buscarITR(String nombre) {

		try {
			TypedQuery<ITR> query = em
					.createQuery("select i from ITR i where i.nombre=:nombre", ITR.class)
					.setParameter("nombre", nombre);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}
}
