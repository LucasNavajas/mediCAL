package com.medical.springserver.model.calendariosintoma;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medical.springserver.model.calendario.Calendario;

@Repository
public interface CalendarioSintomaRepository extends CrudRepository<CalendarioSintoma, Integer>{
	
	// Método para buscar todas las instancias de Calendario con un codcalendario específico
	@Query("SELECT cs FROM CalendarioSintoma cs WHERE cs.calendario.codCalendario = :codCalendario AND cs.fechaFinVigenciaCS IS NULL")
	List<CalendarioSintoma> findByCodCalendario(@Param("codCalendario") Integer codCalendario);


}