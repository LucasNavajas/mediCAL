package com.medical.springserver.model.usuario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.UpdateRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class UsuarioDao {
	@Autowired
	private UsuarioRepository repository;
	private PasswordEncoder passwordEncoder;
	
	public UsuarioDao(PasswordEncoder passwordEncoder) {
		this.passwordEncoder=passwordEncoder;
	}
	
	public Usuario save(Usuario usuario) {
		String hashedPassword = passwordEncoder.encode(usuario.getContraseniaUsuario());
	    usuario.setContraseniaUsuario(hashedPassword);
		return repository.save(usuario);
	}
	
	public Usuario saveSinHash(Usuario usuario) {
		return repository.save(usuario);
	}
	public List<Usuario> getAllUsuarios() {
	    Streamable<Usuario> streamableUsuarios = Streamable.of(repository.findAll());
	    List<Usuario> usuarios = new ArrayList<>();
	    streamableUsuarios.forEach(usuarios::add);
	    return usuarios;
	}
	
	public void delete(Usuario usuario) {
		repository.delete(usuario);
	}
	
	public Usuario modificarUsuario(int codUsuario, String nuevoNombre, String nuevoApellido,
            String nuevaContrasenia, LocalDate nuevaFechaAlta, 
            LocalDate nuevaFechaNacimiento, String nuevoGenero,
            String nuevoMail, String nuevoNombreInstitucion,
            String nuevoTelefono, String nuevoUsuarioUnico) throws FirebaseAuthException {
			// Paso 1 (opcional): Buscar el usuario por su ID
			Optional<Usuario> optionalUsuario = repository.findByCodUsuario(codUsuario);
			Usuario usuario;
			if (optionalUsuario.isPresent()) {
			usuario = optionalUsuario.get();
			} else {
			// Si no se encontró el usuario, puedes crear uno nuevo (opcional)
			usuario = new Usuario();
			}
			if(!usuario.getMailUsuario().equals(nuevoMail)) {
				UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(usuario.getMailUsuario());
		        String uid = userRecord.getUid();
		        UpdateRequest request = new UpdateRequest(uid).setPassword(nuevaContrasenia);
		        UpdateRequest request2 = new UpdateRequest(uid).setEmail(nuevoMail);
		        UserRecord userRecord2 = FirebaseAuth.getInstance().updateUser(request);
		        FirebaseAuth.getInstance().updateUser(request2);
			}
			// Paso 2: Realizar los cambios necesarios
			usuario.setNombreUsuario(nuevoNombre);
			usuario.setApellidoUsuario(nuevoApellido);
			String hashedPassword = passwordEncoder.encode(nuevaContrasenia);
		    usuario.setContraseniaUsuario(hashedPassword);
			usuario.setFechaAltaUsuario(nuevaFechaAlta);
			usuario.setFechaNacimientoUsuario(nuevaFechaNacimiento);
			usuario.setGeneroUsuario(nuevoGenero);
			usuario.setMailUsuario(nuevoMail);
			usuario.setNombreInstitucion(nuevoNombreInstitucion);
			usuario.setTelefonoUsuario(nuevoTelefono);
			usuario.setUsuarioUnico(nuevoUsuarioUnico);
			
			// Paso 3: Guardar los cambios en la base de datos
			return repository.save(usuario);
			}
	
	public Usuario modificarContrasenia(int codUsuario, String nuevaContrasenia) throws FirebaseAuthException {
        Optional<Usuario> optionalUsuario = repository.findByCodUsuario(codUsuario);
        Usuario usuario;
        nuevaContrasenia = nuevaContrasenia.replace("\"", "");
        if (optionalUsuario.isPresent()) {
            usuario = optionalUsuario.get();
            usuario.setCodigoVerificacion(null);
        } else {
            throw new NoSuchElementException("El usuario no existe.");
        }
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(usuario.getMailUsuario());
        String uid = userRecord.getUid();
        UpdateRequest request = new UpdateRequest(uid).setPassword(nuevaContrasenia);
        UserRecord userRecord2 = FirebaseAuth.getInstance().updateUser(request);
        String hashedPassword = passwordEncoder.encode(nuevaContrasenia);
        usuario.setContraseniaUsuario(hashedPassword);
        return repository.save(usuario);
    }
	public Usuario obtenerUsuariosPorUsuarioUnico(String usuarioUnico) {
        return repository.findByUsuarioUnico(usuarioUnico);
    }
	
	public List<String> obtenerUsuariosUnicos() {
        return repository.findAllDistinctUsuarioUnico();
    }
	
	public List<String> obtenerMailsUnicos() {
        return repository.findAllDistinctMailUsuario();
    }

	public Optional<Usuario> getByCodUsuario(int codUsuario) {
		return repository.findByCodUsuario(codUsuario);
	}
	public Usuario getByMailUsuario(String mailUsuario) {
		return repository.findByMailUsuario(mailUsuario);
	}
	
	public List<String> obtenerMailsCuentas(){
		return repository.findAllDistinctMailCuentas();
	}

	public boolean verificarMismaContrasenia(int codUsuario, String nuevaContrasenia) {
		Optional<Usuario> optionalUsuario = repository.findByCodUsuario(codUsuario);
        Usuario usuario;
        nuevaContrasenia = nuevaContrasenia.replace("\"", "");
        if (optionalUsuario.isPresent()) {
            usuario = optionalUsuario.get();
        } else {
            throw new NoSuchElementException("El usuario no existe.");
        }
        System.out.println("03042001".equals(nuevaContrasenia));
        System.out.println(passwordEncoder.matches("03042001", usuario.getContraseniaUsuario()));
        System.out.println(usuario.getContraseniaUsuario());
        System.out.println(passwordEncoder.matches(nuevaContrasenia, usuario.getContraseniaUsuario()));
        return(passwordEncoder.matches(nuevaContrasenia, usuario.getContraseniaUsuario()));
	}
}
