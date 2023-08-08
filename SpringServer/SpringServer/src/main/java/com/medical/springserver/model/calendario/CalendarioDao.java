package com.medical.springserver.model.calendario;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service

public class CalendarioDao {
	@Autowired
	private CalendarioRepository repository;
	
	public Calendario save(Calendario calendario) {
		return repository.save(calendario);
	}
	
	public List<Calendario> getAllCalendarios(){
		Streamable<Calendario> streamableCalendarios = Streamable.of(repository.findAll());
		List<Calendario> calendarios = new ArrayList<>();
		streamableCalendarios.forEach(calendarios::add);
		return calendarios;
	}
	
	public void delete(Calendario calendario) {
		repository.delete(calendario);
	}
	
	public List<Calendario> findByCodUsuario(int codUsuario){
		return repository.findByCodUsuario(codUsuario);
	}

}
