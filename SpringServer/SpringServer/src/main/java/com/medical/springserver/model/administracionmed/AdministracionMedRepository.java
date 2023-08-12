package com.medical.springserver.model.administracionmed;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medical.springserver.model.usuario.Usuario;

public interface AdministracionMedRepository extends CrudRepository<AdministracionMed, Integer>{
	Optional<AdministracionMed> findByCodAdministracionMed(int codAdministracionMed);
}