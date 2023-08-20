package com.medical.springserver.model.medicion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicionRepository extends CrudRepository<Medicion, Integer>{

}