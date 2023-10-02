package com.medical.springserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.estadosolicitud.EstadoSolicitud;
import com.medical.springserver.model.estadosolicitud.EstadoSolicitudDao;
import com.medical.springserver.model.solicitud.Solicitud;
import com.medical.springserver.model.solicitud.SolicitudDao;
import com.medical.springserver.model.usuario.Usuario;
import com.medical.springserver.model.usuario.UsuarioDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsSolicitud {
	
	@Autowired
	private SolicitudDao solicitudDao;
	@Autowired
	private EstadoSolicitudDao estadoSolicitudDao;
	@Autowired 
	private UsuarioDao usuarioDao;
	
	//@Test
	void addSolicitudTest() {
				
		// Obtener la fecha de hoy
		
		LocalDate fechaHoy = LocalDate.now();
		LocalDate fechaNacimiento = LocalDate.of(2001, 4, 3);
		
		Solicitud solicitud = new Solicitud();
		EstadoSolicitud estadoS = new EstadoSolicitud()	;
		solicitud.setFechaSolicitud(fechaHoy);
		solicitud.setEstadoSolicitud(estadoS);
		
		estadoS.setNombreEstadoSolicitud("Aceptado");
		List<Solicitud> solicitudes = new ArrayList<>();	
		solicitudes.add(solicitud);
		estadoS.setSolicitudes(solicitudes);
		
		System.out.println(solicitud.getEstadoSolicitud());
		System.out.println(estadoS.getSolicitudes());
		estadoSolicitudDao.save(estadoS);
		
		Usuario usuario = new Usuario();
		usuario.setUsuarioUnico("usuario1");
		usuario.setApellidoUsuario("Navajas");
		usuario.setContraseniaUsuario("contraseñaHash?");
		usuario.setFechaNacimientoUsuario(fechaNacimiento);
		usuario.setFechaAltaUsuario(fechaHoy);
		usuario.setGeneroUsuario("Hombre");
		usuario.setMailUsuario("lucasnavajas@gmail.com");
		usuario.setNombreInstitucion("Hospital2");
		usuario.setNombreUsuario("Lucas");
		usuario.setTelefonoUsuario("3756409336");
		
		Usuario usuario2 = new Usuario();
		usuario.setUsuarioUnico("usuario2");
		usuario.setApellidoUsuario("Navajas");
		usuario.setContraseniaUsuario("contraseñaHash?");
		usuario.setFechaNacimientoUsuario(fechaNacimiento);
		usuario.setFechaAltaUsuario(fechaHoy);
		usuario.setGeneroUsuario("Hombre");
		usuario.setMailUsuario("pedronavajas@gmail.com");
		usuario.setNombreInstitucion("Hospital2");
		usuario.setNombreUsuario("Pedro");
		usuario.setTelefonoUsuario("3756409336");
		
		usuarioDao.save(usuario2);
		usuarioDao.save(usuario);
		solicitud.setUsuarioControlado(usuario2);
		solicitud.setUsuarioControlador(usuario);
		solicitudDao.save(solicitud);
		
	}
	
	//@Test
	void getAllSolicitudesAndDelete() {
		List<Solicitud> solicitudes = solicitudDao.getAllSolicitudes();
		for (Solicitud solicitud : solicitudes) {
			solicitudDao.delete(solicitud);
		}
		
	}

}
