package com.medical.springserver;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.dosis.Dosis;
import com.medical.springserver.model.dosis.DosisDao;


@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsDosis{
	
	@Autowired
	private DosisDao dosisDao;
	
	@Test
	void addDosisTest() {
		Dosis dosis = new Dosis();
		dosis.setCantidadDosis(4);
		dosisDao.save(dosis);
		
	}
	
	//@Test
	void getAllDosis() {
		List<Dosis> dosiss = dosisDao.getAllDosis();
		for (Dosis dosis : dosiss) {
			dosisDao.delete(dosis);
		}
		
	}

}