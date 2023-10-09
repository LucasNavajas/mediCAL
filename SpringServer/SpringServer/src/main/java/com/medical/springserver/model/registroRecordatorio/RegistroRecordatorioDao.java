package com.medical.springserver.model.registroRecordatorio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.medical.springserver.model.calendariomedicion.CalendarioMedicion;

@Service
public class RegistroRecordatorioDao {
	@Autowired
	private RegistroRecordatorioRepository repository;
	
	public List<RegistroRecordatorio> findByCodRecordatorio(int codRecordatorio){
		return repository.findByCodRecordatorio(codRecordatorio);
	}
	
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
	
	public List<RegistroRecordatorio> obtenerRegistrosCalendario(int codCalendario){
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startDate = now.minusDays(30);
		LocalDateTime endDate = now.plusDays(30);

		return repository.obtenerRegistrosCalendarioEnRango(codCalendario, startDate, endDate);
	}
	public List<RegistroRecordatorio> obtenerRegistrosActuales(){
		LocalDateTime now = LocalDateTime.now().plusMinutes(1);
		LocalDateTime hace15 = now.minusMinutes(16);
		return repository.obtenerRegistrosCalendarioActuales(hace15, now);
	}
	public List<RegistroRecordatorio> obtenerRegistrosCalendarioNotificacion(int codCalendario){
		return repository.obtenerRegistrosCalendarioNotificacion(codCalendario, LocalDateTime.now());
	}

	public void bajaRegistro(int codRegistroRecordatorio) {
		RegistroRecordatorio registro = repository.findById(codRegistroRecordatorio)
		        .orElseThrow(() -> new NoSuchElementException("Registro no encontrado"));
		
		registro.setFechaFinVigenciaRR(LocalDate.now());
		repository.save(registro);
	}
	
	public List<RegistroRecordatorio> getByCodRecordatorio(int codRecordatorio){
		return repository.obtenerRegistrosPorCodRecordatorio(codRecordatorio);
	}
	
	public List<RegistroRecordatorio> getByCodRecordatorioHistorial(int codRecordatorio){
		return repository.obtenerRegistrosPorCodRecordatorioHistorial(codRecordatorio);
	}

	public RegistroRecordatorio getByCodRegistroRecordatorio(int codRegistroRecordatorio) {
		return repository.findById(codRegistroRecordatorio)
		        .orElseThrow(() -> new NoSuchElementException("Registro no encontrado"));
	}
	
	public void deleteRegistrosRecordatorio(int codRecordatorio) {
		List<RegistroRecordatorio> registrosPosteriores = repository.obtenerRegistrosPorCodRecordatorioHistorial(codRecordatorio);
		for (RegistroRecordatorio registro : registrosPosteriores) {
			delete(registro);
		}
	}
}
