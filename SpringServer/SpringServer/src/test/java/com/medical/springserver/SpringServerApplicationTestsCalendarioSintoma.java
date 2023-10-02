package com.medical.springserver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;
import com.medical.springserver.model.calendariosintoma.CalendarioSintomaDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsCalendarioSintoma {
	
	@Autowired
	private CalendarioSintomaDao calendariosintomaDao;
	
	//@Test
	void addCalendarioSintomaTest() {
		
		// Obtener la fecha de hoy
		LocalDateTime fechaHoy = LocalDateTime.now();
		
		CalendarioSintoma calendariosintoma = new CalendarioSintoma();
		calendariosintoma.setFechaCalendarioSintoma(fechaHoy);
		calendariosintoma.setFechaFinVigenciaCS(null);
		
		calendariosintomaDao.save(calendariosintoma);
				
	}
	
	//@Test
	void getAllCalendarioSintomasAndDelete() {
		List<CalendarioSintoma> calendariosintomas = calendariosintomaDao.getAllCalendarioSintomas();
		for (CalendarioSintoma calendariosintoma : calendariosintomas) {
			calendariosintomaDao.delete(calendariosintoma);
		}
		
	}

}
