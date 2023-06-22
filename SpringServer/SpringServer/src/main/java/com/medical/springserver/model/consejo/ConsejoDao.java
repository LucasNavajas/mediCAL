package com.medical.springserver.model.consejo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class ConsejoDao {
	@Autowired
	private ConsejoRepository repository;
	
	public Consejo save(Consejo consejo) {
		return repository.save(consejo);
	}
	
	public List<Consejo> getAllConsejo() {
	    Streamable<Consejo> streamableConsejo = Streamable.of(repository.findAll());
	    List<Consejo> consejo = new ArrayList<>();
	    streamableConsejo.forEach(consejo::add);
	    return consejo;
	}
	
	public void delete(Consejo consejo) {
		repository.delete(consejo);
	}
	
}
