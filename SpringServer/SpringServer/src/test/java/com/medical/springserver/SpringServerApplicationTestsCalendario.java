package com.medical.springserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.calendario.CalendarioDao;
import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;


@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsCalendario {
	
	@Autowired
	private CalendarioDao calendarioDao;
	
	@Test
	void addCalendarioTest() {
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		//instancia de calendario sintoma
		CalendarioSintoma calendariosintoma = new CalendarioSintoma();
		calendariosintoma.setFechaCalendarioSintoma(fechaHoy);
		calendariosintoma.setFechaFinVigenciaCS(null);
		
		Calendario calendario = new Calendario();
		calendario.setFechaAltaCalendario(fechaHoy);
		calendario.setFechaFinVigenciaC(null);
		calendario.setNombreCalendario("calendario_01");
		// Ejemplo de Calendario de Institución:
		calendario.setNombrePaciente("Julieta Gimenez");
		calendario.setRelacionCalendario(null);
		
		calendariosintoma.setCalendario(calendario);
		List<CalendarioSintoma> varcalendariosintoma = new ArrayList<>();
		varcalendariosintoma.add(calendariosintoma);
		calendario.setVarcalendariosintoma(varcalendariosintoma);
			
		System.out.println(calendario.getVarcalendariosintoma());
		calendarioDao.save(calendario);
	}
	
	//@Test
	void getAllCalendariosAndDelete() {
		List<Calendario> calendarios = calendarioDao.getAllCalendarios();
		for (Calendario calendario : calendarios) {
			calendarioDao.delete(calendario);
		}
		
	}

}
