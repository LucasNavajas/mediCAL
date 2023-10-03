package com.medical.springserver.controller;

import com.medical.springserver.model.reporte.Reporte;
import com.medical.springserver.model.tiporeporte.TipoReporte;
import com.medical.springserver.model.tiporeporte.TipoReporteDao;

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
	
	@GetMapping("/tipoReporte/reporte/{nroReporte}")
	public TipoReporte getByNroReporte(@PathVariable int nroReporte) {
	    return tipoReporteDao.findByNroReporte(nroReporte);
	}
	
}
