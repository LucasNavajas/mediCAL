package com.medical.springserver.model.codigoverificacion;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class CodigoVerificacionDao {
	@Autowired
	private CodigoVerificacionRepository repository;
	
	public CodigoVerificacion save(CodigoVerificacion codigoverificacion){
		return repository.save(codigoverificacion);
	}
	
	public List<CodigoVerificacion> getAllCodigosVerificacion(){
		Streamable<CodigoVerificacion> streamableCodigosVerificacion = Streamable.of(repository.findAll());
		List<CodigoVerificacion> codigosverificacion = new ArrayList<>();
		streamableCodigosVerificacion.forEach(codigosverificacion::add);
		return codigosverificacion;
	}
	
	public void delete(CodigoVerificacion codigoverificacion) {
		repository.delete(codigoverificacion);
	}
}
