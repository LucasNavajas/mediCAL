package com.medical.springserver.model.codigoverificacion;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface CodigoVerificacionRepository extends CrudRepository<CodigoVerificacion, Integer>{
	boolean existsByCodVerificacion(String codVerificacion);
	
    List<CodigoVerificacion> findByFechaGeneradoBefore(LocalDateTime fecha);
    
    void deleteByFechaGeneradoBefore(LocalDateTime fecha);
}
