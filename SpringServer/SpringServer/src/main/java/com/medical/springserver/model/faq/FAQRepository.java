package com.medical.springserver.model.faq;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FAQRepository extends CrudRepository<FAQ, Integer>{
	@Query("SELECT f FROM FAQ f where f.fechaFinVigenciaFAQ IS NULL OR f.fechaFinVigenciaFAQ >= :fechaActual")
	List<FAQ> getAllFAQ(LocalDate fechaActual);
	
	@Query("SELECT f from FAQ f")
	List<FAQ> getAllFAQS();
	
	FAQ findByCodFAQ(int codFAQ);
	
}
