package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.usuario.TipoReporte;
import com.medical.springserver.model.usuario.TipoReporteDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
public class TipoReporteController {

	@Autowired
	TipoReporteDao tipoReporteDao;
	
	@GetMapping("/tipoReporte/get-all")
	public List<TipoReporte> getAllTipoReporte(){
		return tipoReporteDao.getAllTipoReporte();
	}
	
	@PostMapping("/tipoReporte/save")
	public TipoReporte save(@RequestBody TipoReporte tipoReporte){
		return tipoReporteDao.save(tipoReporte);
	}
}
