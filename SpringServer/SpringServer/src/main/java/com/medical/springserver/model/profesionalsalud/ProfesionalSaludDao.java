package com.medical.springserver.model.profesionalsalud;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class ProfesionalSaludDao {
	@Autowired
	private ProfesionalSaludRepository repository;
	
	public ProfesionalSalud save(ProfesionalSalud profesionalsalud) {
		return repository.save(profesionalsalud);
	}
	
	public List<ProfesionalSalud> getAllProfesionalesSalud() {
	    Streamable<ProfesionalSalud> streamableProfesionalesSalud = Streamable.of(repository.findAll());
	    List<ProfesionalSalud> profesionalessalud = new ArrayList<>();
	    streamableProfesionalesSalud.forEach(profesionalessalud::add);
	    return profesionalessalud;
	}
	
	public void delete(ProfesionalSalud profesionalsalud) {
		repository.delete(profesionalsalud);
	}
	
}