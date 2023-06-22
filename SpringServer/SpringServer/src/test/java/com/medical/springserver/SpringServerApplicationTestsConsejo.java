package com.medical.springserver;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.consejo.Consejo;
import com.medical.springserver.model.consejo.ConsejoDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsConsejo {
	
	@Autowired
	private ConsejoDao consejoDao;
	
	@Test
	void addConsejoTest() {
		
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		Consejo consejo = new Consejo();
		consejo.setNombreConsejo("Consejo Binestar y Salud");
		consejo.setLikeConsejo(false);
		consejo.setLinkConsejo("www.youtube.com");
		consejo.setFechaAltaConsejo(fechaHoy);
		consejo.setDescConsejo("¿Cuanta agua se recomienda beber al dia?...");
		consejoDao.save(consejo);
		
		
	}
	
	//@Test
	void getAllConsejosAndDelete() {
		List<Consejo> consejos = consejoDao.getAllConsejo();
		for (Consejo consejo : consejos) {
			consejoDao.delete(consejo);
		}
		
	}

}
