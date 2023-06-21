package com.medical.springserver;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.historialfinvigencia.HistorialFinVigencia;
import com.medical.springserver.model.historialfinvigencia.HistorialFinVigenciaDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsHistorialFinVigencia {
	
	@Autowired
	private HistorialFinVigenciaDao historialfinvigenciaDao;
	
	@Test
	void addHistorialFinVigenciaTest() {
		
		// Assuming you have a Connection object named 'connection'
		LocalDate fechaDesde = LocalDate.of(2023, 1, 1);

		
		// Obtener la fecha de hoy
		LocalDate fechaHasta = LocalDate.of(2023,9,1);
		
		HistorialFinVigencia historialfinvigencia = new HistorialFinVigencia();
		historialfinvigencia.setFechaDesdeFV(fechaDesde);
		historialfinvigencia.setFechaHastaFV(fechaHasta);
		historialfinvigencia.setMotivoFV("Licencia por maternidad");
		historialfinvigenciaDao.save(historialfinvigencia);
		
		
	}
	
	//@Test
	void getAllHistorialFinVigenciasAndDelete() {
		List<HistorialFinVigencia> historialfinvigencias = historialfinvigenciaDao.getAllHistorialFinVigencias();
		for (HistorialFinVigencia historialfinvigencia : historialfinvigencias) {
			historialfinvigenciaDao.delete(historialfinvigencia);
		}
		
	}

}

