package com.persistencia.dao;

import java.util.List;
import java.util.logging.SimpleFormatter;
import java.sql.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.persistencia.dto.PersonaAlumnoDTO;
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

	public List<PersonaAlumnoDTO> listarPersonasDTO() {
		try {
			String query = "select p.id,p.nombreUsuario,p.apellido1,p.nombre1,p.fechaNacimiento,p.direccion,p.mail,p.activo,a.idestudiantil,a.carrera_id from Personas p FULL OUTER JOIN Alumnos a on a.id=p.id";

			List<Object[]> resultList = em.createNativeQuery(query).getResultList();
			String pattern = "dd-MM-yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			List<PersonaAlumnoDTO> personasDTO = new ArrayList<>();

			String strDato;

			for (Object[] tupla : resultList) {

				int contador = 0;
				PersonaAlumnoDTO aux = new PersonaAlumnoDTO();
				for (Object dato : tupla) {
					if (dato != null) {
						if (dato instanceof Date) {
							strDato = sdf.format(dato);
						} else {
							strDato = dato.toString();
						}
					} else {
						strDato = "";
					}

					switch (contador) {
					case 0:
						aux.setId(Long.parseLong(strDato));
						break;
					case 1:
						aux.setNombreUsuario(strDato);
						break;
					case 2:
						aux.setApellido1(strDato);
						break;
					case 3:
						aux.setNombre1(strDato);
						break;
					case 4:
						aux.setFechaNacimiento(strDato);
						break;
					case 5:
						aux.setDireccion(strDato);
						break;
					case 6:
						aux.setMail(strDato);
						break;
					case 7:
						aux.setActivo((Boolean) dato);
						break;
					case 8:
						aux.setIdEstudiantil(Long.parseLong(strDato));
						break;
					case 9:
						aux.setCarrera(Long.parseLong(strDato));
						break;
					}
					contador++;
				}
				personasDTO.add(aux);
			}

			return personasDTO;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Persona buscarPersona(long id) {
		return em.find(Persona.class, id);
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
			TypedQuery<Persona> query = em
					.createQuery(
							"select p from Persona p where p.nombreUsuario=:nombreUsuario and p.contrasena=:contra",
							Persona.class)
					.setParameter("nombreUsuario", nombreUsuario).setParameter("contra", contrasena);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
