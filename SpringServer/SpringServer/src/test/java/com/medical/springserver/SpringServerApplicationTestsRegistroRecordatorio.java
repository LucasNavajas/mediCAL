package com.medical.springserver;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.omision.Omision;
import com.medical.springserver.model.omision.OmisionDao;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorio;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorioDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
public class SpringServerApplicationTestsRegistroRecordatorio {
	
	@Autowired
	private RegistroRecordatorioDao registroRecordatorioDao;
	private OmisionDao omisionDao;
	
	@Test 
	void addRegistroRecordatorio() {
		RegistroRecordatorio registroRecordatorio = new RegistroRecordatorio();
		registroRecordatorio.setFechaFinVigenciaRR(LocalDate.of(2023, 8, 21));
		registroRecordatorio.setFechaTomaEsperada(LocalDateTime.of(2023, 6, 21, 8, 0));
		registroRecordatorio.setFechaTomaReal(LocalDateTime.of(2023,6,21,8,30));
		registroRecordatorio.setNroRegistro(1);
		registroRecordatorio.setTomaRegistroRecordatorio(true);
				
		// instancia de Omision
		Omision omision = new Omision();
		omision.setNombreOmision("Se quedó sin medicamento");
		omisionDao.save(omision);
				
		registroRecordatorio.setOmision(omision);
		
		registroRecordatorioDao.save(registroRecordatorio);
		
	}
	
	//@Test
	void getAllRegistrosRecordatorioAndDelete() {
		List<RegistroRecordatorio> registrosRecordatorio = registroRecordatorioDao.getAllRegistrosRecordatorio();
		for(RegistroRecordatorio registroRecordatorio : registrosRecordatorio) {
			registroRecordatorioDao.delete(registroRecordatorio);
		}
	}

}
