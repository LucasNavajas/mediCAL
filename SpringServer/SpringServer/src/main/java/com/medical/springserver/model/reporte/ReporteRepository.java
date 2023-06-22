package com.medical.springserver.model.reporte;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReporteRepository extends CrudRepository<Reporte, Integer>{
	
}
