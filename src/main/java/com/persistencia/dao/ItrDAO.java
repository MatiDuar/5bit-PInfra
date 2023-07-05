package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.persistencia.entities.ITR;





@Stateless
@LocalBean

public class ItrDAO {

	public ItrDAO() {
		super();
	}

	@PersistenceContext
	private EntityManager em;

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
	
	
	public void agregarITR(ITR i) {
		
		try {
			em.merge(i);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo crear el ITR");
		}
	}
	
	public void modificarITR(ITR i) {
		try {
			em.merge(i);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo modificar el ITR");
		}
	}
	
	public void borrarITR(long id) {
		
		try {
			ITR i=em.find(ITR.class, id);
			
			em.remove(i);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo borrar el ITR");
		}
		
	}
}
