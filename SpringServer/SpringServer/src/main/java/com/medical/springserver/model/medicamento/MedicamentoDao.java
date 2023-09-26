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
        // Buscar la entidad existente por su código
        Medicamento existingMedicamento = repository.findById(medicamento.getCodMedicamento()).orElse(null);

        if (existingMedicamento != null) {
            if (medicamento.getFechaAltaMedicamento() != null) {
                existingMedicamento.setFechaAltaMedicamento(medicamento.getFechaAltaMedicamento());
            }
            if (medicamento.getFechaFinVigenciaMed() != null) {
                existingMedicamento.setFechaFinVigenciaMed(medicamento.getFechaFinVigenciaMed());
            }
            if (medicamento.getMarcaMedicamento() != null) {
                existingMedicamento.setMarcaMedicamento(medicamento.getMarcaMedicamento());
            }
            if (medicamento.getNombreMedicamento() != null) {
                existingMedicamento.setNombreMedicamento(medicamento.getNombreMedicamento());
            }

            // Guardar la entidad actualizada
            return repository.save(existingMedicamento);
        } else {
            // Si no se encuentra la entidad existente, crea una nueva
            return repository.save(medicamento);
        }
    }

	
	public List<Medicamento> getAllMedicamentos(){
		return repository.getAllMedicamentos(LocalDate.now());
	}
	
	public void delete(Medicamento medicamento) {
		repository.delete(medicamento);
	}
	
	public List<Medicamento> obtenerMedicamentosGenericos(){
		return repository.obtenerMedicamentosGenericos(LocalDate.now());
	}
	
	public List<Medicamento> getAllMedicamentosGenericos(){
		return repository.getAllMedicamentosGenericos();
	}
	
	public Medicamento bajaMedicamento(int codMedicamento) {
		Medicamento medicamento = repository.findByCodMedicamento(codMedicamento);
		medicamento.setFechaFinVigenciaMed(LocalDate.now());
		return repository.save(medicamento);
	}

	public Medicamento recuperarMedicamento(int codMedicamento) {
		Medicamento medicamento = repository.findByCodMedicamento(codMedicamento);
		medicamento.setFechaFinVigenciaMed(null);
		return repository.save(medicamento);
	}
}
