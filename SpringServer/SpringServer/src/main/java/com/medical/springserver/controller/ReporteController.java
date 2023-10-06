package com.medical.springserver.controller;

import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;
import com.medical.springserver.model.inventario.Inventario;
import com.medical.springserver.model.reporte.Reporte;
import com.medical.springserver.model.reporte.ReporteDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
	
	@GetMapping("/reporte/{nroReporte}")
	public Reporte getByNroReporte(@PathVariable int nroReporte) {
	    return reporteDao.findByNroReporte(nroReporte);
	}
	
	@GetMapping("/reporte/usuario/{codUsuario}")
	public List<Reporte> getByCodUsuario(@PathVariable int codUsuario) {
	    return reporteDao.findByCodUsuario(codUsuario);
	}
	
	@PostMapping("/reporte/eliminar/{nroReporte}") 
	public ResponseEntity<Reporte> deleteByNroReporte(@PathVariable int nroReporte) {
	    // Buscar el reporte por su número de reporte
	    Reporte reporte = reporteDao.findByNroReporte(nroReporte);
	    if (reporte == null) {
	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    reporteDao.delete(reporte);	    
	    return new ResponseEntity<>(reporte, HttpStatus.OK);
	}
	
}
