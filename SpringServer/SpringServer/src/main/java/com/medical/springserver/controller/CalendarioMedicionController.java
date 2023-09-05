package com.medical.springserver.controller;
import com.medical.springserver.model.calendariomedicion.CalendarioMedicion;
import com.medical.springserver.model.calendariomedicion.CalendarioMedicionDao;
import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class CalendarioMedicionController {
	
	@Autowired
	CalendarioMedicionDao calendariomedicionDao;
	
	@GetMapping("/calendariomedicion/get-all")
	public List<CalendarioMedicion> getAllCalendarioMediciones(){
		return calendariomedicionDao.getAllCalendarioMediciones();
	}
	
	@GetMapping("/calendariomedicion/calendario/{codCalendario}")
	public List<CalendarioMedicion> getByCodCalendarioMedicion(@PathVariable int codCalendario){
		return calendariomedicionDao.findByCodCalendario(codCalendario);
	}

	
	@PostMapping("/calendariomedicion/save")
	public CalendarioMedicion save(@RequestBody CalendarioMedicion calendariomedicion) {
		return calendariomedicionDao.save(calendariomedicion);
	}

}