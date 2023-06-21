package com.medical.springserver.model.usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConcentracionRepository extends CrudRepository<Concentracion, Integer>{
	
}
