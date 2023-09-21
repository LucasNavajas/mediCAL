package com.medical.springserver.model.perfil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class PerfilDao{
	@Autowired
	private PerfilRepository repository;
	
	public Perfil save(Perfil perfil) {
		return repository.save(perfil);
	}
	
	public List<Perfil> getAllPerfiles() {
	    Streamable<Perfil> streamablePerfiles = Streamable.of(repository.findAll());
	    List<Perfil> perfiles = new ArrayList<>();
	    streamablePerfiles.forEach(perfiles::add);
	    return perfiles;
	}
	
	public void delete(Perfil perfil) {
		repository.delete(perfil);
	}
	
	public Perfil findByCodPerfil(int codPefil) {
		return repository.findByCodPerfil(codPefil);
	}
	
}

