package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.administracionmed.AdministracionMed;
import com.medical.springserver.model.administracionmed.AdministracionMedDao;
import com.medical.springserver.model.usuario.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class AdministracionMedController {
	
	@Autowired
	AdministracionMedDao administracionmedDao;
	
	@GetMapping("/administracionmed/get-all")
	public List<AdministracionMed> getAllAdministracionMeds(){
		return administracionmedDao.getAllAdministracionMeds();
	}
	
	@PostMapping("/administracionmed/save")
	public AdministracionMed save(@RequestBody AdministracionMed administracionmed) {
		return administracionmedDao.save(administracionmed);
	}
	
	@GetMapping("/administracionmed/{codAdministracionMed}")
	public AdministracionMed getByCodAdministracionMed(@PathVariable int codAdministracionMed) {
		Optional<AdministracionMed> administracionMedOptional = administracionmedDao.findByCodAdministracionMed(codAdministracionMed);
		if (administracionMedOptional.isPresent()) {
			AdministracionMed administracionMed = administracionMedOptional.get();
			return administracionMed;
		} else {
			return null;
		}
	   
	           
	}

}

