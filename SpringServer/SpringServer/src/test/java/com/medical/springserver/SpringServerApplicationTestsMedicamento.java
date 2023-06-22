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
	}
	
	//@Test
	void getAllMedicamentosAndDelete() {
		List<Medicamento> medicamentos = medicamentoDao.getAllMedicamentos();
		for(Medicamento medicamento : medicamentos) {
			medicamentoDao.delete(medicamento);
		}
	}
}
