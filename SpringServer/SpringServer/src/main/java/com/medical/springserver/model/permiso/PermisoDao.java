package com.medical.springserver.model.permiso;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class PermisoDao {
	@Autowired
	private PermisoRepository repository;
	
	public Permiso save(Permiso permiso) {
		return repository.save(permiso);
	}
	
	public List<Permiso> getAllPermisos() {
	    Streamable<Permiso> streamablePermisos = Streamable.of(repository.findAll());
	    List<Permiso> permisos = new ArrayList<>();
	    streamablePermisos.forEach(permisos::add);
	    return permisos;
	}
	
	public void delete(Permiso permiso) {
		repository.delete(permiso);
	}
	
}

