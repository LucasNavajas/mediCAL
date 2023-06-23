package com.medical.springserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.historialfinvigencia.HistorialFinVigencia;
import com.medical.springserver.model.usuario.Usuario;
import com.medical.springserver.model.usuario.UsuarioDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsUsuario {
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Test
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
		
		
	
		//instancia de calendario
		Calendario calendario = new Calendario();
		calendario.setFechaAltaCalendario(fechaHoy);
		calendario.setFechaFinVigenciaC(null);
		calendario.setNombreCalendario("calendario_02");
		
		
		Usuario usuario = new Usuario();
		usuario.setUsuarioUnico("usuario1");
		usuario.setApellidoUsuario("Navajas");
		usuario.setContraseniaUsuario("123");
		usuario.setFechaNacimientoUsuario(fechaNacimiento);
		usuario.setFechaAltaUsuario(fechaHoy);
		usuario.setGeneroUsuario("Hombre");
		usuario.setMailUsuario("lucasnavajas@gmail.com");
		usuario.setNombreInstitucion("Hospital2");
		usuario.setNombreUsuario("Lucas");
		usuario.setTelefonoUsuario("3756409336");
		
		
		historialfinvigencia.setUsuario(usuario);
		List<HistorialFinVigencia> historial = new ArrayList<>();
		historial.add(historialfinvigencia);
		usuario.setHistorial(historial);
		
		calendario.setUsuario(usuario);
		List<Calendario> varcalendario = new ArrayList<>();
		varcalendario.add(calendario);
		usuario.setVarcalendario(varcalendario);
		
		
		System.out.println(usuario.getHistorial());
		System.out.println(usuario.getVarcalendario());
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