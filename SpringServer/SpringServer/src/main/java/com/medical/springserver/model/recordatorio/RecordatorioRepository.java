package com.medical.springserver.model.recordatorio;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordatorioRepository extends CrudRepository<Recordatorio, Integer>{
	public Recordatorio findByCodRecordatorio(int codRecordatorio);
	
	// Método para buscar todas las instancias de Recordatorio con un codcalendario específico
    @Query("SELECT r FROM Recordatorio r WHERE r.calendario.codCalendario = :codcalendario AND r.fechaFinVigenciaR = null")
    List<Recordatorio> findByCodCalendario(@Param("codcalendario") Integer codcalendario);

	@Query("SELECT r FROM Recordatorio r WHERE r.fechaAltaRecordatorio < :time AND (r.duracionRecordatorio IS NULL OR r.instruccion IS NULL)")
    List<Recordatorio> buscarRecordatoriosBasura(@Param("time") LocalDate time);

}
