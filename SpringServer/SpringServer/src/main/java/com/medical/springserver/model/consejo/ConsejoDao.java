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
	    // Consulta la entidad existente por su código
	    Consejo existingConsejo = repository.findById(consejo.getNroConsejo()).orElse(null);

	    if (existingConsejo!=null) {
	        // Copia los valores de las propiedades no nulas del objeto JSON a la entidad existente
	        BeanUtils.copyProperties(consejo, existingConsejo, getNullPropertyNames(consejo));

	        // Guarda la entidad actualizada en la base de datos
	        return repository.save(existingConsejo);
	    } else {
	        // Si no existe la entidad, simplemente la guardas como una nueva
	        return repository.save(consejo);
	    }
	}

	// Método para obtener los nombres de las propiedades nulas en un objeto
	private String[] getNullPropertyNames(Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    List<String> emptyNames = new ArrayList<>();
	    for (java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }
	    
	    return emptyNames.toArray(new String[0]);
	}
	public List<Consejo> getAllConsejo() {
	    return repository.getAllConsejos(LocalDate.now());
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
