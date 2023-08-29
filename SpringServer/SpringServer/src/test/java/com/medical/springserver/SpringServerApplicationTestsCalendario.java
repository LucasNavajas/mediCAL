package com.medical.springserver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.calendario.CalendarioDao;
import com.medical.springserver.model.calendariomedicion.CalendarioMedicion;
import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;
import com.medical.springserver.model.recordatorio.Recordatorio;


@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsCalendario {
	
	@Autowired
	private CalendarioDao calendarioDao;
	
	@Test
	void addCalendarioTest() {
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		// Obtener la fecha de hoy
		LocalDateTime fechaHoyTime = LocalDateTime.now();
		
		Calendario calendario = new Calendario();
		calendario.setFechaAltaCalendario(fechaHoy);
		calendario.setFechaFinVigenciaC(null);
		calendario.setNombreCalendario("calendario_01");
		// Ejemplo de Calendario de Institución:
		calendario.setNombrePaciente("Julieta Gimenez");
		calendario.setRelacionCalendario(null);
		
		// instancia de Recordatorio
		Recordatorio recordatorio = new Recordatorio();
		recordatorio.setDuracionRecordatorio(15);
		recordatorio.setFechaAltaRecordatorio(LocalDate.now());
		recordatorio.setFechaFinRecordatorio(LocalDate.of(2023, 7, 21));
		recordatorio.setFechaInicioRecordatorio(LocalDate.now());
		recordatorio.setFechaFinVigenciaR(null);
		recordatorio.setHorarioRecordatorio(8);
		
		// instancia de CalendarioSintoma
		CalendarioSintoma calendariosintoma = new CalendarioSintoma();
		calendariosintoma.setFechaCalendarioSintoma(fechaHoyTime);
		calendariosintoma.setFechaFinVigenciaCS(null);
		
		// instancia de CalendarioMedicion
		CalendarioMedicion calendariomedicion = new CalendarioMedicion();
		calendariomedicion.setFechaCalendarioMedicion(fechaHoy);
		calendariomedicion.setFechaFinVigenciaCM(null);
		calendariomedicion.setValorCalendarioMedicion(0.6f); // Agrega la letra 'f' para indicar que es un float
		
		
		recordatorio.setCalendario(calendario);
		List<Recordatorio> varrecordatorio = new ArrayList<>();
		varrecordatorio.add(recordatorio);
		calendario.setRecordatorio(varrecordatorio);
		
		calendariosintoma.setCalendario(calendario);
		List<CalendarioSintoma> varcalendariosintoma = new ArrayList<>();
		varcalendariosintoma.add(calendariosintoma);
		calendario.setVarcalendariosintoma(varcalendariosintoma);
		
		calendariomedicion.setCalendario(calendario);
		List<CalendarioMedicion> varcalendariomedicion = new ArrayList<>();
		varcalendariomedicion.add(calendariomedicion);
		calendario.setVarcalendariomedicion(varcalendariomedicion);
		
		System.out.println(calendario.getRecordatorio());
		System.out.println(calendario.getVarcalendariosintoma());
		System.out.println(calendario.getVarcalendariomedicion());
		
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
