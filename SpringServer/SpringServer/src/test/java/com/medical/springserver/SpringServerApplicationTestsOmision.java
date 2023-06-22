package com.medical.springserver;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.omision.Omision;
import com.medical.springserver.model.omision.OmisionDao;


@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
public class SpringServerApplicationTestsOmision {
	
	@Autowired 
	private OmisionDao omisionDao;
	
	@Test
	void addOmisionTest(){
		Omision omision = new Omision();
		omision.setNombreOmision("No tengo el medicamento cerca");
		omisionDao.save(omision);
	}
	
	//@Test
	void getAllOmisionesAndDelete() {
		List<Omision> omisiones = omisionDao.getAllOmisiones();
		for(Omision omision : omisiones) {
			omisionDao.delete(omision);
		}
	}

}
