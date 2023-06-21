package com.medical.springserver;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.codigoverificacion.CodigoVerificacion;
import com.medical.springserver.model.codigoverificacion.CodigoVerificacionDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsCodigoVerificacion{
	
	@Autowired
	private CodigoVerificacionDao codigoverificacionDao;
	
	@Test
	void addCodigoVerificacionTest() {
				
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		CodigoVerificacion codigoverificacion = new CodigoVerificacion();
		codigoverificacion.setFechaGenerado(fechaHoy);

		codigoverificacionDao.save(codigoverificacion);
		
		
	}
	
	//@Test
	void getAllCodigosVerificacionAndDelete() {
		List<CodigoVerificacion> codigosverificacion = codigoverificacionDao.getAllCodigosVerificacion();
		for (CodigoVerificacion codigoverificacion : codigosverificacion) {
			codigoverificacionDao.delete(codigoverificacion);
		}
		
	}

}
