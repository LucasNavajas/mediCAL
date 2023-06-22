package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorio;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class RegistroRecordatorioController {
	
	@Autowired
	RegistroRecordatorioDao registroRecordatorioDao;
	
	@GetMapping("/registroRecordatorio/get-all")
	public List<RegistroRecordatorio> getAllRegistrosRecordatorio(){
		return registroRecordatorioDao.getAllRegistrosRecordatorio();
	}
	
	@PostMapping("registroRecordatorio/save")
	public RegistroRecordatorio save(@RequestBody RegistroRecordatorio registroRecordatorio) {
		return registroRecordatorioDao.save(registroRecordatorio);
	}
}
