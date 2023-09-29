package com.medical.springserver.model.presentacionMed;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class PresentacionMedDao {

	@Autowired
	private PresentacionMedRepository repository;
	
	public PresentacionMed save(PresentacionMed presentacionMed) {
	    // Buscar la entidad existente por su código
	    Optional<PresentacionMed> existingPresentacionMedOptional = repository.findById(presentacionMed.getCodPresentacionMed());

	    if (existingPresentacionMedOptional.isPresent()) {
	        PresentacionMed existingPresentacionMed = existingPresentacionMedOptional.get();
	        
	        // Verificar y actualizar cada campo si no es nulo
	        if (presentacionMed.getDescPresentacionMed() != null) {
	            existingPresentacionMed.setDescPresentacionMed(presentacionMed.getDescPresentacionMed());
	        }
	        if (presentacionMed.getFechaAltaPresentacionMed() != null) {
	            existingPresentacionMed.setFechaAltaPresentacionMed(presentacionMed.getFechaAltaPresentacionMed());
	        }
	        existingPresentacionMed.setFechaFinVigenciaPM(presentacionMed.getFechaFinVigenciaPM());
	        if (presentacionMed.getNombrePresentacionMed() != null) {
	            existingPresentacionMed.setNombrePresentacionMed(presentacionMed.getNombrePresentacionMed());
	        }
	        if (presentacionMed.getAdministracionMed() != null) {
	            existingPresentacionMed.setVaradministracionmed(presentacionMed.getAdministracionMed());
	        }

	        // Guardar la entidad actualizada
	        return repository.save(existingPresentacionMed);
	    } else {
	        // Si no se encuentra la entidad existente, crea una nueva
	        return repository.save(presentacionMed);
	    }
	}

	
	public List<PresentacionMed> getAllPresentacionesMed(){
		return repository.getAllPresentacionesMedicas(LocalDate.now());
	}
	
	public void delete(PresentacionMed presentacionMed) {
		repository.delete(presentacionMed);
	}
	
	public PresentacionMed findByCodPresentacionMed(int codPresentacionMed) {
        return repository.findByCodPresentacionMed(codPresentacionMed);
    }

    public List<PresentacionMed> findByCodAdministracionMed(int codAdministracionMed) {
        return repository.findByAdministracionMed_CodAdministracionMed(codAdministracionMed);
    }
    
    public List<PresentacionMed> getAllPresentacionesMedicasYBajas() {
        return repository.getAllPresentacionesMedicasYBajas();
    }

    public PresentacionMed bajaPresentacionMedica(int codPresentacionMed) {
        PresentacionMed presentacionMed = repository.findById(codPresentacionMed).orElse(null);
        if (presentacionMed != null) {
            presentacionMed.setFechaFinVigenciaPM(LocalDate.now());
            repository.save(presentacionMed);
        }
        return presentacionMed;
    }

    public PresentacionMed recuperarPresentacionMedica(int codPresentacionMed) {
        PresentacionMed presentacionMed = repository.findById(codPresentacionMed).orElse(null);
        if (presentacionMed != null) {
            presentacionMed.setFechaFinVigenciaPM(null);
            repository.save(presentacionMed);
        }
        return presentacionMed;
    }

}
