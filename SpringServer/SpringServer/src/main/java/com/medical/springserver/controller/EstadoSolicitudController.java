package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.estadosolicitud.EstadoSolicitud;
import com.medical.springserver.model.estadosolicitud.EstadoSolicitudDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
public class EstadoSolicitudController {

	@Autowired
	EstadoSolicitudDao estadoSolicitudDao;
	
	@GetMapping("/estadoSolicitud/get-all")
	public List<EstadoSolicitud> getAllEstadoSolicitud(){
		return estadoSolicitudDao.getAllEstadoSolicitud();
	}
	
	@PostMapping("/estadoSolicitud/save")
	public EstadoSolicitud save(@RequestBody EstadoSolicitud estadoSolicitud){
		return estadoSolicitudDao.save(estadoSolicitud);
	}
	
	@GetMapping("/estadoSolicitud/{codEstadoSolicitud}")
	public EstadoSolicitud findByCodEstadoSolicitud(@PathVariable int codEstadoSolicitud) {
		return estadoSolicitudDao.findByCodEstadoSolicitud(codEstadoSolicitud);
	}
}
