package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.perfilpermiso.PerfilPermiso;
import com.medical.springserver.model.perfilpermiso.PerfilPermisoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class PerfilPermisoController {

	@Autowired
	PerfilPermisoDao perfilpermisoDao;
	
	@GetMapping("/perfilpermiso/get-all")
	public List<PerfilPermiso> getAllPerfilPermisos(){
		return perfilpermisoDao.getAllPerfilPermisos();
	}
	
	@PostMapping("/perfilpermiso/save")
	public PerfilPermiso save(@RequestBody PerfilPermiso perfilpermiso){
		return perfilpermisoDao.save(perfilpermiso);
	}
}