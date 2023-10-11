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
	
	public List<PerfilPermiso> obtenerPorCodigoPerfil(int codPerfil){
		return repository.findByCodigoPerfil(codPerfil);
	}
	
	public void guardarLista(List<PerfilPermiso> perfilPermisos) {
        repository.saveAll(perfilPermisos);
    }
	
	public void eliminarPorCodigoPerfil(int codPerfil) {
        List<PerfilPermiso> permisosAntiguos = obtenerPorCodigoPerfil(codPerfil);
        for(PerfilPermiso permiso : permisosAntiguos) {
        	delete(permiso);
        }
    }
	
}

