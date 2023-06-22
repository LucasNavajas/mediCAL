package com.medical.springserver.model.instruccion;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class InstruccionDao {
	@Autowired
	private InstruccionRepository repository;
	
	public Instruccion save(Instruccion instruccion) {
		return repository.save(instruccion);
	}
	
	public List<Instruccion> getAllInstrucciones(){
		Streamable<Instruccion> streamableInstrucciones = Streamable.of(repository.findAll());
		List<Instruccion> instrucciones = new ArrayList<>();
		streamableInstrucciones.forEach(instrucciones::add);
		return instrucciones;
	}
	
	public void delete(Instruccion instruccion) {
		repository.delete(instruccion);
	}

}
