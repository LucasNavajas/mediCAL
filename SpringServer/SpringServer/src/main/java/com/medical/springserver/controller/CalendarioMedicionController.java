package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.calendariomedicion.CalendarioMedicion;
import com.medical.springserver.model.calendariomedicion.CalendarioMedicionDao;

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
	
	@PostMapping("/calendariomedicion/save")
	public CalendarioMedicion save(@RequestBody CalendarioMedicion calendariomedicion) {
		return calendariomedicionDao.save(calendariomedicion);
	}

}