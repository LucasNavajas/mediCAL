package com.medical.springserver.controller;

import com.medical.springserver.model.inventario.Inventario;
import com.medical.springserver.model.reporte.Reporte;
import com.medical.springserver.model.reporte.ReporteDao;

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
	
	@GetMapping("/reporte/usuario/{codUsuario}")
	public List<Reporte> getByCodUsuario(@PathVariable int codUsuario) {
	    return reporteDao.findByCodUsuario(codUsuario);
	}
	
}
