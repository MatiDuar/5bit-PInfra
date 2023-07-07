package com.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;
import org.primefaces.util.LangUtils;

import com.logicaNegocio.GestionPersonaService;
import com.persistencia.entities.Alumno;
import com.persistencia.entities.Persona;

@Named("dtFilterView")
@ViewScoped
public class FilterView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private GestionPersonaService service;

	private List<Persona> personas;

	private List<Persona> filteredPersonas;

	private List<FilterMeta> filterBy;

	private boolean globalFilterOnly;

	@PostConstruct
	public void init() {
		globalFilterOnly = true;
		try {
			personas = service.listarPersonas();
		} catch (Exception e) {
			e.printStackTrace();
		}

		filterBy = new ArrayList<>();

//        filterBy.add(FilterMeta.builder()
//                .field("status")
//                .filterValue(CustomerStatus.NEW)
//                .matchMode(MatchMode.EQUALS)
//                .build());
//
		filterBy.add(FilterMeta.builder().field("date")
				.filterValue(
						new ArrayList<>(Arrays.asList(LocalDate.now().minusDays(28), LocalDate.now().plusDays(28))))
				.matchMode(MatchMode.BETWEEN).build());

	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (LangUtils.isBlank(filterText)) {
			return true;
		}

		Persona persona = (Persona) value;
		Alumno personaAlumno=buscar(persona.getId());
		return persona.getNombre1().toLowerCase().contains(filterText)
				|| persona.getApellido1().toLowerCase().contains(filterText)
				|| persona.getNombreUsuario().toLowerCase().contains(filterText)
				|| persona.getMail().toString().toLowerCase().contains(filterText)
				|| persona.getDireccion().toLowerCase().contains(filterText)
				|| (personaAlumno!=null && personaAlumno.getCarrera().getNombre().toLowerCase().contains(filterText) )
				|| (personaAlumno!=null && personaAlumno.getIdEstudiantil().toString().toLowerCase().contains(filterText));

	}

	public void toggleGlobalFilter() {
		setGlobalFilterOnly(!isGlobalFilterOnly());
	}

	private int getInteger(String string) {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	public Alumno buscar(long id) {
		return service.buscarAlumno(id);
	}

	public List<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	public List<Persona> getFilteredPersonas() {
		return filteredPersonas;
	}

	public void setFilteredPersonas(List<Persona> filteredPersonas) {
		this.filteredPersonas = filteredPersonas;
	}

	public void setFilterBy(List<FilterMeta> filterBy) {
		this.filterBy = filterBy;
	}

	public List<FilterMeta> getFilterBy() {
		return filterBy;
	}

	public boolean isGlobalFilterOnly() {
		return globalFilterOnly;
	}

	public void setGlobalFilterOnly(boolean globalFilterOnly) {
		this.globalFilterOnly = globalFilterOnly;
	}
}