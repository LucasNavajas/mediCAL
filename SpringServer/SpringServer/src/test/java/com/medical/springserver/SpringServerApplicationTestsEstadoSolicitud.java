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
		
		EstadoSolicitud estadoSolicitud2 = new EstadoSolicitud();
	    estadoSolicitud2.setNombreEstadoSolicitud("Rechazada");
		estadoSolicitudDao.save(estadoSolicitud2);
		
		EstadoSolicitud estadoSolicitud3 = new EstadoSolicitud();
	    estadoSolicitud3.setNombreEstadoSolicitud("Baja");
		estadoSolicitudDao.save(estadoSolicitud3);
		
		EstadoSolicitud estadoSolicitud4 = new EstadoSolicitud();
	    estadoSolicitud4.setNombreEstadoSolicitud("Pendiente");
		estadoSolicitudDao.save(estadoSolicitud4);
		
		EstadoSolicitud estadoSolicitud5 = new EstadoSolicitud();
	    estadoSolicitud5.setNombreEstadoSolicitud("Aceptado sin visualizar");
		estadoSolicitudDao.save(estadoSolicitud5);
		
		EstadoSolicitud estadoSolicitud6 = new EstadoSolicitud();
	    estadoSolicitud6.setNombreEstadoSolicitud("Rechazado sin visualizar");
		estadoSolicitudDao.save(estadoSolicitud6);
		
		EstadoSolicitud estadoSolicitud7 = new EstadoSolicitud();
	    estadoSolicitud7.setNombreEstadoSolicitud("Desvinculado sin visualizar");
		estadoSolicitudDao.save(estadoSolicitud7);
		
	}
	
	//@Test
	void getAllEstadoSolicitudAndDelete() {
		List<EstadoSolicitud> estadoSolicitudes = estadoSolicitudDao.getAllEstadoSolicitud();
		for (EstadoSolicitud estadoSolicitud : estadoSolicitudes) {
			estadoSolicitudDao.delete(estadoSolicitud);
		}
		
	}

}
