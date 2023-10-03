package com.medical.springserver.model.perfil;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PerfilRepository extends CrudRepository<Perfil, Integer> {
    Perfil findByCodPerfil(int codPerfil);

    @Query("SELECT p FROM Perfil p WHERE p.fechaFinVigenciaP IS NULL OR p.fechaFinVigenciaP >= :fechaActual")
    List<Perfil> getAllPerfiles(LocalDate fechaActual);

    @Query("SELECT p FROM Perfil p")
    List<Perfil> getAllPerfiles();
}

