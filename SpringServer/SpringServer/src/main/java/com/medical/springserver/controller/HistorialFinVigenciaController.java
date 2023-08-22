package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.historialfinvigencia.HistorialFinVigencia;
import com.medical.springserver.model.historialfinvigencia.HistorialFinVigenciaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
public class HistorialFinVigenciaController {

	@Autowired
	HistorialFinVigenciaDao historialfinvigenciaDao;
	
	@GetMapping("/historialfinvigencia/get-all")
	public List<HistorialFinVigencia> getAllHistorialFinVigencias(){
		return historialfinvigenciaDao.getAllHistorialFinVigencias();
	}
	
	@PostMapping("/historialfinvigencia/save")
	public HistorialFinVigencia save(@RequestBody HistorialFinVigencia historialfinvigencia){
		return historialfinvigenciaDao.save(historialfinvigencia);
	}
	
	@GetMapping("/historialfinvigencia/{codUsuario}")
	public List<HistorialFinVigencia> findByCodUsuario(@PathVariable int codUsuario){
		return historialfinvigenciaDao.findByCodUsuario(codUsuario);
	}
}
