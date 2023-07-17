package com.medical.springserver;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.codigoverificacion.CodigoVerificacion;
import com.medical.springserver.model.codigoverificacion.CodigoVerificacionDao;
import com.medical.springserver.model.codigoverificacion.CodigoVerificacionRepository;


@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
public class SpringServerApplicationTestsCodigoVerificacion {
	
	@Autowired 
	private CodigoVerificacionDao codigoverificacionDao;

	
	@Test
	void addCodigoVerificacionTest(){
		CodigoVerificacion codigoverificacion = new CodigoVerificacion();
		codigoverificacion.setCodVerificacion("1701");
		codigoverificacionDao.save(codigoverificacion);
	}
	
	//@Test
	void getAllCodigosVerificacionAndDelete() {
		List<CodigoVerificacion> codigosverificacion = codigoverificacionDao.getAllCodigosVerificacion();
		for(CodigoVerificacion codigoverificacion : codigosverificacion) {
			codigoverificacionDao.delete(codigoverificacion);
		}
	}

}

