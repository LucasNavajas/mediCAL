package com.medical.springserver.model.calendariosintoma;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.calendariomedicion.CalendarioMedicion;

@Service

public class CalendarioSintomaDao {
	@Autowired
	private CalendarioSintomaRepository repository;
	
	 public CalendarioSintoma eliminarCalendarioSintoma(int codCalendarioSintoma) {
	        // Paso 1: Obtener el CalendarioSintoma actual
	        CalendarioSintoma calendarioSintoma = repository.findByCodCalendarioSintoma(codCalendarioSintoma);

	        if (calendarioSintoma == null) {
	            // Manejar la situación en la que no se encuentra el CalendarioSintoma
	            throw new NoSuchElementException("El CalendarioSintoma no existe.");
	        }

	        // Paso 2: Modificar el atributo fechaFinVigenciaCS con la fecha actual
	        calendarioSintoma.setFechaFinVigenciaCS(LocalDate.now());

	        // Paso 3: Guardar los cambios en la base de datos
	        return repository.save(calendarioSintoma);
	    }
	
	public CalendarioSintoma save(CalendarioSintoma calendariosintoma) {
		return repository.save(calendariosintoma);
	}
	
	public CalendarioSintoma findByCodCalendarioSintoma(int codCalendarioSintoma) {
		return repository.findByCodCalendarioSintoma(codCalendarioSintoma);
	}
	
	public List<CalendarioSintoma> findByCodSintoma(int codSintoma){
		return repository.findByCodSintoma(codSintoma);
	}
	
	public List<CalendarioSintoma> findByCodCalendario(int codCalendario){
		return repository.findByCodCalendario(codCalendario);
	}
	
	public List<CalendarioSintoma> getAllCalendarioSintomas(){
		Streamable<CalendarioSintoma> streamableCalendarioSintomas = Streamable.of(repository.findAll());
		List<CalendarioSintoma> calendariosintomas = new ArrayList<>();
		streamableCalendarioSintomas.forEach(calendariosintomas::add);
		return calendariosintomas;
	}
	
	public void delete(CalendarioSintoma calendariosintoma) {
		repository.delete(calendariosintoma);
	}

}