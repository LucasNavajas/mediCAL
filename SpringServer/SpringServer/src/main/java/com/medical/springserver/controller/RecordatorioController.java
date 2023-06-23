package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.recordatorio.Recordatorio;
import com.medical.springserver.model.recordatorio.RecordatorioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class RecordatorioController {
	
	@Autowired
	RecordatorioDao recordatorioDao;
	
	@GetMapping("/recordatorio/get-all")
	public List<Recordatorio> getAllRecordatorios(){
		return recordatorioDao.getAllRecordatorios();
	}
	
	@PostMapping("/recordatorio/save")
	public Recordatorio save(@RequestBody Recordatorio recordatorio) {
		return recordatorioDao.save(recordatorio);
	}

}