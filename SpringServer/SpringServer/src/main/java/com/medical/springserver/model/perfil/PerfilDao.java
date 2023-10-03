package com.medical.springserver.model.perfil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class PerfilDao{
	@Autowired
	private PerfilRepository repository;
	
	public Perfil save(Perfil perfil) {
	    // Buscar el perfil existente por su código
	    Perfil existingPerfil = repository.findByCodPerfil(perfil.getCodPerfil());

	    if (existingPerfil != null) {
	        if (perfil.getFechaAltaPerfil() != null) {
	            existingPerfil.setFechaAltaPerfil(perfil.getFechaAltaPerfil());
	        }
	        if (perfil.getFechaFinVigenciaP() != null) {
	            existingPerfil.setFechaFinVigenciaP(perfil.getFechaFinVigenciaP());
	        }
	        if (perfil.getMotivoFinVigenciaP() != null) {
	            existingPerfil.setMotivoFinVigenciaP(perfil.getMotivoFinVigenciaP());
	        }
	        if (perfil.getNombrePerfil() != null) {
	            existingPerfil.setNombrePerfil(perfil.getNombrePerfil());
	        }

	        // Guardar el perfil actualizado
	        return repository.save(existingPerfil);
	    } else {
	        // Si no se encuentra el perfil existente, crea uno nuevo
	        return repository.save(perfil);
	    }
	}

	
	public List<Perfil> getAllPerfiles() {
	    Streamable<Perfil> streamablePerfiles = Streamable.of(repository.findAll());
	    List<Perfil> perfiles = new ArrayList<>();
	    streamablePerfiles.forEach(perfiles::add);
	    return perfiles;
	}
	
	public void delete(Perfil perfil) {
		repository.delete(perfil);
	}
	
	public Perfil findByCodPerfil(int codPefil) {
		return repository.findByCodPerfil(codPefil);
	}
	
	public Perfil bajaPerfil(int codPerfil) {
	    Perfil perfil = repository.findByCodPerfil(codPerfil);
	    
	    if (perfil != null) {
	        perfil.setFechaFinVigenciaP(LocalDate.now());
	        repository.save(perfil);
	    }
	    
	    return perfil;
	}

	public Perfil recuperarPerfil(int codPerfil) {
	    Perfil perfil = repository.findByCodPerfil(codPerfil);
	    
	    if (perfil != null) {
	        perfil.setFechaFinVigenciaP(null);
	        perfil.setMotivoFinVigenciaP(null);
	        repository.save(perfil);
	    }
	    
	    return perfil;
	}

	
}

