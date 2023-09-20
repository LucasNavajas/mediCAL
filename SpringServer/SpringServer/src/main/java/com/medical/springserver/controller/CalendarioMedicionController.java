package com.medical.springserver.controller;
import com.medical.springserver.model.calendario.Calendario;
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
	
	@GetMapping("/calendariomedicion/{codCalendarioMedicion}")
	public CalendarioMedicion getByCodCalendarioM(@PathVariable int codCalendarioMedicion) {
		return calendariomedicionDao.findByCodCalendarioMedicion(codCalendarioMedicion);
	}
	
	@GetMapping("/calendariomedicion/calendario/{codCalendario}")
	public List<CalendarioMedicion> getByCodCalendarioMedicion(@PathVariable int codCalendario){
		return calendariomedicionDao.findByCodCalendario(codCalendario);
	}
	
	@GetMapping("/calendariomedicion/medicion/{codMedicion}")
	public List<CalendarioMedicion> getByCodMedicion(@PathVariable int codMedicion){
		return calendariomedicionDao.findByCodMedicion(codMedicion);
	}

	
	@PostMapping("/calendariomedicion/save")
	public CalendarioMedicion save(@RequestBody CalendarioMedicion calendariomedicion) {
		return calendariomedicionDao.save(calendariomedicion);
	}

}