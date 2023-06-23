package com.medical.springserver;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.recordatorio.Recordatorio;
import com.medical.springserver.model.recordatorio.RecordatorioDao;


@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
public class SpringServerApplicationTestsRecordatorio {
	@Autowired
	private RecordatorioDao recordatorioDao;
	
	@Test 
	void addRecordatorioTest() {
		Recordatorio recordatorio = new Recordatorio();
		recordatorio.setDuracionRecordatorio(10);
		recordatorio.setFechaAltaRecordatorio(LocalDate.now());
		recordatorio.setFechaFinRecordatorio(LocalDate.of(2023, 7, 21));
		recordatorio.setFechaInicioRecordatorio(LocalDate.now());
		recordatorio.setFechaFinVigenciaR(LocalDate.of(2023, 7, 21));
		recordatorio.setHorarioRecordatorio(7);
		
		recordatorioDao.save(recordatorio);
	}
	
	//@Test
	void getAllRecordatoriosAndDelete() {
		List<Recordatorio> recordatorios = recordatorioDao.getAllRecordatorios();
		for (Recordatorio recordatorio : recordatorios) {
			recordatorioDao.delete(recordatorio);
		}
	}
}