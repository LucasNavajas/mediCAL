package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.calendario.CalendarioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class CalendarioController {
	
	@Autowired
	CalendarioDao calendarioDao;
	
	@GetMapping("/calendario/get-all")
	public List<Calendario> getAllCalendarios(){
		return calendarioDao.getAllCalendarios();
	}
	
	@PostMapping("/calendario/save")
	public Calendario save(@RequestBody Calendario calendario) {
		return calendarioDao.save(calendario);
	}

}