package com.medical.springserver.model.frecuencia;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class FrecuenciaDao {
	@Autowired
	private FrecuenciaRepository repository;
	
	public Frecuencia save(Frecuencia frecuencia){
		return repository.save(frecuencia);
	}
	
	public List<Frecuencia> getAllFrecuencias(){
		Streamable<Frecuencia> streamableFrecuencias = Streamable.of(repository.findAll());
		List<Frecuencia> frecuencias = new ArrayList<>();
		streamableFrecuencias.forEach(frecuencias::add);
		return frecuencias;
	}
	
	public void delete(Frecuencia frecuencia) {
		repository.delete(frecuencia);
	}
}
