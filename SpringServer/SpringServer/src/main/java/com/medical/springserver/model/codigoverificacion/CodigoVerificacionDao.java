package com.medical.springserver.model.codigoverificacion;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CodigoVerificacionDao {
	@Autowired
	private CodigoVerificacionRepository repository;
	
	public CodigoVerificacion save(CodigoVerificacion CodigoVerificacion){
		if(repository.existsByCodVerificacion(CodigoVerificacion.getCodVerificacion())) {
		do {
            CodigoVerificacion = new CodigoVerificacion();
        } while (repository.existsByCodVerificacion(CodigoVerificacion.getCodVerificacion()));
		}
		return repository.save(CodigoVerificacion);
	}
	
	public List<CodigoVerificacion> getAllCodigosVerificacion(){
		Streamable<CodigoVerificacion> streamableCodigosVerificacion = Streamable.of(repository.findAll());
		List<CodigoVerificacion> CodigosVerificacion = new ArrayList<>();
		streamableCodigosVerificacion.forEach(CodigosVerificacion::add);
		return CodigosVerificacion;
	}

	public void delete(CodigoVerificacion CodigoVerificacion) {
		repository.delete(CodigoVerificacion);
	}
}
