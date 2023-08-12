package com.medical.springserver.model.administracionmed;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service

public class AdministracionMedDao {
	@Autowired
	private AdministracionMedRepository repository;
	
	public AdministracionMed save(AdministracionMed administracionmed) {
		return repository.save(administracionmed);
	}
	
	public List<AdministracionMed> getAllAdministracionMeds(){
		Streamable<AdministracionMed> streamableAdministracionMeds = Streamable.of(repository.findAll());
		List<AdministracionMed> administracionmeds = new ArrayList<>();
		streamableAdministracionMeds.forEach(administracionmeds::add);
		return administracionmeds;
	}
	
	public void delete(AdministracionMed administracionmed) {
		repository.delete(administracionmed);
	}
	
	public Optional<AdministracionMed> findByCodAdministracionMed(int codAdministracionMed) {
		Optional<AdministracionMed> optionalAdministracionMed = repository.findByCodAdministracionMed(codAdministracionMed);
	    return Optional.ofNullable(optionalAdministracionMed.orElse(null)); // o maneja el caso de null según tus necesidades
	}

}
