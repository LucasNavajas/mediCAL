package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.inventario.Inventario;
import com.medical.springserver.model.inventario.InventarioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class InventarioController {
	
	@Autowired
	InventarioDao inventarioDao;
	
	@GetMapping("/inventario/get-all")
	public List<Inventario> getAllInventarios(){
		return inventarioDao.getAllInventarios();
	}
	
	@PostMapping("/inventario/save")
	public Inventario save(@RequestBody Inventario inventario) {
		return inventarioDao.save(inventario);
	}

}
