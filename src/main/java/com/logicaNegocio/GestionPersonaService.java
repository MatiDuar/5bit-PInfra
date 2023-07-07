package com.logicaNegocio;

import java.io.Serializable;
import java.sql.Date;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;



import com.persistencia.dao.PersonaDAO;
import com.persistencia.entities.Persona;





@Stateless
@LocalBean

public class GestionPersonaService implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	PersonaDAO personaDAO;
	
	
	
	public List<Persona> listarPersonas() throws Exception {
		
		List<Persona>listaPersonas=personaDAO.listarPersonas();
		
		return listaPersonas;
	}
	
	public Persona verificarUsuario(String nombreUsuario,String contra) {
		return personaDAO.verificar(nombreUsuario,contra);
	}

	
	public void agregarUsuario(Persona p) {
		personaDAO.agregarPersona(p);
	}
	
	public void modificarUsuario(Persona p) {
		personaDAO.modificarPersona(p);
	}

	
	public void borrarUsuario(long id) {
		personaDAO.borrarPersona(id);
	}
	
	
	public void initPersona() {
		Persona p=new Persona();
		
		p.setActivo(true);
		p.setApellido1("demo");
		p.setNombre1("demo");
		p.setNombreUsuario("demo");
		p.setContrasena("demo");		
		p.setDireccion("demo");
		p.setFechaNacimiento(new Date(2002,02,04));
		p.setMail("demo@demo");
		
		personaDAO.agregarPersona(p);
	}
	
	
	
}
