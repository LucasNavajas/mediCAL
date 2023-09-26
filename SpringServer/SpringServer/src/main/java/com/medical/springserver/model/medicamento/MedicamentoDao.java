package com.medical.springserver.model.medicamento;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class MedicamentoDao {
	
	@Autowired
	private MedicamentoRepository repository;
	
	public Medicamento save(Medicamento medicamento) {
		return repository.save(medicamento);
	}
	
	public List<Medicamento> getAllMedicamentos(){
		return repository.getAllMedicamentos();
	}
	
	public void delete(Medicamento medicamento) {
		repository.delete(medicamento);
	}
	
	public List<Medicamento> getAllMedicamentosGenericos(){
		return repository.obtenerMedicamentosGenericos();
	}
	
	public Medicamento bajaMedicamento(int codMedicamento) {
		Medicamento medicamento = repository.findByCodMedicamento(codMedicamento);
		medicamento.setFechaFinVigenciaMed(LocalDate.now());
		return repository.save(medicamento);
	}
}
