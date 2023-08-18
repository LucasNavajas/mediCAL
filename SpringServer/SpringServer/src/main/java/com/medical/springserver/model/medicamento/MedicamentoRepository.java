package com.medical.springserver.model.medicamento;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicamentoRepository extends CrudRepository<Medicamento, Integer>{
    @Query("SELECT m FROM Medicamento m WHERE m.esParticular = false")
    List<Medicamento> obtenerMedicamentosGenericos();
}
