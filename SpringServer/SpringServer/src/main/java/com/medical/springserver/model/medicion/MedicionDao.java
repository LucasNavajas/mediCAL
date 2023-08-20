package com.medical.springserver.model.medicion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class MedicionDao {
	@Autowired
	private MedicionRepository repository;
	
	public Medicion save(Medicion medicion) {
		return repository.save(medicion);
	}
	
	public List<Medicion> getAllMediciones(){
		Streamable<Medicion> streamableMediciones = Streamable.of(repository.findAll());
		List<Medicion> mediciones = new ArrayList<>();
		streamableMediciones.forEach(mediciones::add);
		return mediciones;
	}
	
	public void delete(Medicion medicion) {
		repository.delete(medicion);
	}

}
