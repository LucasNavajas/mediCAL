package com.medical.springserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.calendariomedicion.CalendarioMedicion;
import com.medical.springserver.model.medicion.Medicion;
import com.medical.springserver.model.medicion.MedicionDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsMedicion {
	
	@Autowired
	private MedicionDao medicionDao;
	
	@Test
	void addMedicionTest() {
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		CalendarioMedicion calendariomedicion = new CalendarioMedicion();
		calendariomedicion.setFechaCalendarioMedicion(fechaHoy);
		calendariomedicion.setFechaFinVigenciaCM(null);
		
		
		Medicion medicion = new Medicion();
		medicion.setFechaAltaMedicion(fechaHoy);
		medicion.setFechaFinVigenciaM(null);
		medicion.setNombreMedicion("Frecuencia cardiaca en reposo");
		medicion.setUnidadMedidaMedicion("LPM");
		
		calendariomedicion.setMedicion(medicion);
		List<CalendarioMedicion> calendariomed = new ArrayList<>();
		calendariomed.add(calendariomedicion);
		medicion.setCalendariomed(calendariomed);
		
		
		System.out.println(medicion.getCalendariomed());
		medicionDao.save(medicion);
		
		
	}
	
	//@Test
	void getAllMedicionesAndDelete() {
		List<Medicion> mediciones = medicionDao.getAllMediciones();
		for (Medicion medicion : mediciones) {
			medicionDao.delete(medicion);
		}
		
	}

}
