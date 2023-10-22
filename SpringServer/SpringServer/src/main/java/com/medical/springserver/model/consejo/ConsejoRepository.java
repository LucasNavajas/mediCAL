package com.medical.springserver.model.consejo;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medical.springserver.model.reporte.Reporte;


@Repository
public interface ConsejoRepository extends CrudRepository<Consejo, Integer>{
	@Query("SELECT c FROM Consejo c where c.fechaFinVigenciaConsejo IS NULL OR c.fechaFinVigenciaConsejo >= :fechaActual")
	List<Consejo> getAllConsejos(LocalDate fechaActual);
	
	// Método para buscar la instancia de Consejo asociada con un nroConsejo específico
	@Query("SELECT c FROM Consejo c WHERE c.nroConsejo = :nroConsejo")
	Consejo findByNroConsejo(@Param("nroConsejo") int nroConsejo);
	
	@Query("SELECT c FROM Consejo c")
	List<Consejo> getAllConsejosYBajas();
	
	//Consejo findByNroConsejo(int nroConsejo);
}
