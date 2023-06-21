package com.medical.springserver.controller;

import com.medical.springserver.model.usuario.Consejo;
import com.medical.springserver.model.usuario.ConsejoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class ConsejoController {

	@Autowired
	ConsejoDao consejoDao;
	
	@GetMapping("/consejo/get-all")
	public List<Consejo> getAllConsejo(){
		return consejoDao.getAllConsejo();
	}
	
	@PostMapping("/consejo/save")
	public Consejo save(@RequestBody Consejo consejo){
		return consejoDao.save(consejo);
	}
}
