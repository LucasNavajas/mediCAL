package com.medical.springserver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.calendariomedicion.CalendarioMedicion;
import com.medical.springserver.model.calendariomedicion.CalendarioMedicionDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsCalendarioMedicion {
	
	@Autowired
	private CalendarioMedicionDao calendariomedicionDao;
	
	//@Test
	void addCalendarioMedicionTest() {
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		LocalDateTime fechaHoyTime = LocalDateTime.now();
		
		CalendarioMedicion calendariomedicion = new CalendarioMedicion();
		calendariomedicion.setFechaCalendarioMedicion(fechaHoyTime);
		calendariomedicion.setFechaFinVigenciaCM(null);
		
		calendariomedicionDao.save(calendariomedicion);
		
	}
	
	//@Test
	void getAllCalendarioMedicionesAndDelete() {
		List<CalendarioMedicion> calendariomediciones = calendariomedicionDao.getAllCalendarioMediciones();
		for (CalendarioMedicion calendariomedicion : calendariomediciones) {
			calendariomedicionDao.delete(calendariomedicion);
		}
		
	}

}
