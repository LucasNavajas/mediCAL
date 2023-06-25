package com.medical.springserver;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.dosis.Dosis;
import com.medical.springserver.model.dosis.DosisDao;
import com.medical.springserver.model.frecuencia.Frecuencia;
import com.medical.springserver.model.frecuencia.FrecuenciaDao;
import com.medical.springserver.model.instruccion.Instruccion;
import com.medical.springserver.model.instruccion.InstruccionDao;
import com.medical.springserver.model.inventario.Inventario;
import com.medical.springserver.model.inventario.InventarioDao;
import com.medical.springserver.model.medicamento.Medicamento;
import com.medical.springserver.model.medicamento.MedicamentoDao;
import com.medical.springserver.model.recordatorio.Recordatorio;
import com.medical.springserver.model.recordatorio.RecordatorioDao;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorio;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorioDao;


@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
public class SpringServerApplicationTestsRecordatorio {
	@Autowired
	private RecordatorioDao recordatorioDao;
	@Autowired
	private RegistroRecordatorioDao registrorecordatorioDao;
	@Autowired
	private DosisDao dosisDao;
	@Autowired
	private FrecuenciaDao frecuenciaDao;
	@Autowired
	private MedicamentoDao medicamentoDao;
	@Autowired
	private InstruccionDao instruccionDao;
	@Autowired
	private InventarioDao inventarioDao;
	
	@Test 
	void addRecordatorioTest() {
			
		Recordatorio recordatorio = new Recordatorio();
		recordatorio.setDuracionRecordatorio(10);
		recordatorio.setFechaAltaRecordatorio(LocalDate.now());
		recordatorio.setFechaFinRecordatorio(LocalDate.of(2023, 7, 21));
		recordatorio.setFechaInicioRecordatorio(LocalDate.now());
		recordatorio.setFechaFinVigenciaR(LocalDate.of(2023, 7, 21));
		recordatorio.setHorarioRecordatorio(7);
		
		// instancia de RegistroRecordatorio
		RegistroRecordatorio registrorecordatorio = new RegistroRecordatorio();
		registrorecordatorio.setFechaFinVigenciaRR(null);
		registrorecordatorio.setFechaTomaEsperada(LocalDateTime.now());
		registrorecordatorio.setFechaTomaReal(LocalDateTime.now());
		registrorecordatorio.setNroRegistro(1);
		registrorecordatorio.setTomaRegistroRecordatorio(true);
		registrorecordatorioDao.save(registrorecordatorio);
		
		// instancia de Dosis
		Dosis dosis = new Dosis();
		dosis.setCantidadDosis(4);
		dosisDao.save(dosis);
		
		// instancia de Frecuencia
		Frecuencia frecuencia = new Frecuencia();
		frecuencia.setCantidadFrecuencia(7);
		frecuencia.setDiasDescansoF(1);
		frecuencia.setDiasTomaF(6);
		frecuencia.setNombreFrecuencia("6 dias toma 1 descanso");
		frecuenciaDao.save(frecuencia);
		
		// instancia de Medicamento
		Medicamento medicamento = new Medicamento();
		medicamento.setEsParticular(false);
		medicamento.setFechaAltaMedicamento(LocalDate.now());
		medicamento.setFechaFinVigenciaMed(null);
		medicamento.setNombreMedicamento("Ibuprofeno");
		medicamento.setMarcaMedicamento("Actron");
		medicamentoDao.save(medicamento);
		
		// instancia de Instruccion
		Instruccion instruccion = new Instruccion();
		instruccion.setDescInstruccion("Tomar con agua");
		instruccion.setNombreInstruccion("Antes de Comer");
		instruccionDao.save(instruccion);
		
		// instancia de Inventario
		Inventario inventario = new Inventario();
		inventario.setCantAvisoInventario(5);
		inventario.setCantRealInventario(10);
		inventarioDao.save(inventario);
		
		recordatorio.setDosis(dosis);
		recordatorio.setFrecuencia(frecuencia);
		recordatorio.setMedicamento(medicamento);
		recordatorio.setInstruccion(instruccion);
		recordatorio.setInventario(inventario);
		
		registrorecordatorio.setRecordatorio(recordatorio);
		List<RegistroRecordatorio> regrecordatorio = new ArrayList<>();
		regrecordatorio.add(registrorecordatorio);
		recordatorio.setRegistroRecordatorio(regrecordatorio);
		
		
		System.out.println(recordatorio.getRegistroRecordatorio());
		
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
