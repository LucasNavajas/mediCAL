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
}
