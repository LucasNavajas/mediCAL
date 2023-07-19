package com.medical.springserver.model.usuario;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
	Optional<Usuario> findByCodUsuario(int codUsuario);
	List<Usuario> findByUsuarioUnico(String usuarioUnico);
	
	@Query("SELECT u.usuarioUnico FROM Usuario u")
    List<String> findAllDistinctUsuarioUnico();

}
