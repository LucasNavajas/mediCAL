package com.medical.springserver.model.reporte;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medical.springserver.model.tiporeporte.TipoReporte;


@Repository
public interface ReporteRepository extends CrudRepository<Reporte, Integer>{
	
	// Método para buscar todas las instancias de Reporte con un codUsuario específico
    @Query("SELECT r FROM Reporte r WHERE r.usuario.codUsuario = :codUsuario")		// AND r.fechaFinVigenciaRe = null ? Agregar fechaFinVigencia a Reporte?
    List<Reporte> findByCodUsuario(@Param("codUsuario") int codUsuario);
    
    // Método para buscar la instancia de Reporte asociada con un nroReporte específico
	@Query("SELECT r FROM Reporte r WHERE r.nroReporte = :nroReporte")
	Reporte findByNroReporte(@Param("nroReporte") int nroReporte);
	
	@Query("DELETE FROM Reporte r WHERE r.nroReporte = :nroReporte")
	void deleteByNroReporte(@Param("nroReporte") int nroReporte);

	
}
