package com.medical.springserver.model.calendariosintoma;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.calendariomedicion.CalendarioMedicion;

@Repository
public interface CalendarioSintomaRepository extends CrudRepository<CalendarioSintoma, Integer>{
	
	// Método para buscar todas las instancias de Calendario con un codcalendario específico
	@Query("SELECT cs FROM CalendarioSintoma cs WHERE cs.calendario.codCalendario = :codCalendario AND cs.fechaFinVigenciaCS IS NULL")
	List<CalendarioSintoma> findByCodCalendario(@Param("codCalendario") int codCalendario);

    CalendarioSintoma findByCodCalendarioSintoma(Integer codCalendarioSintoma);
    
	@Query("SELECT cs FROM CalendarioSintoma cs WHERE cs.sintoma.codSintoma = :codSintoma AND cs.fechaFinVigenciaCS IS NULL")
	List<CalendarioSintoma> findByCodSintoma(@Param("codSintoma") Integer codSintoma);
	
    @Query("SELECT cs FROM CalendarioSintoma cs " +
            "WHERE cs.calendario.codCalendario = :codCalendario " +
            "AND cs.fechaCalendarioSintoma BETWEEN :fechaDesde AND :fechaHasta " +
            "AND cs.fechaFinVigenciaCS IS NULL")
     List<CalendarioSintoma> findSintomasByCalendarioAndDateRange(
         @Param("codCalendario") Integer codCalendario,
         @Param("fechaDesde") LocalDateTime fechaDesde,
         @Param("fechaHasta") LocalDateTime fechaHasta
     );


}