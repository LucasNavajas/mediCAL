package com.medical.springserver.model.faq;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FAQRepository extends CrudRepository<FAQ, Integer>{
	
}
