package com.medical.springserver;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.solicitud.Solicitud;
import com.medical.springserver.model.solicitud.SolicitudDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsSolicitud {
	
	@Autowired
	private SolicitudDao solicitudDao;
	
	@Test
	void addSolicitudTest() {
				
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		Solicitud solicitud = new Solicitud();
		solicitud.setFechaSolicitud(fechaHoy);
		
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
