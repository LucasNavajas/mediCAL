package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.medical.springserver.model.codigoverificacion.CodigoVerificacion;
import com.medical.springserver.model.codigoverificacion.CodigoVerificacionDao;
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
	@Autowired
	CodigoVerificacionDao codVerificacionDao;
	
	@GetMapping("/usuario/get-all")
	public List<Usuario> getAllUsuarios(){
		return usuarioDao.getAllUsuarios();
	}
	
	@GetMapping("/usuario/get-all-usuarios-unicos")
	public List<String> obtenerUsuariosUnicos(){
		return usuarioDao.obtenerUsuariosUnicos();
	}
	
	@GetMapping("/usuario/get-all-mails-unicos")
	public List<String> obtenerMailsUnicos(){
		return usuarioDao.obtenerMailsUnicos();
	}
	
	@GetMapping("/usuario/get-all-mails-unicos-cuentas")
	public List<String> obtenerMailsUnicosCuentas(){
		return usuarioDao.obtenerMailsCuentas();
	}
	
	@PostMapping("/usuario/save")
	public Usuario save(@RequestBody Usuario usuario){
		return usuarioDao.save(usuario);
	}
	
	@GetMapping("/usuario/cod/{codUsuario}")
	public ResponseEntity<Usuario> getByCodUsuario(@PathVariable int codUsuario){
		Optional<Usuario> usuarioOptional = usuarioDao.getByCodUsuario(codUsuario);
		
		if (usuarioOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/usuario/mail/{mailUsuario}")
	public ResponseEntity<Usuario> getByMailUsuario(@PathVariable String mailUsuario){
		Usuario usuario = usuarioDao.getByMailUsuario(mailUsuario);
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}
	@PostMapping("/usuario/misma-contrasenia/{codUsuario}/{nuevaContrasenia}")
	public boolean verificarMismaContrasenia(@PathVariable int codUsuario, @PathVariable String nuevaContrasenia){
		return usuarioDao.verificarMismaContrasenia(codUsuario, nuevaContrasenia);
	}
	
	@PostMapping("/usuario/modificar")
	public ResponseEntity<Usuario> modificarUsuario(@RequestBody Usuario usuario) throws FirebaseAuthException {
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
	
	@PostMapping("/usuario/modificarContrasenia/{codUsuario}")
    public ResponseEntity<String> modificarContrasenia(@PathVariable int codUsuario, @RequestBody String nuevaContrasenia) {
        try {
            Usuario usuario = usuarioDao.modificarContrasenia(codUsuario, nuevaContrasenia.trim());
            return new ResponseEntity<>("Contraseña modificada correctamente.", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("El usuario no existe.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al modificar la contraseña.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@DeleteMapping("/usuario/delete/{codUsuario}")
	public ResponseEntity<String> deleteUsuario(@PathVariable int codUsuario) {
	    // Verifica si el usuario existe antes de eliminarlo
	    Optional<Usuario> usuarioOptional = usuarioDao.getByCodUsuario(codUsuario);
	    if (usuarioOptional.isPresent()) {
	    	Usuario usuario = usuarioOptional.get();
	        usuarioDao.delete(usuario);
	        return new ResponseEntity<>("Usuario eliminado correctamente.", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("El usuario no existe.", HttpStatus.NOT_FOUND);
	    }
	}
	
	@PostMapping("/usuario/set-cod-verificacion/{codUsuario}")
    public ResponseEntity<Usuario> setCodVerificacion(@PathVariable int codUsuario) {
        Optional<Usuario> usuarioOptional = usuarioDao.getByCodUsuario(codUsuario);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            // Verifica si ya existe un código de verificación
            if (usuario.getCodigoVerificacion() != null) {
                // Si ya tiene un código de verificación, elimínalo
            	CodigoVerificacion auxiliar = usuario.getCodigoVerificacion();
            	
                usuario.setCodigoVerificacion(null);
            	codVerificacionDao.delete(auxiliar);
            }
            CodigoVerificacion nuevoCodigoVerificacion = new CodigoVerificacion();

            // Establece el nuevo código de verificación en el usuario
            usuario.setCodigoVerificacion(nuevoCodigoVerificacion);

            // Guarda los cambios en la base de datos
            Usuario modifiedUsuario = usuarioDao.saveSinHash(usuario);

            return new ResponseEntity<>(modifiedUsuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
}
