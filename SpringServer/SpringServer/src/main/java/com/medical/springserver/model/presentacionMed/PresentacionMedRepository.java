package com.medical.springserver.model.presentacionMed;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PresentacionMedRepository extends CrudRepository<PresentacionMed, Integer>{
	PresentacionMed findByCodPresentacionMed(int codPresentacionMed);
	List<PresentacionMed> findByAdministracionMed_CodAdministracionMed(int codAdministracionMed);
}
