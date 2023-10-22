package com.medical.springserver.model.consejo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class ConsejoDao {
	@Autowired
	private ConsejoRepository repository;
	
	public Consejo save(Consejo consejo) {
	    // Buscar la entidad existente por su código (nroConsejo)
	    Consejo existingConsejo = repository.findById(consejo.getNroConsejo()).orElse(null);

	    if (existingConsejo != null) {
	        if (consejo.getNombreConsejo() != null) {
	            existingConsejo.setNombreConsejo(consejo.getNombreConsejo());
	        }
	        if (consejo.getLinkConsejo() != null) {
	            existingConsejo.setLinkConsejo(consejo.getLinkConsejo());
	        }
	        if (consejo.getAuspiciante() != null) {
	            existingConsejo.setAuspiciante(consejo.getAuspiciante());
	        }
	        if (consejo.getDescConsejo() != null) {
	            existingConsejo.setDescConsejo(consejo.getDescConsejo());
	        }
	        if (consejo.getFechaAltaConsejo() != null) {
	            existingConsejo.setFechaAltaConsejo(consejo.getFechaAltaConsejo());
	        }
	        if (consejo.getFotoConsejo() != null) {
	            existingConsejo.setFotoConsejo(consejo.getFotoConsejo());
	        }
	        existingConsejo.setFechaFinVigenciaConsejo(consejo.getFechaFinVigenciaConsejo());
	        if (consejo.getTipoConsejo() != null) {
	            existingConsejo.setTipoConsejo(existingConsejo.getTipoConsejo());
	        }
	        if (consejo.getCantLikes() != -1) {
	        	existingConsejo.setCantLikes(consejo.getCantLikes());
	        }
	        if (consejo.getListaLikeados() != null) {
	        	existingConsejo.setListaLikeados(consejo.getListaLikeados());
	        }
	        // Guardar la entidad actualizada
	        return repository.save(existingConsejo);
	    } else {
	        // Si no se encuentra la entidad existente, crea una nueva
	        return repository.save(consejo);
	    }
	}

	public List<Consejo> getAllConsejo() {
	    return repository.getAllConsejos(LocalDate.now());
	}
	
	public Consejo findByNroConsejo(int nroConsejo) {
		return repository.findByNroConsejo(nroConsejo);
	}
	
	public void delete(Consejo consejo) {
		repository.delete(consejo);
	}

	public List<Consejo> getAllConsejos() {
		return repository.getAllConsejosYBajas();
	}

	public Consejo bajaConsejo(int codConsejo) {
		Consejo consejo = repository.findByNroConsejo(codConsejo);
		consejo.setFechaFinVigenciaConsejo(LocalDate.now());
		repository.save(consejo);
		return consejo;
	}
	
	public Consejo recuperarConsejo(int codConsejo) {
		Consejo consejo = repository.findByNroConsejo(codConsejo);
		consejo.setFechaFinVigenciaConsejo(null);
		repository.save(consejo);
		return consejo;
	}
	
}
