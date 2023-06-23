package com.medical.springserver;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.concentracion.Concentracion;
import com.medical.springserver.model.concentracion.ConcentracionDao;
import com.medical.springserver.model.dosis.Dosis;
import com.medical.springserver.model.dosis.DosisDao;


@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsDosis{
	
	@Autowired
	private DosisDao dosisDao;
	@Autowired
	private ConcentracionDao concentracionDao;
	
	@Test
	void addDosisTest() {
		Dosis dosis = new Dosis();
		dosis.setCantidadDosis(4);
		
		Concentracion concentracion = new Concentracion();
        concentracion.setUnidadMedidaC("g");
        concentracion.setValorConcentracion(0.6f); // Agrega la letra 'f' para indicar que es un float

        concentracionDao.save(concentracion);
        
        dosis.setConcentracion(concentracion);
		dosisDao.save(dosis);
		
	}
	
	//@Test
	void getAllDosis() {
		List<Dosis> dosiss = dosisDao.getAllDosis();
		for (Dosis dosis : dosiss) {
			dosisDao.delete(dosis);
		}
		
	}

}