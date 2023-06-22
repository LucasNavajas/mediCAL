package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.omision.Omision;
import com.medical.springserver.model.omision.OmisionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class OmisionController {
	
	@Autowired
	OmisionDao omisionDao;
	
	@GetMapping ("/omision/get-all")
	public List<Omision> getAllOmisiones(){
		return omisionDao.getAllOmisiones();
	}
	
	@PostMapping("/omision/save")
	public Omision save(@RequestBody Omision omision) {
		return omisionDao.save(omision);
	}

}
