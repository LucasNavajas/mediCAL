package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.usuario.Usuario;
import com.medical.springserver.model.usuario.UsuarioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
public class UsuarioController {

	@Autowired
	UsuarioDao usuarioDao;
	
	@GetMapping("/usuario/get-all")
	public List<Usuario> getAllUsuarios(){
		return usuarioDao.getAllUsuarios();
	}
	
	@PostMapping("/usuario/save")
	public Usuario save(@RequestBody Usuario usuario){
		return usuarioDao.save(usuario);
	}
}
