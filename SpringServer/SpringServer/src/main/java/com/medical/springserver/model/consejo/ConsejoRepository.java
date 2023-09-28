package com.medical.springserver.model.consejo;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConsejoRepository extends CrudRepository<Consejo, Integer>{
	@Query("SELECT c FROM Consejo c where c.fechaFinVigenciaConsejo IS NULL OR c.fechaFinVigenciaConsejo >= :fechaActual")
	List<Consejo> getAllConsejos(LocalDate fechaActual);
	
	@Query("SELECT c FROM Consejo c")
	List<Consejo> getAllConsejosYBajas();
	
	Consejo findByNroConsejo(int nroConsejo);
}
