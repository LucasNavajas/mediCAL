package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.codigoverificacion.CodigoVerificacion;
import com.medical.springserver.model.codigoverificacion.CodigoVerificacionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
public class CodigoVerificacionController {

	@Autowired
	CodigoVerificacionDao codigoverificacionDao;
	
	@GetMapping("/codigoverificacion/get-all")
	public List<CodigoVerificacion> getAllCodigosVerificacion(){
		return codigoverificacionDao.getAllCodigosVerificacion();
	}
	
	@PostMapping("/codigoverificacion/save")
	public CodigoVerificacion save(@RequestBody CodigoVerificacion codigoverificacion){
		return codigoverificacionDao.save(codigoverificacion);
	}
}
