package com.medical.springserver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.firebase.auth.FirebaseAuthException;
import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.codigoverificacion.CodigoVerificacion;
import com.medical.springserver.model.historialfinvigencia.HistorialFinVigencia;
import com.medical.springserver.model.perfil.Perfil;
import com.medical.springserver.model.solicitud.Solicitud;
import com.medical.springserver.model.usuario.Usuario;
import com.medical.springserver.model.usuario.UsuarioDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsUsuario {
	
	@Autowired
	private UsuarioDao usuarioDao;
	

	
	//@Test
	void addUsuarioTest() {
		
		// instancia de historial
		HistorialFinVigencia historialfinvigencia = new HistorialFinVigencia();
		
		LocalDate fechaDesde = LocalDate.of(2023, 1, 1);
		LocalDate fechaHasta = LocalDate.of(2023,9,1);
		
		historialfinvigencia.setFechaDesdeFV(fechaDesde);
		historialfinvigencia.setFechaHastaFV(fechaHasta);
		historialfinvigencia.setMotivoFV("Licencia por maternidad");
		
		LocalDate fechaNacimiento = LocalDate.of(2001, 4, 3);

		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		LocalDateTime hoy = LocalDateTime.now();
		
		
	
		// instancia de calendario
		Calendario calendario = new Calendario();
		calendario.setFechaAltaCalendario(fechaHoy);
		calendario.setFechaFinVigenciaC(null);
		calendario.setNombreCalendario("calendario_02");
		
		
		// instancia de perfil
		Perfil perfil = new Perfil();
		perfil.setFechaAltaPerfil(fechaHoy);
		perfil.setDescPerfil("usuario particular");
		perfil.setFechaFinVigenciaP(null);
		perfil.setMotivoFinVigenciaP(null);
		perfil.setNombrePerfil("Particular");
		
		
		// instancia de Solicitud Controlador
		Solicitud solicitudControlador = new Solicitud();
		solicitudControlador.setFechaSolicitud(fechaHoy);
		
		// instancia de Solicitud Controlado
		Solicitud solicitudControlado = new Solicitud();
		solicitudControlado.setFechaSolicitud(fechaHoy);
		
		// instancia de CodVerificacion
		CodigoVerificacion codigoVerificacion = new CodigoVerificacion();
		
		
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
		
		historialfinvigencia.setUsuario(usuario);
		List<HistorialFinVigencia> historial = new ArrayList<>();
		historial.add(historialfinvigencia);
		usuario.setHistorial(historial);
		
		calendario.setUsuario(usuario);
		List<Calendario> varcalendario = new ArrayList<>();
		varcalendario.add(calendario);
		usuario.setVarcalendario(varcalendario);
		
		solicitudControlador.setUsuarioControlador(usuario);
		List<Solicitud> varsolicitud = new ArrayList<>();
		varsolicitud.add(solicitudControlador);
		usuario.setSolicitudControlador(varsolicitud);
		
		solicitudControlado.setUsuarioControlado(usuario2);
		List<Solicitud> var1solicitud = new ArrayList<>();
		var1solicitud.add(solicitudControlado);
		
		
		// La relacion de codVerificacion es a 1, está bien que sea un set de tipo List?
		//codigoVerificacion.setUsuario(usuario);
		//List<CodigoVerificacion> varcodigoVerificacion = new ArrayList<>();
		//varcodigoVerificacion.add(codigoVerificacion);
		usuario.setCodigoVerificacion(new CodigoVerificacion());
		
		
		System.out.println(usuario.getHistorial());
		System.out.println(usuario.getVarcalendario());
		System.out.println(usuario.getPerfil());
		System.out.println(usuario.getSolicitudControlador());
		System.out.println(usuario.getSolicitudControlado());
		System.out.println(usuario.getCodigoVerificacion());
		
		usuarioDao.save(usuario);
		
	}
	
	//@Test
	void getAllUsuariosAndDelete() {
		List<Usuario> usuarios = usuarioDao.getAllUsuarios();
		for (Usuario usuario : usuarios) {
			usuarioDao.delete(usuario);
		}
		
	}
	

}
