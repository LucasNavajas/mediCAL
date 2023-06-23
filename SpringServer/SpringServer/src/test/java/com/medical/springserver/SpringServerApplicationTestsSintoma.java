package com.medical.springserver;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.sintoma.Sintoma;
import com.medical.springserver.model.sintoma.SintomaDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsSintoma {
	
	@Autowired
	private SintomaDao sintomaDao;
	
	@Test
	void addSintomaTest() {
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		Sintoma sintoma = new Sintoma();
		sintoma.setFechaAltaSintoma(fechaHoy);
		sintoma.setFechaFinVigenciaS(null);
		sintoma.setNombreSintoma("Tos");
		
		sintomaDao.save(sintoma);
		
	}
	
	//@Test
	void getAllSintomasAndDelete() {
		List<Sintoma> sintomas = sintomaDao.getAllSintomas();
		for (Sintoma sintoma : sintomas) {
			sintomaDao.delete(sintoma);
		}
		
	}

}
