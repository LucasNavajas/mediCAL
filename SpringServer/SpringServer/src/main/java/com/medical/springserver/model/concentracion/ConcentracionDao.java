package com.medical.springserver.model.usuario;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class ConcentracionDao {
	@Autowired
	private ConcentracionRepository repository;
	
	public Concentracion save(Concentracion concentracion) {
		return repository.save(concentracion);
	}
	
	public List<Concentracion> getAllConcentracion() {
	    Streamable<Concentracion> streamableConcentracion = Streamable.of(repository.findAll());
	    List<Concentracion> concentracion = new ArrayList<>();
	    streamableConcentracion.forEach(concentracion::add);
	    return concentracion;
	}
	
	public void delete(Concentracion concentracion) {
		repository.delete(concentracion);
	}
	
}
