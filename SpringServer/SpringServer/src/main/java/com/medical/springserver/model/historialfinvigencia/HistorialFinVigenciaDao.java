package com.medical.springserver.model.historialfinvigencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class HistorialFinVigenciaDao {
	@Autowired
	private HistorialFinVigenciaRepository repository;
	
	public HistorialFinVigencia save(HistorialFinVigencia historialfinvigencia) {
		return repository.save(historialfinvigencia);
	}
	
	public List<HistorialFinVigencia> getAllHistorialFinVigencias() {
	    Streamable<HistorialFinVigencia> streamableHistorialFinVigencias = Streamable.of(repository.findAll());
	    List<HistorialFinVigencia> historialfinvigencias = new ArrayList<>();
	    streamableHistorialFinVigencias.forEach(historialfinvigencias::add);
	    return historialfinvigencias;
	}
	
	public void delete(HistorialFinVigencia historialfinvigencia) {
		repository.delete(historialfinvigencia);
	}
	
	public List<HistorialFinVigencia> findByCodUsuario(int codUsuario){
		return repository.findHistorialByUsuarioCod(codUsuario);
	}
	
}


