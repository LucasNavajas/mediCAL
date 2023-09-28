package com.medical.springserver.model.medicion;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medical.springserver.model.calendario.Calendario;

@Repository
public interface MedicionRepository extends CrudRepository<Medicion, Integer>{
    @Query("SELECT m FROM Medicion m WHERE m.fechaFinVigenciaM IS NULL OR m.fechaFinVigenciaM >= :fechaActual")
    List<Medicion> getAllMediciones(LocalDate fechaActual);

    @Query("SELECT m FROM Medicion m")
    List<Medicion> getAllMedicionesYBajas();
	Medicion findByCodMedicion(Integer codMedicion);

}