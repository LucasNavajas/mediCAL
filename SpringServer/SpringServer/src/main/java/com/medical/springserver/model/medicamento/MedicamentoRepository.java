package com.medical.springserver.model.medicamento;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicamentoRepository extends CrudRepository<Medicamento, Integer>{
	
	@Query("SELECT m FROM Medicamento m where m.fechaFinVigenciaMed IS NULL OR m.fechaFinVigenciaMed >= :fechaActual")
	List<Medicamento> getAllMedicamentos(LocalDate fechaActual);
	
    @Query("SELECT m FROM Medicamento m WHERE m.esParticular = false AND (m.fechaFinVigenciaMed IS NULL OR m.fechaFinVigenciaMed >= :fechaActual)")
    List<Medicamento> obtenerMedicamentosGenericos(LocalDate fechaActual);
    
    @Query("SELECT m FROM Medicamento m WHERE m.esParticular = false")
    List<Medicamento> getAllMedicamentosGenericos();
    
    Medicamento findByCodMedicamento(int codMedicamento);
}
