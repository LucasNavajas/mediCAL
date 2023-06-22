package com.medical.springserver.model.medicamento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicamentoRepository extends CrudRepository<Medicamento, Integer>{

}
