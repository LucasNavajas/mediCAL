package com.medical.springserver;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.medicamento.Medicamento;
import com.medical.springserver.model.medicamento.MedicamentoDao;



@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
public class SpringServerApplicationTestsMedicamento {
	
	@Autowired
	private MedicamentoDao medicamentoDao;
	
	@Test
	void addMedicamentoTest() {
		
		Medicamento medicamento = new Medicamento();
		medicamento.setEsParticular(false);
		medicamento.setFechaAltaMedicamento(LocalDate.now());
		medicamento.setFechaFinVigenciaMed(null);
		medicamento.setMarcaMedicamento("Bayer");
		medicamento.setNombreMedicamento("Ibuprofeno");
		medicamentoDao.save(medicamento);
		
		Medicamento medicamento2 = new Medicamento();
		medicamento2.setEsParticular(false);
		medicamento2.setFechaAltaMedicamento(LocalDate.now());
		medicamento2.setFechaFinVigenciaMed(null);
		medicamento2.setMarcaMedicamento("Tylenol");
		medicamento2.setNombreMedicamento("Paracetamol");
		medicamentoDao.save(medicamento2);
		
		Medicamento medicamento3 = new Medicamento();
		medicamento3.setEsParticular(false);
		medicamento3.setFechaAltaMedicamento(LocalDate.now());
		medicamento3.setFechaFinVigenciaMed(null);
		medicamento3.setMarcaMedicamento("Prilosec");
		medicamento3.setNombreMedicamento("Omeprazol");
		medicamentoDao.save(medicamento3);
		
		Medicamento medicamento4 = new Medicamento();
		medicamento4.setEsParticular(false);
		medicamento4.setFechaAltaMedicamento(LocalDate.now());
		medicamento4.setFechaFinVigenciaMed(null);
		medicamento4.setMarcaMedicamento("Claritin");
		medicamento4.setNombreMedicamento("Loratadina");
		medicamentoDao.save(medicamento4);
		
		Medicamento medicamento5 = new Medicamento();
		medicamento5.setEsParticular(false);
		medicamento5.setFechaAltaMedicamento(LocalDate.now());
		medicamento5.setFechaFinVigenciaMed(null);
		medicamento5.setMarcaMedicamento("Zocor");
		medicamento5.setNombreMedicamento("Simvastatina");
		medicamentoDao.save(medicamento5);
		
		Medicamento medicamento6 = new Medicamento();
		medicamento6.setEsParticular(false);
		medicamento6.setFechaAltaMedicamento(LocalDate.now());
		medicamento6.setFechaFinVigenciaMed(null);
		medicamento6.setMarcaMedicamento("Amoxil");
		medicamento6.setNombreMedicamento("Amoxicilina");
		medicamentoDao.save(medicamento6);
		
	}
	
	//@Test
	void getAllMedicamentosAndDelete() {
		List<Medicamento> medicamentos = medicamentoDao.getAllMedicamentos();
		for(Medicamento medicamento : medicamentos) {
			medicamentoDao.delete(medicamento);
		}
	}
}
