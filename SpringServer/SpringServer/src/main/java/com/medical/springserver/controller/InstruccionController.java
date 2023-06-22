package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.instruccion.Instruccion;
import com.medical.springserver.model.instruccion.InstruccionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class InstruccionController {

	@Autowired
	InstruccionDao instruccionDao;
	
	@GetMapping("/instruccion/get-all")
	public List<Instruccion> getAllInstrucciones(){
		return instruccionDao.getAllInstrucciones();
	}
	
	@PostMapping("/instruccion/save")
	public Instruccion save(@RequestBody Instruccion instruccion) {
		return instruccionDao.save(instruccion);
	}
}
