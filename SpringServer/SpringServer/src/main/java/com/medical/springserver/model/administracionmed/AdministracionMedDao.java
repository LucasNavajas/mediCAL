package com.medical.springserver.model.administracionmed;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service

public class AdministracionMedDao {
	@Autowired
	private AdministracionMedRepository repository;
	
	public AdministracionMed save(AdministracionMed administracionmed) {
		 Optional<AdministracionMed> existingAdministracionMed = repository.findByCodAdministracionMed(administracionmed.getCodAdministracionMed());

	        if (existingAdministracionMed.isPresent()) {
	            AdministracionMed foundAdministracionMed = existingAdministracionMed.get();
	            
	            if (administracionmed.getFechaAltaAdministracionMed() != null) {
	                foundAdministracionMed.setFechaAltaAdministracionMed(administracionmed.getFechaAltaAdministracionMed());
	            }
	            foundAdministracionMed.setFechaFinVigenciaAM(administracionmed.getFechaFinVigenciaAM());
	            if (administracionmed.getNombreAdministracionMed() != null) {
	                foundAdministracionMed.setNombreAdministracionMed(administracionmed.getNombreAdministracionMed());
	            }
	            
	            if(administracionmed.getDescAdministracionMed() != null) {
	            	foundAdministracionMed.setDescAdministracionMed(administracionmed.getDescAdministracionMed());
	            }

	            // Guardar la entidad actualizada
	            return repository.save(foundAdministracionMed);
	        } else {
	            // Si no se encuentra la entidad existente, crea una nueva
	            return repository.save(administracionmed);
	        }
	}
	
	public List<AdministracionMed> getAllAdministracionMeds(){
		return repository.getAllAdministracionMed(LocalDate.now());
	}
	
	public void delete(AdministracionMed administracionmed) {
		repository.delete(administracionmed);
	}
	
	public Optional<AdministracionMed> findByCodAdministracionMed(int codAdministracionMed) {
		Optional<AdministracionMed> optionalAdministracionMed = repository.findByCodAdministracionMed(codAdministracionMed);
	    return Optional.ofNullable(optionalAdministracionMed.orElse(null)); // o maneja el caso de null según tus necesidades
	}
	public List<AdministracionMed> getAllAdministracionMedYBajas() {
        return repository.getAllAdministracionMedYBajas();
    }

	public AdministracionMed bajaAdministracionMed(int codAdministracionMed) {
        Optional<AdministracionMed> optionalAdministracionMed = repository.findByCodAdministracionMed(codAdministracionMed);
        
        if (optionalAdministracionMed.isPresent()) {
            AdministracionMed administracionMed = optionalAdministracionMed.get();
            administracionMed.setFechaFinVigenciaAM(LocalDate.now());
            repository.save(administracionMed);
            return administracionMed;
        } else {
            // Puedes manejar aquí el caso en el que no se encuentra la entidad.
            // Por ejemplo, lanzar una excepción o devolver un valor por defecto.
            throw new EntityNotFoundException("No se encontró la entidad AdministracionMed con el código " + codAdministracionMed);
        }
    }

    public AdministracionMed recuperarAdministracionMed(int codAdministracionMed) {
        Optional<AdministracionMed> optionalAdministracionMed = repository.findByCodAdministracionMed(codAdministracionMed);
        
        if (optionalAdministracionMed.isPresent()) {
            AdministracionMed administracionMed = optionalAdministracionMed.get();
            administracionMed.setFechaFinVigenciaAM(null);
            repository.save(administracionMed);
            return administracionMed;
        } else {
            // Manejar el caso en el que no se encuentra la entidad.
            throw new EntityNotFoundException("No se encontró la entidad AdministracionMed con el código " + codAdministracionMed);
        }
    }
}
