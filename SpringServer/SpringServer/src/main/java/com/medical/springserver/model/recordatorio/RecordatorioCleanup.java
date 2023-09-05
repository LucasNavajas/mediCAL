package com.medical.springserver.model.recordatorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import jakarta.transaction.Transactional;

@Component
public class RecordatorioCleanup {
	 private final RecordatorioRepository repository;

	    @Autowired
	    public RecordatorioCleanup(RecordatorioRepository repository) {
	        this.repository = repository;
	        System.out.println("RecordatorioCleanup instantiated");
	    }

	    @Transactional
	    @Scheduled(cron = "0 * * * * *") // Se ejecuta cada minuto
	    public void cleanupRecordatoriosBasura() {
	        LocalDate dosDias = LocalDate.now().minusDays(2);
	        List<Recordatorio> recordatoriosBasura = repository.buscarRecordatoriosBasura(dosDias);
	        repository.deleteAll(recordatoriosBasura);

	    }
}
