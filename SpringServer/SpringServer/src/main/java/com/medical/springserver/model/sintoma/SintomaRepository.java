package com.medical.springserver.model.sintoma;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SintomaRepository extends CrudRepository<Sintoma, Integer>{
    @Query("SELECT s FROM Sintoma s WHERE s.fechaFinVigenciaS IS NULL OR s.fechaFinVigenciaS >= :fechaActual")
    List<Sintoma> getAllSintomas(LocalDate fechaActual);

    @Query("SELECT s FROM Sintoma s")
    List<Sintoma> getAllSintomasYBajas();

    Sintoma findByCodSintoma(Integer codSintoma);
}

