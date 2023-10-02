package com.medical.springserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.consejo.Consejo;
import com.medical.springserver.model.consejo.ConsejoDao;
import com.medical.springserver.model.tipoconsejo.TipoConsejo;
import com.medical.springserver.model.tipoconsejo.TipoConsejoDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsConsejo {
	
	@Autowired
	private ConsejoDao consejoDao;
	@Autowired
	private TipoConsejoDao tipoConsejoDao;
	
	//@Test
	void addConsejoTest() {	
		
		// Usar Tests de TipoConsejo
		
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();	
			
		Consejo consejo = new Consejo();
		consejo.setNombreConsejo("Su consejo diario de MediCAL!");
		consejo.setLinkConsejo("www.youtube.com");
		consejo.setFechaAltaConsejo(fechaHoy);
		consejo.setAuspiciante("MediCAL");
		consejo.setDescConsejo("¡Es fácil añadir un medicamento! " + " Pulse en “Leer más” para obtener más información. ");
		
		
		TipoConsejo tipoConsejo3 = new TipoConsejo();
		tipoConsejo3.setNombreTipoConsejo("Sobre la App");
		tipoConsejoDao.save(tipoConsejo3);
		
		List<Consejo> consejos = new ArrayList<>();
		consejos.add(consejo);
		
		consejo.setTipoConsejo(tipoConsejo3);
		consejoDao.save(consejo);
		tipoConsejoDao.save(tipoConsejo3);	
		
				
	}
	
	//@Test
	void getAllConsejosAndDelete() {
		List<Consejo> consejos = consejoDao.getAllConsejo();
		for (Consejo consejo : consejos) {
			consejoDao.delete(consejo);
		}
		
	}

}
