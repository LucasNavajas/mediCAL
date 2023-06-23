package com.medical.springserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.consejo.Consejo;
import com.medical.springserver.model.historialfinvigencia.HistorialFinVigencia;
import com.medical.springserver.model.tipoconsejo.TipoConsejo;
import com.medical.springserver.model.tipoconsejo.TipoConsejoDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsTipoConsejo {
	
	@Autowired
	private TipoConsejoDao tipoConsejoDao;
	
	@Test
	void addTipoConsejoTest() {
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		Consejo consejo = new Consejo();
		consejo.setNombreConsejo("Consejo Binestar y Salud");
		consejo.setLikeConsejo(false);
		consejo.setLinkConsejo("www.youtube.com");
		consejo.setFechaAltaConsejo(fechaHoy);
		consejo.setDescConsejo("7 consejos para celiacos");

		
		TipoConsejo tipoConsejo = new TipoConsejo();
		tipoConsejo.setNombreTipoConsejo("Medico");

		consejo.setTipoconsejo(tipoConsejo);
		List<Consejo> varconsejo = new ArrayList<>();
		varconsejo.add(consejo);
		tipoConsejo.setVarconsejo(varconsejo);
		
		
		System.out.println(tipoConsejo.getVarconsejo());
		tipoConsejoDao.save(tipoConsejo);
	
		
	}
	
	//@Test
	void getAllTipoConsejosAndDelete() {
		List<TipoConsejo> tipoConsejos = tipoConsejoDao.getAllTipoConsejo();
		for (TipoConsejo tipoConsejo : tipoConsejos) {
			tipoConsejoDao.delete(tipoConsejo);
		}
		
	}

}