package com.medical.springserver.model.calendario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service

public class CalendarioDao {
	@Autowired
	private CalendarioRepository repository;
	
	public Calendario save(Calendario calendario) {
	    // Buscar la entidad existente por su código
	    Calendario existingCalendario = repository.findById(calendario.getCodCalendario()).orElse(null);

	    if (existingCalendario != null) {
	        if (calendario.getFechaAltaCalendario() != null) {
	            existingCalendario.setFechaAltaCalendario(calendario.getFechaAltaCalendario());
	        }
	        if (calendario.getFechaFinVigenciaC() != null) {
	            existingCalendario.setFechaFinVigenciaC(calendario.getFechaFinVigenciaC());
	        }
	        if (calendario.getNombreCalendario() != null) {
	            existingCalendario.setNombreCalendario(calendario.getNombreCalendario());
	        }
	        if (calendario.getNombrePaciente() != null) {
	            existingCalendario.setNombrePaciente(calendario.getNombrePaciente());
	        }
	        if (calendario.getRelacionCalendario() != null) {
	            existingCalendario.setRelacionCalendario(calendario.getRelacionCalendario());
	        }

	        // Guardar la entidad actualizada
	        return repository.save(existingCalendario);
	    } else {
	        // Si no se encuentra la entidad existente, crea una nueva
	        return repository.save(calendario);
	    }
	}

	
	public List<Calendario> getAllCalendarios(){
		Streamable<Calendario> streamableCalendarios = Streamable.of(repository.findAll());
		List<Calendario> calendarios = new ArrayList<>();
		streamableCalendarios.forEach(calendarios::add);
		return calendarios;
	}
	
	public void delete(Calendario calendario) {
		repository.delete(calendario);
	}
	
	public List<Calendario> findByCodUsuario(int codUsuario){
		return repository.findByCodUsuario(codUsuario);
	}
	
	public Calendario findByCodCalendario(int codCalendario) {
		return repository.findByCodCalendario(codCalendario);
	}
	
	 public Calendario modificarCalendario(int codCalendario, String nombre, String relacion, String paciente) {
			 	//int id, LocalDate fechaAlta, LocalDate fechaFin, String nombre, String relacion, String paciente) {
	        	Calendario calendario = repository.findByCodCalendario(codCalendario);
	            //calendario.setFechaAltaCalendario(fechaAlta);
	            //calendario.setFechaFinVigenciaC(fechaFin);
	            calendario.setNombreCalendario(nombre);
	            calendario.setRelacionCalendario(relacion);
	            calendario.setNombrePaciente(paciente);
	            return save(calendario);
	        
	    }
	 
	 public void eliminarCalendario(int codCalendario) {
		 Calendario calendario = repository.findByCodCalendario(codCalendario);
		 calendario.setFechaFinVigenciaC(LocalDate.now());
		 save(calendario);
		 
	 }
	 
	 public List<Calendario> findByInstitucion(String nombreInstitucion) {
		 return repository.findByInstitucion(nombreInstitucion);
	 }

}
