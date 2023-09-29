package com.medical.springserver.model.administracionmed;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medical.springserver.model.usuario.Usuario;

public interface AdministracionMedRepository extends CrudRepository<AdministracionMed, Integer>{
	@Query("SELECT am FROM AdministracionMed am WHERE am.fechaFinVigenciaAM IS NULL OR am.fechaFinVigenciaAM >= :fechaActual")
    List<AdministracionMed> getAllAdministracionMed(LocalDate fechaActual);

    @Query("SELECT am FROM AdministracionMed am")
    List<AdministracionMed> getAllAdministracionMedYBajas();

	
	Optional<AdministracionMed> findByCodAdministracionMed(int codAdministracionMed);
}