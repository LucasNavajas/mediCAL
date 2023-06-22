package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

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
	
	@PostMapping("/calendariosintoma/save")
	public CalendarioSintoma save(@RequestBody CalendarioSintoma calendariosintoma) {
		return calendariosintomaDao.save(calendariosintoma);
	}

}
