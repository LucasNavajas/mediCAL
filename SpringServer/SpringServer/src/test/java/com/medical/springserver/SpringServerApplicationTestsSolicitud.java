package com.medical.springserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.estadosolicitud.EstadoSolicitud;
import com.medical.springserver.model.estadosolicitud.EstadoSolicitudDao;
import com.medical.springserver.model.solicitud.Solicitud;
import com.medical.springserver.model.solicitud.SolicitudDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsSolicitud {
	
	@Autowired
	private SolicitudDao solicitudDao;
	@Autowired
	private EstadoSolicitudDao estadoSolicitudDao;
	
	@Test
	void addSolicitudTest() {
				
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		Solicitud solicitud = new Solicitud();
		EstadoSolicitud estadoS = new EstadoSolicitud()	;
		solicitud.setFechaSolicitud(fechaHoy);
		solicitud.setEstadoSolicitud(estadoS);
		
		estadoS.setNombreEstadoSolicitud("Aceptado");
		List<Solicitud> solicitudes = new ArrayList<>();	
		solicitudes.add(solicitud);
		estadoS.setSolicitudes(solicitudes);
		
		System.out.println(solicitud.getEstadoSolicitud());
		System.out.println(estadoS.getSolicitudes());
		estadoSolicitudDao.save(estadoS);
		solicitudDao.save(solicitud);
		
	}
	
	//@Test
	void getAllSolicitudesAndDelete() {
		List<Solicitud> solicitudes = solicitudDao.getAllSolicitudes();
		for (Solicitud solicitud : solicitudes) {
			solicitudDao.delete(solicitud);
		}
		
	}

}
