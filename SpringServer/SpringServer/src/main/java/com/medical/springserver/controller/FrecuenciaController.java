package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.frecuencia.Frecuencia;
import com.medical.springserver.model.frecuencia.FrecuenciaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class FrecuenciaController {

	@Autowired
	FrecuenciaDao frecuenciaDao;
	
	@GetMapping("/frecuencia/get-all")
	public List<Frecuencia> getAllFrecuencias(){
		return frecuenciaDao.getAllFrecuencias();
	}
	
	@PostMapping("/frecuencia/save")
	public Frecuencia save(@RequestBody Frecuencia frecuencia) {
		return frecuenciaDao.save(frecuencia);
	}
}
