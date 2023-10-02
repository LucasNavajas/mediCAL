package com.medical.springserver;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.tiporeporte.TipoReporte;
import com.medical.springserver.model.tiporeporte.TipoReporteDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsTipoReporte{
	
	@Autowired
	private TipoReporteDao tipoReporteDao;
	
	//@Test
	void addTipoReporteTest() {
		
		
		TipoReporte tipoReporte = new TipoReporte();
		tipoReporte.setNombreTipoReporte("Medicamentos consumidos");
		tipoReporteDao.save(tipoReporte);
		
		
	}
	
	//@Test
	void getAllTipoReportesAndDelete() {
		List<TipoReporte> tipoReportes = tipoReporteDao.getAllTipoReporte();
		for (TipoReporte tipoReporte : tipoReportes) {
			tipoReporteDao.delete(tipoReporte);
		}
		
	}

}