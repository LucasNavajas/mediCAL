package com.medical.springserver.model.calendariosintoma;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.medical.springserver.model.calendario.Calendario;

@Service

public class CalendarioSintomaDao {
	@Autowired
	private CalendarioSintomaRepository repository;
	
	public CalendarioSintoma save(CalendarioSintoma calendariosintoma) {
		return repository.save(calendariosintoma);
	}
	
	public CalendarioSintoma findByCalendario(Calendario calendario) {
		return repository.findByCalendario(calendario);
	}
	
	public List<CalendarioSintoma> getAllCalendarioSintomas(){
		Streamable<CalendarioSintoma> streamableCalendarioSintomas = Streamable.of(repository.findAll());
		List<CalendarioSintoma> calendariosintomas = new ArrayList<>();
		streamableCalendarioSintomas.forEach(calendariosintomas::add);
		return calendariosintomas;
	}
	
	public void delete(CalendarioSintoma calendariosintoma) {
		repository.delete(calendariosintoma);
	}

}