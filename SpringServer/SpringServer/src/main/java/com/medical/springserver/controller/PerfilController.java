package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.perfil.Perfil;
import com.medical.springserver.model.perfil.PerfilDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class PerfilController {

	@Autowired
	PerfilDao perfilDao;
	
	@GetMapping("/perfil/get-all")
	public List<Perfil> getAllPerfiles(){
		return perfilDao.getAllPerfiles();
	}
	
	@PostMapping("/perfil/save")
	public Perfil save(@RequestBody Perfil perfil){
		return perfilDao.save(perfil);
	}
	
	@GetMapping("/perfil/{codPerfil}")
	public Perfil findByCodPerfil(@PathVariable int codPerfil) {
		return perfilDao.findByCodPerfil(codPerfil);
	}
}