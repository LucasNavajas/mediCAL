package com.medical.springserver;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.administracionmed.AdministracionMed;
import com.medical.springserver.model.administracionmed.AdministracionMedDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsAdministracionMed {
	
	@Autowired
	private AdministracionMedDao administracionmedDao;
	
	@Test
	void addAdministracionMedTest() {
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		AdministracionMed administracionmed = new AdministracionMed();
		administracionmed.setDescAdministracionMed("Incluye presentaciones: pastilla, comprimido, cápsula, etc.");
		administracionmed.setFechaAltaAdministracionMed(fechaHoy);
		administracionmed.setFechaFinVigenciaAM(null);
		administracionmed.setNombreAdministracionMed("De Forma Oral");
		
		administracionmedDao.save(administracionmed);
	
	}
	
	//@Test
	void getAllAdministracionMedsAndDelete() {
		List<AdministracionMed> administracionmeds = administracionmedDao.getAllAdministracionMeds();
		for (AdministracionMed administracionmed : administracionmeds) {
			administracionmedDao.delete(administracionmed);
		}
		
	}

}

