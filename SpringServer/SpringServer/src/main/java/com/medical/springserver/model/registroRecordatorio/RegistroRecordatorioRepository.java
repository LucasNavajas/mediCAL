package com.medical.springserver.model.registroRecordatorio;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RegistroRecordatorioRepository extends CrudRepository<RegistroRecordatorio, Integer>{

    @Query("SELECT r FROM RegistroRecordatorio r WHERE " +
    		"r.recordatorio.calendario.codCalendario = :codCalendario " +
            "AND r.fechaTomaEsperada BETWEEN :startDate AND :endDate ORDER BY r.fechaTomaEsperada")
     List<RegistroRecordatorio> obtenerRegistrosCalendarioEnRango(
             @Param("codCalendario") int codCalendario,
             @Param("startDate") LocalDateTime startDate,
             @Param("endDate") LocalDateTime endDate
     );
    
    @Query("SELECT r FROM RegistroRecordatorio r WHERE " +
            "r.fechaTomaEsperada BETWEEN :startDate AND :endDate AND r.tomaRegistroRecordatorio = FALSE AND r.omision IS NULL AND r.recordatorio.frecuencia IS NOT NULL ORDER BY r.fechaTomaEsperada")
     List<RegistroRecordatorio> obtenerRegistrosCalendarioActuales(
             @Param("startDate") LocalDateTime startDate,
             @Param("endDate") LocalDateTime endDate
     );
    
    @Query("SELECT r FROM RegistroRecordatorio r WHERE " +
            "r.recordatorio.calendario.codCalendario = :codCalendario " +
            "AND r.fechaTomaEsperada < :endDate " +
            "AND r.tomaRegistroRecordatorio = FALSE " +
            "AND r.recordatorio.frecuencia IS NOT NULL "+
            "AND r.omision IS NULL " +  // Agrega esta condición
            "ORDER BY r.fechaTomaEsperada")
    List<RegistroRecordatorio> obtenerRegistrosCalendarioNotificacion(
             @Param("codCalendario") int codCalendario,
             @Param("endDate") LocalDateTime endDate
    );
}
