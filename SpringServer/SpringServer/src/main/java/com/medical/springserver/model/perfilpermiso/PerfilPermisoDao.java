package com.medical.springserver.model.perfilpermiso;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class PerfilPermisoDao {
	@Autowired
	private PerfiPermisoRepository repository;
	
	public PerfilPermiso save(PerfilPermiso perfilpermiso) {
		return repository.save(perfilpermiso);
	}
	
	public List<PerfilPermiso> getAllPerfilPermisos() {
	    Streamable<PerfilPermiso> streamablePerfilPermisos = Streamable.of(repository.findAll());
	    List<PerfilPermiso> perfilpermisos = new ArrayList<>();
	    streamablePerfilPermisos.forEach(perfilpermisos::add);
	    return perfilpermisos;
	}
	
	public void delete(PerfilPermiso perfilpermiso) {
		repository.delete(perfilpermiso);
	}
	
}

