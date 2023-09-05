package com.medical.springserver.model.calendariomedicion;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;

@Repository
public interface CalendarioMedicionRepository extends CrudRepository<CalendarioMedicion, Integer>{

	
	@Query("SELECT cm FROM CalendarioMedicion cm WHERE cm.calendario.codCalendario = :codCalendario AND cm.fechaFinVigenciaCM IS NULL")
	List<CalendarioMedicion> findByCodCalendario(@Param("codCalendario") Integer codCalendario);

}
