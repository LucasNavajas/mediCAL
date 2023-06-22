package com.medical.springserver.model.omision;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class OmisionDao {
	@Autowired
	private OmisionRepository repository;
	
	public Omision save(Omision omision) {
		return repository.save(omision);
	}
	
	public List<Omision> getAllOmisiones(){
		Streamable<Omision> streamableOmisiones = Streamable.of(repository.findAll());
		List<Omision> omisiones = new ArrayList<>();
		streamableOmisiones.forEach(omisiones::add);
		return omisiones;
	}
	
	public void delete(Omision omision) {
		repository.delete(omision);
	}
}
