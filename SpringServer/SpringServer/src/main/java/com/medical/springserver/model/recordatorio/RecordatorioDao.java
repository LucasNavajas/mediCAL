package com.medical.springserver.model.recordatorio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service

public class RecordatorioDao {
	@Autowired
	private RecordatorioRepository repository;
	
	public Recordatorio save(Recordatorio recordatorio) {
		return repository.save(recordatorio);
	}
	
	public List<Recordatorio> getAllRecordatorios(){
		Streamable<Recordatorio> streamableRecordatorios = Streamable.of(repository.findAll());
		List<Recordatorio> recordatorios = new ArrayList<>();
		streamableRecordatorios.forEach(recordatorios::add);
		return recordatorios;
	}
	
	public void delete(Recordatorio recordatorio) {
		repository.delete(recordatorio);
	}

}
