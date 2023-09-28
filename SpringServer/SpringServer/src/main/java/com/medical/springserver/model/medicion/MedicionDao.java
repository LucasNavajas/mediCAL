package com.medical.springserver.model.medicion;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.medical.springserver.model.calendario.Calendario;

@Service
public class MedicionDao {
	@Autowired
	private MedicionRepository repository;
	
	public Medicion save(Medicion medicion) {
	    // Buscar la entidad existente por su código
	    Medicion existingMedicion = repository.findById(medicion.getCodMedicion()).orElse(null);

	    if (existingMedicion != null) {
	        if (medicion.getFechaAltaMedicion() != null) {
	            existingMedicion.setFechaAltaMedicion(medicion.getFechaAltaMedicion());
	        }
	        existingMedicion.setFechaFinVigenciaM(medicion.getFechaFinVigenciaM());
	        if (medicion.getNombreMedicion() != null) {
	            existingMedicion.setNombreMedicion(medicion.getNombreMedicion());
	        }
	        if (medicion.getUnidadMedidaMedicion() != null) {
	            existingMedicion.setUnidadMedidaMedicion(medicion.getUnidadMedidaMedicion());
	        }

	        // Guardar la entidad actualizada
	        return repository.save(existingMedicion);
	    } else {
	        // Si no se encuentra la entidad existente, crea una nueva
	        return repository.save(medicion);
	    }
	}


	
	public Medicion findByCodMedicion(int codMedicion) {
		return repository.findByCodMedicion(codMedicion);
	}
	
	public List<Medicion> getAllMediciones(){
		return repository.getAllMediciones(LocalDate.now());
	}
	
	public void delete(Medicion medicion) {
		repository.delete(medicion);
	}
	
	public List<Medicion> getAllMedicionesYBajas(){
		return repository.getAllMedicionesYBajas();
	}
	
	public Medicion bajaMedicion(int codMedicion) {
	    Medicion medicion = repository.findByCodMedicion(codMedicion);
	    medicion.setFechaFinVigenciaM(LocalDate.now());
	    repository.save(medicion);
	    return medicion;
	}

	public Medicion recuperarMedicion(int codMedicion) {
	    Medicion medicion = repository.findByCodMedicion(codMedicion);
	    medicion.setFechaFinVigenciaM(null);
	    repository.save(medicion);
	    return medicion;
	}


}
