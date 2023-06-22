package com.medical.springserver;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.tipoconsejo.TipoConsejo;
import com.medical.springserver.model.tipoconsejo.TipoConsejoDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsTipoConsejo {
	
	@Autowired
	private TipoConsejoDao tipoConsejoDao;
	
	@Test
	void addTipoConsejoTest() {
		
		
		TipoConsejo tipoConsejo = new TipoConsejo();
		tipoConsejo.setNombreTipoConsejo("Medico");
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