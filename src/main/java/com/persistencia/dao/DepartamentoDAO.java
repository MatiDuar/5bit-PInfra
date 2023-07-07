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

	public void agregarDepartamento(Departamento d) {

		try {
			em.merge(d);
			em.flush();
		} catch (Exception e) {
			new Exception("No se pudo crear el Departamento");
		}
	}

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

	public void modificarDepartamento(Departamento d) {
		try {
			em.merge(d);
			em.flush();
		} catch (Exception e) {
			new Exception("No se pudo modificar el Departamento");
		}
	}

	public void borrarDepartamento(long id) {

		try {
			Departamento d = em.find(Departamento.class, id);

			em.remove(d);
			em.flush();
		} catch (Exception e) {
			new Exception("No se pudo borrar el Departamento");
		}

	}

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
