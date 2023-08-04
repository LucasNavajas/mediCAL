package com.medical.springserver.model.codigoverificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.medical.springserver.model.usuario.Usuario;
import com.medical.springserver.model.usuario.UsuarioRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CodigoverificacionCleanup {

    private final CodigoVerificacionRepository codigoverificacionRepository;
    private final UsuarioRepository usuarioRepository; // Asumiendo que tienes un repositorio para Usuario también.

    @Autowired
    public CodigoverificacionCleanup(CodigoVerificacionRepository codigoverificacionRepository,
                                     UsuarioRepository usuarioRepository) {
        this.codigoverificacionRepository = codigoverificacionRepository;
        this.usuarioRepository = usuarioRepository;
        System.out.println("CodigoverificacionCleanup instantiated");
    }

    @Transactional
    @Scheduled(cron = "0 * * * * *") // Se ejecuta cada minuto
    public void cleanupExpiredCodigos() {
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);

        List<CodigoVerificacion> expiredCodigos = codigoverificacionRepository.findByFechaGeneradoBefore(twentyFourHoursAgo);
        
        for (CodigoVerificacion codigoVerificacion : expiredCodigos) {
            Usuario usuario = usuarioRepository.findByCodigoVerificacion(codigoVerificacion.getCodVerificacion());
            if (usuario != null) {
            	if(usuario.getFechaAltaUsuario()!=null) {
	                usuario.setCodigoVerificacion(null);
	                usuarioRepository.save(usuario);
            	}
            	else {
            		usuarioRepository.delete(usuario);
            	}
            }
        }

        codigoverificacionRepository.deleteByFechaGeneradoBefore(twentyFourHoursAgo);
        codigoverificacionRepository.deleteAll(expiredCodigos);
    }
}