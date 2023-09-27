package com.medical.springserver.controller;
import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.calendariomedicion.CalendarioMedicion;
import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;
import com.medical.springserver.model.calendariosintoma.CalendarioSintomaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class CalendarioSintomaController {
	
	@Autowired
	CalendarioSintomaDao calendariosintomaDao;
	
	@GetMapping("/calendariosintoma/get-all")
	public List<CalendarioSintoma> getAllCalendarioSintomas(){
		return calendariosintomaDao.getAllCalendarioSintomas();
	}
	
	@GetMapping("/calendariosintoma/{codCalendarioSintoma}")
	public CalendarioSintoma getByCodCalendarioS(@PathVariable int codCalendarioSintoma) {
		return calendariosintomaDao.findByCodCalendarioSintoma(codCalendarioSintoma);
	}
	
	@GetMapping("/calendariosintoma/sintoma/{codSintoma}")
	public List<CalendarioSintoma> getByCodSintoma(@PathVariable int codSintoma){
		return calendariosintomaDao.findByCodSintoma(codSintoma);
	}
	
	
	@GetMapping("/calendariosintoma/calendario/{codCalendario}")
	public List<CalendarioSintoma> getByCodCalendarioSintoma(@PathVariable int codCalendario){
		return calendariosintomaDao.findByCodCalendario(codCalendario);
	}

	
	@PostMapping("/calendariosintoma/save")
	public CalendarioSintoma save(@RequestBody CalendarioSintoma calendariosintoma) {
		return calendariosintomaDao.save(calendariosintoma);
	}

}
