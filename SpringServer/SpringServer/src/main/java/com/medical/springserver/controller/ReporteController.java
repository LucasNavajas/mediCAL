package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.usuario.Reporte;
import com.medical.springserver.model.usuario.ReporteDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
public class ReporteController {

	@Autowired
	ReporteDao reporteDao;
	
	@GetMapping("/reporte/get-all")
	public List<Reporte> getAllReporte(){
		return reporteDao.getAllReporte();
	}
	
	@PostMapping("/reporte/save")
	public Reporte save(@RequestBody Reporte reporte){
		return reporteDao.save(reporte);
	}
}
