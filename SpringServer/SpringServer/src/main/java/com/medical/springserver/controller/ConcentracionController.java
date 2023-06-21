package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.usuario.Concentracion;
import com.medical.springserver.model.usuario.ConcentracionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
public class ConcentracionController {

	@Autowired
	ConcentracionDao concentracionDao;
	
	@GetMapping("/concentracion/get-all")
	public List<Concentracion> getAllConcentracion(){
		return concentracionDao.getAllConcentracion();
	}
	
	@PostMapping("/concentracion/save")
	public Concentracion save(@RequestBody Concentracion concentracion){
		return concentracionDao.save(concentracion);
	}
}
