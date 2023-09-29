package com.medical.springserver.model.presentacionMed;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PresentacionMedRepository extends CrudRepository<PresentacionMed, Integer>{
    @Query("SELECT pm FROM PresentacionMed pm WHERE pm.fechaFinVigenciaPM IS NULL OR pm.fechaFinVigenciaPM >= :fechaActual")
    List<PresentacionMed> getAllPresentacionesMedicas(LocalDate fechaActual);

    @Query("SELECT pm FROM PresentacionMed pm")
    List<PresentacionMed> getAllPresentacionesMedicasYBajas();
	PresentacionMed findByCodPresentacionMed(int codPresentacionMed);
	List<PresentacionMed> findByAdministracionMed_CodAdministracionMed(int codAdministracionMed);
}
