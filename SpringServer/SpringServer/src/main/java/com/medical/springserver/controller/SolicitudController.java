package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.solicitud.Solicitud;
import com.medical.springserver.model.solicitud.SolicitudDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
public class SolicitudController {

	@Autowired
	SolicitudDao solicitudDao;
	
	@GetMapping("/solicitud/get-all")
	public List<Solicitud> getAllSolicitudes(){
		return solicitudDao.getAllSolicitudes();
	}
	
	@PostMapping("/solicitud/save")
	public Solicitud save(@RequestBody Solicitud solicitud){
		return solicitudDao.save(solicitud);
	}
}
