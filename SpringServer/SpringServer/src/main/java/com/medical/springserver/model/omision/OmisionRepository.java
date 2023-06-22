package com.medical.springserver.model.omision;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OmisionRepository extends CrudRepository<Omision, Integer>{

}
