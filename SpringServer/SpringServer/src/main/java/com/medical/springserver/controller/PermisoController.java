package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.permiso.Permiso;
import com.medical.springserver.model.permiso.PermisoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class PermisoController {

	@Autowired
	PermisoDao permisoDao;
	
	@GetMapping("/permiso/get-all")
	public List<Permiso> getAllPermisos(){
		return permisoDao.getAllPermisos();
	}
	
	@PostMapping("/permiso/save")
	public Permiso save(@RequestBody Permiso permiso){
		return permisoDao.save(permiso);
	}
}