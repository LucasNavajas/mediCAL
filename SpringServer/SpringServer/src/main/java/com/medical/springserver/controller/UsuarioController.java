package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuthException;
import com.medical.springserver.model.codigoverificacion.CodigoVerificacion;
import com.medical.springserver.model.codigoverificacion.CodigoVerificacionDao;
import com.medical.springserver.model.usuario.Usuario;
import com.medical.springserver.model.usuario.UsuarioDao;
import com.medical.springserver.model.usuario.UsuarioRepository;

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
	UsuarioRepository repository;
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
		return usuarioDao.saveConHash(usuario);
	}
	
	@PostMapping("/usuario/saveSinHash")
	public Usuario saveSinHash(@RequestBody Usuario usuario) throws FirebaseAuthException {
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
    public ResponseEntity<Usuario> setCodVerificacion(@PathVariable int codUsuario) throws FirebaseAuthException {
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
            Usuario modifiedUsuario = repository.save(usuario);

            return new ResponseEntity<>(modifiedUsuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@GetMapping("/usuario/buscar-mail-y-usuario")
    public Usuario buscarUsuariosPorMailYUser(@RequestParam(name = "usuarioTexto") String usuarioTexto) {

        if (usuarioTexto != null) {
            Usuario usuarioPorUsuarioUnico = usuarioDao.obtenerUsuariosPorUsuarioUnico(usuarioTexto);
            if (usuarioPorUsuarioUnico != null) {
                return usuarioPorUsuarioUnico;
            }
            Usuario usuarioPorMail = usuarioDao.getByMailUsuario(usuarioTexto);
            if (usuarioPorMail != null) {
               return usuarioPorMail;
        }
 
    }
    return null;
}
	@PostMapping("/usuario/eliminar/{codUsuario}")
	public void eliminarUsuario(@PathVariable int codUsuario, @RequestBody String motivoFinVigencia) throws FirebaseAuthException {
        usuarioDao.eliminarUsuario(codUsuario, motivoFinVigencia);
	}
	
	@PostMapping("/usuario/recuperar/{codUsuario}")
	public Usuario recuperarUsuario(@PathVariable int codUsuario) throws FirebaseAuthException {
        return usuarioDao.habilitarUsuario(codUsuario);
	}
	
	@PutMapping("/usuario/token/{codUsuario}")
	public Usuario modificarToken(@PathVariable int codUsuario, @RequestBody String  token) throws FirebaseAuthException {
		return usuarioDao.modificarToken(codUsuario, token);
	}
	
	@GetMapping("/usuario/get-all/{codPerfil}")
	public List<Usuario> getAllByCodPerfil(@PathVariable int codPerfil){
		return usuarioDao.findUsuariosByCodPerfil(codPerfil);
	}
	
	@PostMapping("/usuario/alta-desde-admin")
	public Usuario altaAdmin(@RequestBody Usuario usuario){
		return usuarioDao.altaAdmin(usuario);
	}
	
	@PutMapping("/usuario/verificar-mail-existente/{mail}")
	public void verificarMailExistente(@PathVariable String mail) throws FirebaseAuthException {
		usuarioDao.verificarMailExistente(mail);
	}
	
	@GetMapping("/usuario/institucion/{nombreInstitucion}")
	public List<Usuario> findByInstitucion(@PathVariable String nombreInstitucion) {
	    return usuarioDao.findByInstitucion(nombreInstitucion);
	}

}
