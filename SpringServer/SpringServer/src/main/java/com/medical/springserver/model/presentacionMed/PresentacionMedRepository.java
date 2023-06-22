package com.medical.springserver.model.presentacionMed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PresentacionMedRepository extends CrudRepository<PresentacionMed, Integer>{

}
