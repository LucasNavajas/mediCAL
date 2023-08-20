package com.medical.springserver.model.sintoma;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class SintomaDao {
	@Autowired
	private SintomaRepository repository;
	
	public Sintoma save(Sintoma sintoma) {
		return repository.save(sintoma);
	}
	
	public List<Sintoma> getAllSintomas(){
		Streamable<Sintoma> streamableSintomas = Streamable.of(repository.findAll());
		List<Sintoma> sintomas = new ArrayList<>();
		streamableSintomas.forEach(sintomas::add);
		return sintomas;
	}
	
	public void delete(Sintoma sintoma) {
		repository.delete(sintoma);
	}

}