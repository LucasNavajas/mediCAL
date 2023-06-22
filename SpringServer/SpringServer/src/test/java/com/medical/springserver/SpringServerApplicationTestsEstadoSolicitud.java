package com.medical.springserver;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.estadosolicitud.EstadoSolicitud;
import com.medical.springserver.model.estadosolicitud.EstadoSolicitudDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsEstadoSolicitud {
	
	@Autowired
	private EstadoSolicitudDao estadoSolicitudDao;
	
	@Test
	void addEstadoSolicitudTest() {
		
		
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
	    estadoSolicitud.setNombreEstadoSolicitud("Aceptada");
		estadoSolicitudDao.save(estadoSolicitud);
		
		
	}
	
	//@Test
	void getAllEstadoSolicitudAndDelete() {
		List<EstadoSolicitud> estadoSolicitudes = estadoSolicitudDao.getAllEstadoSolicitud();
		for (EstadoSolicitud estadoSolicitud : estadoSolicitudes) {
			estadoSolicitudDao.delete(estadoSolicitud);
		}
		
	}

}
