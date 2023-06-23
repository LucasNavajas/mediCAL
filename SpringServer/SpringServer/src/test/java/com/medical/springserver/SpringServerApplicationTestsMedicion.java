package com.medical.springserver;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
		
		Medicion medicion = new Medicion();
		medicion.setFechaAltaMedicion(fechaHoy);
		medicion.setFechaFinVigenciaM(null);
		medicion.setNombreMedicion("Peso");
		medicion.setUnidadMedidaMedicion("kg");
		
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
