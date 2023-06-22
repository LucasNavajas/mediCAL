package com.medical.springserver.model.profesionalsalud;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfesionalSaludRepository extends CrudRepository<ProfesionalSalud, Integer>{
	
}