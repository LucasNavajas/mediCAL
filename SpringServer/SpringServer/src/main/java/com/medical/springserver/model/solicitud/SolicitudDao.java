package com.medical.springserver.model.solicitud;
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
}
