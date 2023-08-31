package com.medical.springserver.model.solicitud;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class SolicitudDao {
	@Autowired
	private SolicitudRepository repository;
	
	public Solicitud save(Solicitud Solicitud){
		Solicitud.setFechaSolicitud(LocalDate.now());
		return repository.save(Solicitud);
	}
	
	public List<Solicitud> getAllSolicitudes(){
		Streamable<Solicitud> streamableSolicitudes = Streamable.of(repository.findAll());
		List<Solicitud> Solicitudes = new ArrayList<>();
		streamableSolicitudes.forEach(Solicitudes::add);
		return Solicitudes;
	}

	public void delete(Solicitud Solicitud) {
		repository.delete(Solicitud);
	}
	public Solicitud obtenerSolicitudPendiente(int codUsuarioControlado) {
		return repository.obtenerSolicitudPendienteUsuario(codUsuarioControlado);
	}
	
	public Solicitud findByCodSolicitud(int codSolicitud) {
		return repository.findByCodSolicitud(codSolicitud);
	}
	
	public List<Solicitud> obtenerRespuestasSolicitud(int codUsuarioControlador){
		return repository.obtenerRespuestasSolicitudes(codUsuarioControlador);
	}
	
	public List<Solicitud> obtenerContactos(int codUsuarioControlador){
		return repository.obtenerContactos(codUsuarioControlador);
	}
	
	public List<Solicitud> obtenerSupervisor(int codUsuario){
		return repository.obtenerSupervisor(codUsuario);
	}
	
	public List<Solicitud> obtenerTodasSolicitudesUsuario(int codUsuario){
		return repository.obtenerTodasSolicitudesUsuario(codUsuario);
	}

	public List<Solicitud> obtenerSolicitudesActivas() {
		return repository.obtenerSolicitudesActivas();
	}
}
