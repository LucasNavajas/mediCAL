package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.profesionalsalud.ProfesionalSalud;
import com.medical.springserver.model.profesionalsalud.ProfesionalSaludDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
public class ProfesionalSaludController {

	@Autowired
	ProfesionalSaludDao profesionalsaludDao;
	
	@GetMapping("/profesionalsalud/get-all")
	public List<ProfesionalSalud> getAllProfesionalesSalud(){
		return profesionalsaludDao.getAllProfesionalesSalud();
	}
	
	@PostMapping("/profesionalsalud/save")
	public ProfesionalSalud save(@RequestBody ProfesionalSalud profesionalsalud){
		return profesionalsaludDao.save(profesionalsalud);
	}
}