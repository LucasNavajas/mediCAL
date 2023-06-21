package com.medical.springserver;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.frecuencia.Frecuencia;
import com.medical.springserver.model.frecuencia.FrecuenciaDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsFrecuencia {
	
	@Autowired
	private FrecuenciaDao frecuenciaDao;
	
	@Test
	void addFrecuenciaTest() {
		Frecuencia frecuencia = new Frecuencia();
		frecuencia.setCantidadFrecuencia(7);
		frecuencia.setDiasDescansoF(1);
		frecuencia.setDiasTomaF(6);
		frecuencia.setNombreFrecuencia("6 dias toma 1 descanso");
		frecuenciaDao.save(frecuencia);
	}
	
	//@Test
	void getAllFrecuneciasAndDelete() {
		List<Frecuencia> frecuencias = frecuenciaDao.getAllFrecuencias();
		for(Frecuencia frecuencia : frecuencias) {
			frecuenciaDao.delete(frecuencia);
		}
	}

}
