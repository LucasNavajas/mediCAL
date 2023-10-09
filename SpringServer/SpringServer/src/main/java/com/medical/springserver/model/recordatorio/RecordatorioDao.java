package com.medical.springserver.model.recordatorio;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorio;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorioDao;

@Service

public class RecordatorioDao {
	@Autowired
	private RecordatorioRepository repository;
	@Autowired
	private RegistroRecordatorioDao registroDao;
	
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

	   public void crearRegistros(int codRecordatorio) {
		    Recordatorio recordatorio = repository.findByCodRecordatorio(codRecordatorio);
		    int frecuencia;
		    if(recordatorio.getFrecuencia()!=null) {
		    	frecuencia = recordatorio.getFrecuencia().getCantidadFrecuencia(); // Obtener la frecuencia en horas
		    }
		    else {
		    	frecuencia=0;
		    }
		    int nroRegistro = 1; // Se inicializa el nro_registro en 1 y se va aumentando
		    LocalDateTime fechaEsperadaToma = recordatorio.getFechaInicioRecordatorio();
		    List<RegistroRecordatorio> registrosHistorial = registroDao.getByCodRecordatorioHistorial(codRecordatorio);

		    LocalDateTime primeraFechaHistorial = registrosHistorial.isEmpty() ? null : registrosHistorial.get(0).getFechaTomaEsperada();
		    if (primeraFechaHistorial != null) {
		    	while(fechaEsperadaToma.isBefore(primeraFechaHistorial.minusSeconds(60)) && frecuencia!=0) {
		    	System.out.println(fechaEsperadaToma);
		        fechaEsperadaToma = fechaEsperadaToma.plusHours(frecuencia);
		    	}
		        registroDao.deleteRegistrosRecordatorio(codRecordatorio);
		    }
		    if(frecuencia==0) {
		    	fechaEsperadaToma = fechaEsperadaToma.withHour(0).withMinute(0);
		    	while (fechaEsperadaToma.compareTo(recordatorio.getFechaFinRecordatorio()) < 0) {
			        RegistroRecordatorio registro = new RegistroRecordatorio();
			        registro.setFechaTomaEsperada(fechaEsperadaToma);
			        fechaEsperadaToma = fechaEsperadaToma.plusHours(24); // Actualizar fechaEsperadaToma
			        registro.setNroRegistro(nroRegistro++); // Se setea el nro y se aumenta
			        registro.setRecordatorio(recordatorio);
			        registro.setTomaRegistroRecordatorio(false);
			        registroDao.save(registro);
		    		}
		    }
		    else {
		    	
		 
		    //Para los ciclos
		    if(recordatorio.getFrecuencia().getDiasDescansoF()!=0) {
		    	int diasTranscurridos = 0;

		        while (fechaEsperadaToma.compareTo(recordatorio.getFechaFinRecordatorio()) < 0) {
		            // Verificar si es un día de toma o de descanso
		            if (diasTranscurridos % (recordatorio.getFrecuencia().getDiasTomaF() + recordatorio.getFrecuencia().getDiasDescansoF()) < recordatorio.getFrecuencia().getDiasTomaF()) {
		                RegistroRecordatorio registro = new RegistroRecordatorio();
		                registro.setFechaTomaEsperada(fechaEsperadaToma);
		                fechaEsperadaToma = fechaEsperadaToma.plusHours(frecuencia); // Actualizar fechaEsperadaToma
		                registro.setNroRegistro(nroRegistro++); // Se setea el nro y se aumenta
		                registro.setRecordatorio(recordatorio);
		                registro.setTomaRegistroRecordatorio(false);
		                registroDao.save(registro);
		            }

		            // Avanzar al siguiente día
		            fechaEsperadaToma = fechaEsperadaToma.plusDays(1);
		            diasTranscurridos++;
		        }
		    }
		    else { //Para los que no son ciclo
			    while (fechaEsperadaToma.compareTo(recordatorio.getFechaFinRecordatorio()) < 0) {
			        RegistroRecordatorio registro = new RegistroRecordatorio();
			        registro.setFechaTomaEsperada(fechaEsperadaToma);
			        fechaEsperadaToma = fechaEsperadaToma.plusHours(frecuencia); // Actualizar fechaEsperadaToma
			        registro.setNroRegistro(nroRegistro++); // Se setea el nro y se aumenta
			        registro.setRecordatorio(recordatorio);
			        registro.setTomaRegistroRecordatorio(false);
			        registroDao.save(registro);
		    		}
		    	}
		    }
		    
		}

	   
	public void bajaRecordatorio(int codRecordatorio) {
		Recordatorio recordatorio = repository.findByCodRecordatorio(codRecordatorio);
		recordatorio.setFechaFinVigenciaR(LocalDate.now());
		repository.save(recordatorio);
		List<RegistroRecordatorio> registros = registroDao.getByCodRecordatorio(codRecordatorio);
		for (RegistroRecordatorio registro : registros) {
			registro.setFechaFinVigenciaRR(LocalDate.now());
			registroDao.save(registro);
		}
		
	}

	public void bajaRecordatorioConHistorial(int codRecordatorio) {
		Recordatorio recordatorio = repository.findByCodRecordatorio(codRecordatorio);
		recordatorio.setFechaFinVigenciaR(LocalDate.now());
		repository.save(recordatorio);
		List<RegistroRecordatorio> registros = registroDao.getByCodRecordatorioHistorial(codRecordatorio);
		for (RegistroRecordatorio registro : registros) {
			registro.setFechaFinVigenciaRR(LocalDate.now());
			registroDao.save(registro);
		}
	}


}
