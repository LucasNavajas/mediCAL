package com.medical.springserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.dosis.Dosis;
import com.medical.springserver.model.dosis.DosisDao;

@RestController
public class DosisController {

	@Autowired
	DosisDao dosisDao;
	
	@GetMapping("/dosis/get-all")
	public List<Dosis> getAllDosis(){
		return dosisDao.getAllDosis();
	}
	
	@PostMapping("/dosis/save")
	public Dosis save(@RequestBody Dosis dosis) {
		return dosisDao.save(dosis);
	}
}
