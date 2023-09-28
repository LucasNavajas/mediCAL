package com.medical.springserver.model.faq;

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
public class FAQDao {
	@Autowired
	private FAQRepository repository;
	
	public FAQ save(FAQ faq) {
	    // Buscar la entidad existente por su código
	    FAQ existingFAQ = repository.findById(faq.getCodFAQ()).orElse(null);

	    if (existingFAQ != null) {
	        if (faq.getFechaActualizacionFAQ() != null) {
	            existingFAQ.setFechaActualizacionFAQ(faq.getFechaActualizacionFAQ());
	        }
	        if (faq.getPreguntatFAQ() != null) {
	            existingFAQ.setPreguntatFAQ(faq.getPreguntatFAQ());
	        }
	        if (faq.getRespuestaFAQ() != null) {
	            existingFAQ.setRespuestaFAQ(faq.getRespuestaFAQ());
	        }
	        existingFAQ.setFechaFinVigenciaFAQ(faq.getFechaFinVigenciaFAQ());

	        // Guardar la entidad actualizada
	        return repository.save(existingFAQ);
	    } else {
	        // Si no se encuentra la entidad existente, crea una nueva
	        return repository.save(faq);
	    }
	}
	
	public List<FAQ> getAllFAQ() {
	    return repository.getAllFAQ(LocalDate.now());
	}
	
	public void delete(FAQ FAQ) {
		repository.delete(FAQ);
	}

	public List<FAQ> getAllFAQS() {
		return repository.getAllFAQS();
	}

	public FAQ bajaFAQ(int codFAQ) {
		FAQ faq = repository.findByCodFAQ(codFAQ);
		faq.setFechaFinVigenciaFAQ(LocalDate.now());
		repository.save(faq);
		return faq;
	}
	
	public FAQ recuperarFAQ(int codFAQ) {
		FAQ faq = repository.findByCodFAQ(codFAQ);
		faq.setFechaFinVigenciaFAQ(null);
		repository.save(faq);
		return faq;
	}
	
}
