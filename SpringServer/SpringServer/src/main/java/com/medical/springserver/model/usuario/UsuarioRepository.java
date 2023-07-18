package com.medical.springserver.model.usuario;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
	Optional<Usuario> findByCodUsuario(int codUsuario);
}
