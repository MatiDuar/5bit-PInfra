package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Departamento;
import com.persistencia.entities.Persona;

@Stateless
@LocalBean

public class DepartamentoDAO {

	public DepartamentoDAO() {
		super();
	}

	@PersistenceContext
	private EntityManager em;

	/**
	 * Listado de Departamentos en la base de datos
	 * 
	 * @return Lista de Departamento
	 */
	public List<Departamento> listarCarreras() {
		try {
			String query = "select d from Departamento d";
			List<Departamento> resultList = (List<Departamento>) em.createQuery(query, Departamento.class)
					.getResultList();
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Se agrega un Departamento en la base de datos
	 * 
	 * @param d Departamento a crear en la base
	 */
	public void agregarDepartamento(Departamento d) {

		try {
			em.merge(d);
			em.flush();
		} catch (Exception e) {
			new Exception("No se pudo crear el Departamento");
		}
	}

	/**
	 * Se agrega una lista de Departamentos en la base de datos
	 * 
	 * @param d lista de Departamentos a crear en la base
	 */
	public void agregarDepartamento(List<Departamento> d) {

		try {
			for (Departamento dep : d) {
				em.merge(dep);
			}
			em.flush();
		} catch (Exception e) {
			new Exception("No se pudo crear el Departamento");
		}
	}

	/**
	 * modifica a una Departamento en la base de datos
	 * 
	 * @param d Departamento a modificar
	 */
	public void modificarDepartamento(Departamento d) {
		try {
			em.merge(d);
			em.flush();
		} catch (Exception e) {
			new Exception("No se pudo modificar el Departamento");
		}
	}

	/**
	 * Borra a un Departamento en la base de datos
	 * 
	 * @param id Id del Departamento a borrar
	 */
	public void borrarDepartamento(long id) {

		try {
			Departamento d = em.find(Departamento.class, id);

			em.remove(d);
			em.flush();
		} catch (Exception e) {
			new Exception("No se pudo borrar el Departamento");
		}

	}

	/**
	 * Busca un Departamento en la base de datos a partir del nombre de la misma
	 * 
	 * @param nombre Nombre de Departamento a buscar
	 * @return Si encuentra devuelve un objeto Departamento sino devuelve Null
	 */
	public Departamento buscarDepartamento(String nombre) {

		try {
			TypedQuery<Departamento> query = em
					.createQuery("select d from Departamento d where d.nombre=:nombre", Departamento.class)
					.setParameter("nombre", nombre);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}
}
