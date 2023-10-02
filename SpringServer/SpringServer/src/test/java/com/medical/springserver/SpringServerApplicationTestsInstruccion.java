package com.medical.springserver;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.instruccion.Instruccion;
import com.medical.springserver.model.instruccion.InstruccionDao;


@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
public class SpringServerApplicationTestsInstruccion {

	@Autowired
	private InstruccionDao instruccionDao;
	
	//@Test
	void addInstruccionTest() {
		Instruccion instruccion = new Instruccion();	
		instruccion.setDescInstruccion("Tomar con agua");
		instruccion.setNombreInstruccion("Tomar en ayunas");
		instruccionDao.save(instruccion);
	}
	
	//@Test
	void getAllInstruccionesAndDelete() {
		List<Instruccion> instrucciones = instruccionDao.getAllInstrucciones();
		for(Instruccion instruccion : instrucciones) {
			instruccionDao.delete(instruccion);
		}
	}
}
