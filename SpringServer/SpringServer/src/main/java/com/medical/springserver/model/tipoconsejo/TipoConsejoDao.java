package com.medical.springserver.model.tipoconsejo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class TipoConsejoDao {
	@Autowired
	private TipoConsejoRepository repository;
	
	public TipoConsejo save(TipoConsejo tipoConsejo) {
		return repository.save(tipoConsejo);
	}
	
	public List<TipoConsejo> getAllTipoConsejo() {
	    Streamable<TipoConsejo> streamableTipoConsejo = Streamable.of(repository.findAll());
	    List<TipoConsejo> tipoConsejo = new ArrayList<>();
	    streamableTipoConsejo.forEach(tipoConsejo::add);
	    return tipoConsejo;
	}
	
	public void delete(TipoConsejo tipoConsejo) {
		repository.delete(tipoConsejo);
	}
	
}
