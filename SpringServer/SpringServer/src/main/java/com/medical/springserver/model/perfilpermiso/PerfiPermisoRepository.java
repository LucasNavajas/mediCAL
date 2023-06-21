package com.medical.springserver.model.perfilpermiso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PerfiPermisoRepository extends CrudRepository<PerfilPermiso, Integer>{
	
}
