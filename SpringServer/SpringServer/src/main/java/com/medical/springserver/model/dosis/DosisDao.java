package com.medical.springserver.model.dosis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class DosisDao {
	@Autowired
	private DosisRepository repository;
	
	public Dosis save(Dosis dosis) {
		return repository.save(dosis);
	}
	
	public List<Dosis> getAllDosis(){
		Streamable<Dosis> streamableDosis = Streamable.of(repository.findAll());
		List<Dosis> dosiss = new ArrayList<>();
		streamableDosis.forEach(dosiss::add);
		return dosiss;
	}
	
	public void delete(Dosis dosis) {
		repository.delete(dosis);
	}
	
}
