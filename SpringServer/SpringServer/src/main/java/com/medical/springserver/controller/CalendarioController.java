package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.calendario.CalendarioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class CalendarioController {
	
	@Autowired
	CalendarioDao calendarioDao;
	
	@GetMapping("/calendario/get-all")
	public List<Calendario> getAllCalendarios(){
		return calendarioDao.getAllCalendarios();
	}
	
	@PostMapping("/calendario/save")
	public Calendario save(@RequestBody Calendario calendario) {
		return calendarioDao.save(calendario);
	}
	
	@GetMapping("/calendario/usuario/{codUsuario}")
	public List<Calendario> getByCodUsuario(@PathVariable int codUsuario){
		return calendarioDao.findByCodUsuario(codUsuario);
	}
	
	@GetMapping("/calendario/{codCalendario}")
	public Calendario getByCodCalendario(@PathVariable int codCalendario) {
		return calendarioDao.findByCodCalendario(codCalendario);
	}
	
	@PostMapping("/calendario/modificar/{codCalendario}")
	public Calendario modificarCalendario(@PathVariable int codCalendario, @RequestBody Calendario calendario) {
		return calendarioDao.modificarCalendario(codCalendario, calendario.getNombreCalendario(), calendario.getRelacionCalendario(), calendario.getNombrePaciente());
		//codCalendario, calendario.getFechaAltaCalendario(), calendario.getFechaFinVigenciaC(),
		//calendario.getNombreCalendario(), calendario.getRelacionCalendario(), calendario.getNombrePaciente());
	}
	
	@PostMapping("/calendario/eliminar/{codCalendario}")
	public void eliminarCalendario(@PathVariable int codCalendario) {
		calendarioDao.eliminarCalendario(codCalendario);
	}
	
	@PostMapping("/calendario/recuperar/{codCalendario}")
	public void recuperarCalendario(@PathVariable int codCalendario) {
		calendarioDao.recuperarCalendario(codCalendario);
	}
	
	@GetMapping("/calendario/get-all/{nombreInstitucion}")
	public List<Calendario> findByInstitucion(@PathVariable String nombreInstitucion){
		return calendarioDao.findByInstitucion(nombreInstitucion);
	}
		
	

}
