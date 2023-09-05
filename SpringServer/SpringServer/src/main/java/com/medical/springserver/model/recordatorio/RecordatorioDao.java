package com.medical.springserver.model.recordatorio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.medical.springserver.model.calendario.Calendario;

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
	
	public List<Recordatorio> findByCodCalendario(int codCalendario){
		return repository.findByCodCalendario(codCalendario);
	}

	public Recordatorio getByCodRecordatorio(int codRecordatorio) {
		return repository.findByCodRecordatorio(codRecordatorio);
	}

	   public Recordatorio modificarRecordatorio(int codRecordatorio, Recordatorio recordatorioModificado) {
	        // Paso 1: Recuperar el registro existente por su ID
	        Recordatorio registroExistente = repository.findByCodRecordatorio(codRecordatorio);
	        

	        // Paso 2: Actualizar los campos necesarios del registro existente con los datos del recordatorio modificado
	        BeanUtils.copyProperties(recordatorioModificado, registroExistente, "codRecordatorio");

	        // Paso 3: Guardar la instancia actualizada
	        return repository.save(registroExistente);
	    }

}
