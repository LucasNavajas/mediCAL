package com.medical.springserver.model.tiporeporte;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TipoReporteRepository extends CrudRepository<TipoReporte, Integer>{
	
}