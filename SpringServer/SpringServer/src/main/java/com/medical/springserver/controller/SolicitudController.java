package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.estadosolicitud.EstadoSolicitud;
import com.medical.springserver.model.solicitud.Solicitud;
import com.medical.springserver.model.solicitud.SolicitudDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
public class SolicitudController {

	@Autowired
	SolicitudDao solicitudDao;
	
	@GetMapping("/solicitud/get-all")
	public List<Solicitud> getAllSolicitudes(){
		return solicitudDao.getAllSolicitudes();
	}
	
	@PostMapping("/solicitud/save")
	public Solicitud save(@RequestBody Solicitud solicitud){
		return solicitudDao.save(solicitud);
	}
	
	@GetMapping("/solicitud/pendiente/{codUsuario}")
	public Solicitud obtenerSolicitudPendiente(@PathVariable int codUsuario) {
		return solicitudDao.obtenerSolicitudPendiente(codUsuario);
	}
	@PutMapping("/solicitud/modificar-estado/{codSolicitud}")
	public ResponseEntity<Solicitud> actualizarEstadoSolicitud(
			 @PathVariable int codSolicitud,
		        @RequestBody EstadoSolicitud nuevoEstadoSolicitud) {
		    // Buscar la solicitud por el código
		    Solicitud solicitud = solicitudDao.findByCodSolicitud(codSolicitud);
		    if (solicitud == null) {
		        return ResponseEntity.notFound().build();
		    }

		    // Actualizar el estado de la solicitud
		    solicitud.setEstadoSolicitud(nuevoEstadoSolicitud);
		    solicitudDao.save(solicitud);

	    return ResponseEntity.ok(solicitud);
	}
	
	@GetMapping("/solicitud/respuestas/{codUsuario}")
	public List<Solicitud> obtenerRespuestasSolicitud(@PathVariable int codUsuario) {
		return solicitudDao.obtenerRespuestasSolicitud(codUsuario);
	}
	
	@GetMapping("/solicitud/contactos/{codUsuario}")
	public List<Solicitud> obtenerContactos(@PathVariable int codUsuario) {
		return solicitudDao.obtenerContactos(codUsuario);
	}
	
	@GetMapping("/solicitud/supervisor/{codUsuario}")
	public List<Solicitud> obtenerSupervisor(@PathVariable int codUsuario){
		return solicitudDao.obtenerSupervisor(codUsuario);
	}
	
	@GetMapping("/solicitud/get-solicitudes-activas")
	public List<Solicitud> obtenerSolicitudesActivas(){
		return solicitudDao.obtenerSolicitudesActivas();
	}
}
