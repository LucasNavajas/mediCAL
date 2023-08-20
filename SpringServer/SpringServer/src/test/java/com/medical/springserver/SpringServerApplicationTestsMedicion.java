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
	    LocalDate fechaHoy = LocalDate.now();

	    List<String> nombresMediciones = new ArrayList<>();
	    nombresMediciones.add("Peso");
	    nombresMediciones.add("Glucosa en sangre");
	    nombresMediciones.add("Presion arterial");
	    nombresMediciones.add("Capacidad vital");
	    nombresMediciones.add("Aféresis");
	    nombresMediciones.add("Circunferencia de la cintura");
	    nombresMediciones.add("Circunferencia del brazo");
	    nombresMediciones.add("Circunferencia del muslo");
	    nombresMediciones.add("Circunferencia del torax");
	    nombresMediciones.add("Ferritina");
	    nombresMediciones.add("Frecuencia respiratoria");

	    for (String nombreMedicion : nombresMediciones) {
	        Medicion medicion = new Medicion();
	        medicion.setFechaAltaMedicion(fechaHoy);
	        medicion.setFechaFinVigenciaM(null);
	        medicion.setNombreMedicion(nombreMedicion);
	        
	        if (nombreMedicion.equals("Peso")) {
	            medicion.setUnidadMedidaMedicion("kg");
	        } else if (nombreMedicion.equals("Glucosa en sangre")) {
	            medicion.setUnidadMedidaMedicion("mg/dL");
	        } else if (nombreMedicion.equals("Presion arterial")) {
	            medicion.setUnidadMedidaMedicion("mmHg");
	        } else if (nombreMedicion.equals("Capacidad vital")) {
	            medicion.setUnidadMedidaMedicion("litros");
	        } else if (nombreMedicion.equals("Aféresis")) {
	            medicion.setUnidadMedidaMedicion("Unidad"); // Cambiar esto a la unidad correcta
	        } else if (nombreMedicion.equals("Circunferencia de la cintura") || nombreMedicion.equals("Circunferencia del brazo")
	                || nombreMedicion.equals("Circunferencia del muslo") || nombreMedicion.equals("Circunferencia del torax")) {
	            medicion.setUnidadMedidaMedicion("cm");
	        } else if (nombreMedicion.equals("Ferritina")) {
	            medicion.setUnidadMedidaMedicion("ng/mL");
	        } else if (nombreMedicion.equals("Frecuencia respiratoria")) {
	            medicion.setUnidadMedidaMedicion("rpm");
	        }

	        CalendarioMedicion calendariomedicion = new CalendarioMedicion();
	        calendariomedicion.setFechaCalendarioMedicion(fechaHoy);
	        calendariomedicion.setFechaFinVigenciaCM(null);

	        calendariomedicion.setMedicion(medicion);
	        List<CalendarioMedicion> calendariomed = new ArrayList<>();
	        calendariomed.add(calendariomedicion);
	        medicion.setCalendariomed(calendariomed);

	        medicionDao.save(medicion);
	    }
	}

	
	//@Test
	void getAllMedicionesAndDelete() {
		List<Medicion> mediciones = medicionDao.getAllMediciones();
		for (Medicion medicion : mediciones) {
			medicionDao.delete(medicion);
		}
	}
}