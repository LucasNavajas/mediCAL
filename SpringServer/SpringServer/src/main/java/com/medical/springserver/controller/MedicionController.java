package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.medicion.Medicion;
import com.medical.springserver.model.medicion.MedicionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class MedicionController {
	
	@Autowired
	MedicionDao medicionDao;
	
	@GetMapping("/medicion/get-all")
	public List<Medicion> getAllMediciones(){
		return medicionDao.getAllMediciones();
	}
	
	@PostMapping("/medicion/save")
	public Medicion save(@RequestBody Medicion medicion) {
		return medicionDao.save(medicion);
	}

}
