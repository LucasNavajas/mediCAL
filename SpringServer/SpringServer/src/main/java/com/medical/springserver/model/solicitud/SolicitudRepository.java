package com.medical.springserver.model.solicitud;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SolicitudRepository extends CrudRepository<Solicitud, Integer>{
	
	@Query("SELECT s FROM Solicitud s WHERE s.estadoSolicitud.codEstadoSolicitud = 4 AND s.usuarioControlado.codUsuario = :codUsuarioControlado")
    Solicitud obtenerSolicitudPendienteUsuario(int codUsuarioControlado);
	
	@Query("SELECT s FROM Solicitud s WHERE (s.estadoSolicitud.codEstadoSolicitud = 5 OR s.estadoSolicitud.codEstadoSolicitud = 6) AND s.usuarioControlador.codUsuario = :codUsuarioControlador")
    List<Solicitud> obtenerRespuestasSolicitudes(int codUsuarioControlador);
	
	@Query("SELECT s FROM Solicitud s WHERE s.estadoSolicitud.codEstadoSolicitud = 1 AND s.usuarioControlador.codUsuario = :codUsuarioControlador")
    List<Solicitud> obtenerContactos(int codUsuarioControlador);
	
	Solicitud findByCodSolicitud(int codSolicitud);
}
