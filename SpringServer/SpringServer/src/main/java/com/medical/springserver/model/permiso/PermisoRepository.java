package com.medical.springserver.model.permiso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermisoRepository extends CrudRepository<Permiso, Integer>{
	
}
