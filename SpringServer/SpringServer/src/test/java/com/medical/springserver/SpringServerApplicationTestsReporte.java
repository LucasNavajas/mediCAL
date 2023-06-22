package com.medical.springserver;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.reporte.Reporte;
import com.medical.springserver.model.reporte.ReporteDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsReporte {
	
	@Autowired
	private ReporteDao reporteDao;
	
	@Test
	void addReporteTest() {
		
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		LocalDate fechaDesde = LocalDate.of(2022, 01, 6);
		LocalDate fechaHasta = LocalDate.of(2023, 06, 2);
		
		Reporte reporte = new Reporte();
		reporte.setFechaDesde(fechaDesde);
		reporte.setFechaGenerada(fechaHoy);
		reporte.setFechaHasta(fechaHasta);
	
		reporteDao.save(reporte);	
		
	}
	
	//@Test
	void getAllReportesAndDelete() {
		List<Reporte> reportes = reporteDao.getAllReporte();
		for (Reporte reporte : reportes) {
			reporteDao.delete(reporte);
		}
		
	}

}
