package com.medical.springserver.model.estadosolicitud;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EstadoSolicitudRepository extends CrudRepository<EstadoSolicitud, Integer>{
	EstadoSolicitud findByCodEstadoSolicitud(int codEstadoSolicitud);
	
}
