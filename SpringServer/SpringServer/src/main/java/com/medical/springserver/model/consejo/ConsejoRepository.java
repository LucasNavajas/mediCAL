package com.medical.springserver.model.consejo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConsejoRepository extends CrudRepository<Consejo, Integer>{
	
}