package com.medical.springserver.model.calendariosintoma;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medical.springserver.model.calendario.Calendario;

public interface CalendarioSintomaRepository extends CrudRepository<CalendarioSintoma, Integer>{
	
    CalendarioSintoma findByCalendario(Calendario calendario);

}