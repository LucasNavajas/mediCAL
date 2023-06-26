package com.medical.springserver.model.codigoverificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CodigoverificacionCleanup {

    private final CodigoVerificacionRepository codigoverificacionRepository;

    @Autowired
    public CodigoverificacionCleanup(CodigoVerificacionRepository codigoverificacionRepository) {
        this.codigoverificacionRepository = codigoverificacionRepository;
        System.out.println("CodigoverificacionCleanup instantiated");
    }
    @Transactional
    @Scheduled(cron = "0 * * * * *") // Se ejecuta cada minuto
    public void cleanupExpiredCodigos() {
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);

        codigoverificacionRepository.deleteByFechaGeneradoBefore(twentyFourHoursAgo);

        List<CodigoVerificacion> expiredCodigos = codigoverificacionRepository.findByFechaGeneradoBefore(twentyFourHoursAgo);

        codigoverificacionRepository.deleteAll(expiredCodigos);
    }
}
