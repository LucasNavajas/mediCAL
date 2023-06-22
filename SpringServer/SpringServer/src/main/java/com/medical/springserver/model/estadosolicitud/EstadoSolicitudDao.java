package com.medical.springserver.model.estadosolicitud;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class EstadoSolicitudDao {
	@Autowired
	private EstadoSolicitudRepository repository;
	
	public EstadoSolicitud save(EstadoSolicitud estadoSolicitud) {
		return repository.save(estadoSolicitud);
	}
	
	public List<EstadoSolicitud> getAllEstadoSolicitud() {
	    Streamable<EstadoSolicitud> streamableEstadoSolicitud= Streamable.of(repository.findAll());
	    List<EstadoSolicitud> estadoSolicitud = new ArrayList<>();
	    streamableEstadoSolicitud.forEach(estadoSolicitud::add);
	    return estadoSolicitud;
	}
	
	public void delete(EstadoSolicitud estadoSolicitud) {
		repository.delete(estadoSolicitud);
	}
	
}
