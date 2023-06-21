package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.usuario.TipoConsejo;
import com.medical.springserver.model.usuario.TipoConsejoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
public class TipoConsejoController {

	@Autowired
	TipoConsejoDao tipoConsejoDao;
	
	@GetMapping("/tipoConsejo/get-all")
	public List<TipoConsejo> getAllTipoConsejo(){
		return tipoConsejoDao.getAllTipoConsejo();
	}
	
	@PostMapping("/tipoConsejo/save")
	public TipoConsejo save(@RequestBody TipoConsejo tipoConsejo){
		return tipoConsejoDao.save(tipoConsejo);
	}
}
