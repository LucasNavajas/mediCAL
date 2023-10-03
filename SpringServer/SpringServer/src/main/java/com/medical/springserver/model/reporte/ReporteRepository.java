package com.medical.springserver.model.reporte;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ReporteRepository extends CrudRepository<Reporte, Integer>{
	
	// Método para buscar todas las instancias de Reporte con un codUsuario específico
    @Query("SELECT r FROM Reporte r WHERE r.usuario.codUsuario = :codUsuario")		// AND r.fechaFinVigenciaRe = null ? Agregar fechaFinVigencia a Reporte?
    List<Reporte> findByCodUsuario(@Param("codUsuario") Integer codUsuario);
	
	
}
