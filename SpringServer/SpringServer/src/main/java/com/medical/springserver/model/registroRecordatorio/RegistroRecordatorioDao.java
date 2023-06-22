package com.medical.springserver.model.registroRecordatorio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class RegistroRecordatorioDao {
	@Autowired
	private RegistroRecordatorioRepository repository;
	
	public RegistroRecordatorio save(RegistroRecordatorio registroRecordatorio) {
		return repository.save(registroRecordatorio);
	}
	
	public List<RegistroRecordatorio> getAllRegistrosRecordatorio(){
		Streamable<RegistroRecordatorio> streamableRegistrosRecordatorio = Streamable.of(repository.findAll());
		List<RegistroRecordatorio> registrosRecordatorio = new ArrayList<>();
		streamableRegistrosRecordatorio.forEach(registrosRecordatorio::add);
		return registrosRecordatorio;
	}
	
	public void delete(RegistroRecordatorio registroRecordatorio) {
		repository.delete(registroRecordatorio);
	}
}
