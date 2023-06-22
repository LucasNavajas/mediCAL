package com.medical.springserver;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.presentacionMed.PresentacionMed;
import com.medical.springserver.model.presentacionMed.PresentacionMedDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
public class SpringServerApplicationTestsPresentacionMed {
	
	@Autowired
	private PresentacionMedDao presentacionMedDao;
	
	@Test
	void addPresentacionMed() {
		PresentacionMed presentacionMed = new PresentacionMed();
		presentacionMed.setDescPresentacionMed("Pastilla");
		presentacionMed.setNombrePresentacionMed("Oral");
		presentacionMed.setFechaAltaPresentacionMed(LocalDate.now());
		presentacionMed.setFechaFinVigenciaPM(null);
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
