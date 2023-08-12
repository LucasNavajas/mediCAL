package com.medical.springserver;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.administracionmed.AdministracionMed;
import com.medical.springserver.model.administracionmed.AdministracionMedDao;
import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.historialfinvigencia.HistorialFinVigencia;
import com.medical.springserver.model.presentacionMed.PresentacionMed;
import com.medical.springserver.model.presentacionMed.PresentacionMedDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
public class SpringServerApplicationTestsPresentacionMed {
	
	@Autowired
	private PresentacionMedDao presentacionMedDao;
	
	@Autowired
	private AdministracionMedDao adminMedDao;
	
	@Test
	void addPresentacionMed() {
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		
		
		// instancia de presentacion med
		
		//instancia de administracion med
		AdministracionMed administracionmed = new AdministracionMed();
		administracionmed.setDescAdministracionMed("Incluye presentaciones: crema, spray y locion");
		administracionmed.setFechaAltaAdministracionMed(fechaHoy);
		administracionmed.setFechaFinVigenciaAM(null);
		administracionmed.setNombreAdministracionMed("De Forma Topica");
	
		PresentacionMed presentacionMed = new PresentacionMed();
		presentacionMed.setDescPresentacionMed("Administracion topica");
		presentacionMed.setNombrePresentacionMed("Crema");
		presentacionMed.setFechaAltaPresentacionMed(LocalDate.now());
		presentacionMed.setFechaFinVigenciaPM(null);
		presentacionMed.setVaradministracionmed(administracionmed);
		
		adminMedDao.save(administracionmed);
		
		presentacionMedDao.save(presentacionMed);
		
	}
	
	//@Test
	void getAllPresentacionesMedAndDelete() {
		List<PresentacionMed> presentacionesMed = presentacionMedDao.getAllPresentacionesMed();
		for (PresentacionMed presentacionMed : presentacionesMed) {
			presentacionMedDao.delete(presentacionMed);
		}
	}

}
