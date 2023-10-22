package com.medical.springserver.controller;

import com.medical.springserver.model.consejo.Consejo;
import com.medical.springserver.model.consejo.ConsejoDao;
import com.medical.springserver.model.reporte.Reporte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class ConsejoController {

	@Autowired
	ConsejoDao consejoDao;
	
	@GetMapping("/consejo/get-all")
	public List<Consejo> getAllConsejo(){
		return consejoDao.getAllConsejo();
	}
	
	@GetMapping("/consejo/{nroConsejo}")
	public Consejo getByNroConsejo(@PathVariable int nroConsejo) {
	    return consejoDao.findByNroConsejo(nroConsejo);
	}
	
	@PostMapping("/consejo/save")
	public Consejo save(@RequestBody Consejo consejo){
		return consejoDao.save(consejo);
	}
	
	@GetMapping("/consejo/get-all-con-bajas")
	public List<Consejo> getAllConsejos(){
		return consejoDao.getAllConsejos();
	}
	
	@PutMapping("/consejo/baja/{codConsejo}")
	public Consejo bajaConsejo(@PathVariable int codConsejo) {
		return consejoDao.bajaConsejo(codConsejo);
	}
	
	@PutMapping("/consejo/recuperar/{codConsejo}")
	public Consejo recuperarConsejo(@PathVariable int codConsejo) {
		return consejoDao.recuperarConsejo(codConsejo);
	}
}
