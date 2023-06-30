package com.medical.springserver.model.usuario;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
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
	
	public List<Usuario> getAllUsuarios() {
	    Streamable<Usuario> streamableUsuarios = Streamable.of(repository.findAll());
	    List<Usuario> usuarios = new ArrayList<>();
	    streamableUsuarios.forEach(usuarios::add);
	    return usuarios;
	}
	
	public void delete(Usuario usuario) {
		repository.delete(usuario);
	}
	
}
