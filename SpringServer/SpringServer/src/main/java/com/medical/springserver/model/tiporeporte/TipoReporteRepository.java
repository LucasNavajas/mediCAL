package com.medical.springserver.model.tiporeporte;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TipoReporteRepository extends CrudRepository<TipoReporte, Integer>{
	
	// Método para buscar la instancia de TipoReporte asociada con un nroReporte específico
		@Query("SELECT t FROM TipoReporte t WHERE EXISTS (SELECT r FROM Reporte r WHERE r.nroReporte = :nroReporte AND r.tipoReporte = t)")
		TipoReporte findByNroReporte(@Param("nroReporte") Integer nroReporte);
	
}
