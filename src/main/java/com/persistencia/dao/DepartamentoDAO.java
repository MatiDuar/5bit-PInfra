package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.persistencia.entities.Departamento;


@Stateless
@LocalBean

public class DepartamentoDAO {

	public DepartamentoDAO() {
		super();
	}

	@PersistenceContext
	private EntityManager em;

	public List<Departamento> listarCarreras() {
		try {
			String query = "select d from Departamento d";
			List<Departamento> resultList = (List<Departamento>) em.createQuery(query, Departamento.class).getResultList();
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void agregarDepartamento(Departamento d) {
		
		try {
			em.merge(d);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo crear el Departamento");
		}
	}
	
	public void modificarDepartamento(Departamento d) {
		try {
			em.merge(d);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo modificar el Departamento");
		}
	}
	
	public void borrarDepartamento(long id) {
		
		try {
			Departamento d=em.find(Departamento.class, id);
			
			em.remove(d);
			em.flush();
		}catch(Exception e) {
			new Exception("No se pudo borrar el Departamento");
		}
		
	}
}
