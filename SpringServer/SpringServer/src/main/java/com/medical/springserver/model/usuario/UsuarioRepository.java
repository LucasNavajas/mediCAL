package com.medical.springserver.model.usuario;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
	Optional<Usuario> findByCodUsuario(int codUsuario);
	List<Usuario> findByUsuarioUnico(String usuarioUnico);
	List<Usuario> findByMailUsuario(String mailUsuario);
	
	@Query("SELECT u.usuarioUnico FROM Usuario u")
    List<String> findAllDistinctUsuarioUnico();
	
	@Query("SELECT u.mailUsuario FROM Usuario u")
    List<String> findAllDistinctMailUsuario();
	
	@Query("SELECT u.mailUsuario FROM Usuario u WHERE u.fechaAltaUsuario IS NOT NULL")
    List<String> findAllDistinctMailCuentas();
	
	@Query("SELECT u FROM Usuario u WHERE u.codigoVerificacion.codVerificacion = :codVerificacion")
    Usuario findByCodigoVerificacion(String codVerificacion);
	
	@Query("SELECT u FROM Usuario u WHERE u.perfil.codPerfil = :codPerfil")
    List<Usuario> findByCodPerfil(@Param("codPerfil") int codPerfil);
	
	@Query("SELECT u FROM Usuario u WHERE u.perfil.codPerfil = 4 AND u.nombreInstitucion = :nombreInstitucion")
	List<Usuario> findPorInstitucion(@Param("nombreInstitucion") String nombreInstitucion);

}
