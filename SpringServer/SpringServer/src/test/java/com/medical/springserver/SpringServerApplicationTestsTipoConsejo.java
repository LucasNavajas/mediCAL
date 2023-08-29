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
class SpringServerApplicationTestsTipoConsejo {
	
	@Autowired
	private TipoConsejoDao tipoConsejoDao;
	@Autowired
	private ConsejoDao consejoDao;
	
	@Test
	void addTipoConsejoTest() {
		
		TipoConsejo tipoConsejo = new TipoConsejo();
		tipoConsejo.setNombreTipoConsejo("Medico"); // consejos médicos respaldados por fuentes (link a video)
		tipoConsejoDao.save(tipoConsejo);
		
		TipoConsejo tipoConsejo2 = new TipoConsejo();
		tipoConsejo2.setNombreTipoConsejo("Binestar y Salud"); // consejos normales sobre bienestar (link a noticia)
		tipoConsejoDao.save(tipoConsejo2);
		
		TipoConsejo tipoConsejo3 = new TipoConsejo();
		tipoConsejo3.setNombreTipoConsejo("Sobre la App"); // consejos sobre el uso de la app (link a manual de usuario)
		tipoConsejoDao.save(tipoConsejo3);
		
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		Consejo consejo = new Consejo();
		consejo.setNombreConsejo("Su consejo diario de MediCAL!");
		// Colocar luego link a Manual de Usuario
		consejo.setLinkConsejo("null");
		consejo.setFechaAltaConsejo(fechaHoy);
		consejo.setAuspiciante("MediCAL");
		consejo.setDescConsejo("¡Es fácil añadir un medicamento! " + " Pulse en “Leer más” para obtener más información. ");
		consejo.setTipoConsejo(tipoConsejo3);
		consejoDao.save(consejo);
		
		
		Consejo consejo1 = new Consejo();
		consejo1.setNombreConsejo("Consejo Diario de Bienestar y Salud");;
		// Link a noticia relacionada: 
		consejo1.setLinkConsejo("https://www.nhlbi.nih.gov/es/salud/sueno/por-que-el-sueno-es-importante");
		consejo1.setFechaAltaConsejo(fechaHoy);
		consejo1.setDescConsejo("Dormir bien es muy importante. No dormir lo suficiente puede afectar tus hormonas, así como tu salud física y mental.");
		consejo1.setTipoConsejo(tipoConsejo2);
		consejoDao.save(consejo1);
		
		
		Consejo consejo2 = new Consejo();
		consejo2.setNombreConsejo("Consejo Médico Auspiciado");
		// Link a video relacionado:
		consejo2.setLinkConsejo("https://www.youtube.com/watch?v=a2EuM2w4LrI");
		consejo2.setFechaAltaConsejo(fechaHoy);
		consejo2.setAuspiciante("Bagó");
		consejo2.setDescConsejo("7 Cuidados básicos para personas con hipertensión.");
		consejo2.setFotoConsejo("https://img.youtube.com/vi/a2EuM2w4LrI/maxresdefault.jpg");
		consejo2.setTipoConsejo(tipoConsejo);
		consejoDao.save(consejo2);
		
		
		
		//System.out.println(tipoConsejo.getVarconsejo());
		
	}
	
	//@Test
	void getAllTipoConsejosAndDelete() {
		List<TipoConsejo> tipoConsejos = tipoConsejoDao.getAllTipoConsejo();
		for (TipoConsejo tipoConsejo : tipoConsejos) {
			tipoConsejoDao.delete(tipoConsejo);
		}
		
	}

}