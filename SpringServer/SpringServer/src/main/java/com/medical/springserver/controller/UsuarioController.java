package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;
import com.medical.springserver.model.usuario.Usuario;
import com.medical.springserver.model.usuario.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
	
	@GetMapping("/usuario/{codUsuario}")
	public ResponseEntity<Usuario> getByCodUsuario(@PathVariable int codUsuario){
		Optional<Usuario> usuarioOptional = usuarioDao.getByCodUsuario(codUsuario);
		
		if (usuarioOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/usuario/modificar")
	public ResponseEntity<Usuario> modificarUsuario(@RequestBody Usuario usuario) {
	    int codUsuario = usuario.getCodUsuario();
	    String nuevoNombre = usuario.getNombreUsuario();
	    String nuevoApellido = usuario.getApellidoUsuario();
	    String nuevaContrasenia = usuario.getContraseniaUsuario();
	    LocalDate nuevaFechaAlta = usuario.getFechaAltaUsuario();
	    LocalDate nuevaFechaNacimiento = usuario.getFechaNacimientoUsuario();
	    String nuevoGenero = usuario.getGeneroUsuario();
	    String nuevoMail = usuario.getMailUsuario();
	    String nuevoNombreInstitucion = usuario.getNombreInstitucion();
	    String nuevoTelefono = usuario.getTelefonoUsuario();
	    String nuevoUsuarioUnico = usuario.getUsuarioUnico();

	    Usuario modifiedUsuario = usuarioDao.modificarUsuario(codUsuario, nuevoNombre, nuevoApellido,
	            nuevaContrasenia, nuevaFechaAlta, nuevaFechaNacimiento, nuevoGenero,
	            nuevoMail, nuevoNombreInstitucion, nuevoTelefono, nuevoUsuarioUnico);

	    if (modifiedUsuario != null) {
	        return new ResponseEntity<>(modifiedUsuario, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

}
