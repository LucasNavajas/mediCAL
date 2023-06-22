package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.presentacionMed.PresentacionMed;
import com.medical.springserver.model.presentacionMed.PresentacionMedDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class PresentacionMedController {
	
	@Autowired
	PresentacionMedDao presentacionMedDao;
	
	@GetMapping("/presentacionMed/get-all")
	public List<PresentacionMed> getAllPresentacionesMed(){
		return presentacionMedDao.getAllPresentacionesMed();
	}
	
	@PostMapping("/presentacionMed/save")
	public PresentacionMed save(@RequestBody PresentacionMed presentacionMed) {
		return presentacionMedDao.save(presentacionMed);
	}

}
