package com.medical.springserver.model.medicamento;
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
		Streamable<Medicamento> streamableMedicamentos = Streamable.of(repository.findAll());
		List<Medicamento> medicamentos = new ArrayList<>();
		streamableMedicamentos.forEach(medicamentos::add);
		return medicamentos;
	}
	
	public void delete(Medicamento medicamento) {
		repository.delete(medicamento);
	}
}
