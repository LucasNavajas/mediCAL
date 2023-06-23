package com.medical.springserver.model.administracionmed;

import java.util.ArrayList;
import java.util.List;

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

}