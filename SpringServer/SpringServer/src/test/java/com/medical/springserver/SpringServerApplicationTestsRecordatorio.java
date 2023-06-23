package com.medical.springserver;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.dosis.Dosis;
import com.medical.springserver.model.dosis.DosisDao;
import com.medical.springserver.model.frecuencia.Frecuencia;
import com.medical.springserver.model.frecuencia.FrecuenciaDao;
import com.medical.springserver.model.recordatorio.Recordatorio;
import com.medical.springserver.model.recordatorio.RecordatorioDao;


@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
public class SpringServerApplicationTestsRecordatorio {
	@Autowired
	private RecordatorioDao recordatorioDao;
	@Autowired
	private DosisDao dosisDao;
	@Autowired
	private FrecuenciaDao frecuenciaDao;
	
	@Test 
	void addRecordatorioTest() {
		Recordatorio recordatorio = new Recordatorio();
		recordatorio.setDuracionRecordatorio(10);
		recordatorio.setFechaAltaRecordatorio(LocalDate.now());
		recordatorio.setFechaFinRecordatorio(LocalDate.of(2023, 7, 21));
		recordatorio.setFechaInicioRecordatorio(LocalDate.now());
		recordatorio.setFechaFinVigenciaR(LocalDate.of(2023, 7, 21));
		recordatorio.setHorarioRecordatorio(7);
		
		
		Dosis dosis = new Dosis();
		dosis.setCantidadDosis(4);
		dosisDao.save(dosis);
		
		Frecuencia frecuencia = new Frecuencia();
		frecuencia.setCantidadFrecuencia(7);
		frecuencia.setDiasDescansoF(1);
		frecuencia.setDiasTomaF(6);
		frecuencia.setNombreFrecuencia("6 dias toma 1 descanso");
		frecuenciaDao.save(frecuencia);
		
		recordatorio.setDosis(dosis);
		recordatorio.setFrecuencia(frecuencia);
		
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
