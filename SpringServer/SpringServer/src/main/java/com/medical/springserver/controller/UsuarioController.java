package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;
import com.medical.springserver.model.usuario.Usuario;
import com.medical.springserver.model.usuario.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("/usuario/get-all-usuarios-unicos")
	public List<String> obtenerUsuariosUnicos(){
		return usuarioDao.obtenerUsuariosUnicos();
	}
	
	@PostMapping("/usuario/save")
	public Usuario save(@RequestBody Usuario usuario){
		return usuarioDao.save(usuario);
	}
	
	@GetMapping("/usuario/get-by-cod/{codUsuario}")
	public ResponseEntity<Usuario> getByCodUsuario(@PathVariable int codUsuario){
		Optional<Usuario> usuarioOptional = usuarioDao.getByCodUsuario(codUsuario);
		
		if (usuarioOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
