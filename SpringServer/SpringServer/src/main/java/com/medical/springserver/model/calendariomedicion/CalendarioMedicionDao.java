package com.medical.springserver.model.calendariomedicion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;

@Service

public class CalendarioMedicionDao {
	@Autowired
	private CalendarioMedicionRepository repository;
	
	public CalendarioMedicion save(CalendarioMedicion calendariomedicion) {
		return repository.save(calendariomedicion);
	}
	
	
	public List<CalendarioMedicion> findByCodCalendario(int codCalendario){
		return repository.findByCodCalendario(codCalendario);
	}
	
	
	public List<CalendarioMedicion> getAllCalendarioMediciones(){
		Streamable<CalendarioMedicion> streamableCalendarioMediciones = Streamable.of(repository.findAll());
		List<CalendarioMedicion> calendariomediciones = new ArrayList<>();
		streamableCalendarioMediciones.forEach(calendariomediciones::add);
		return calendariomediciones;
	}
	
	public void delete(CalendarioMedicion calendariomedicion) {
		repository.delete(calendariomedicion);
	}

}
