package com.medical.springserver.model.medicion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medical.springserver.model.calendario.Calendario;

@Repository
public interface MedicionRepository extends CrudRepository<Medicion, Integer>{
	
	   Medicion findByCodMedicion(Integer codMedicion);

}