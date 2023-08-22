package com.medical.springserver.model.historialfinvigencia;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface HistorialFinVigenciaRepository extends CrudRepository<HistorialFinVigencia, Integer>{
	
	@Query("SELECT h FROM HistorialFinVigencia h WHERE h.usuario.codUsuario = :codUsuario ORDER BY h.nroHistorialFV ASC")
    List<HistorialFinVigencia> findHistorialByUsuarioCod(@Param("codUsuario") int codUsuario);
}
