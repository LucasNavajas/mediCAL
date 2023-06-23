package com.medical.springserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.reporte.Reporte;
import com.medical.springserver.model.reporte.ReporteDao;
import com.medical.springserver.model.tiporeporte.TipoReporte;
import com.medical.springserver.model.tiporeporte.TipoReporteDao;
import com.medical.springserver.model.usuario.Usuario;
import com.medical.springserver.model.usuario.UsuarioDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsReporte {
	
	@Autowired
	private ReporteDao reporteDao;
	@Autowired
	private TipoReporteDao tipoReporteDao;
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Test
	void addReporteTest() {
		
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		LocalDate fechaDesde = LocalDate.of(2022, 01, 6);
		LocalDate fechaHasta = LocalDate.of(2023, 06, 2);
		
		Reporte reporte = new Reporte();
		reporte.setFechaDesde(fechaDesde);
		reporte.setFechaGenerada(fechaHoy);
		reporte.setFechaHasta(fechaHasta);
	
		TipoReporte tipoReporte = new TipoReporte();
		tipoReporte.setNombreTipoReporte("Medicamentos consumidos");
		List<Reporte> reportes = new ArrayList<>();
		reportes.add(reporte);
		
		reporte.setTipoReporte(tipoReporte);
		tipoReporte.setReportes(reportes);
		tipoReporteDao.save(tipoReporte);
		
		
		LocalDate fechaNacimiento = LocalDate.of(2001, 4, 3);

		
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
		usuarioDao.save(usuario);
		reporte.setUsuario(usuario);
		
		
		reporteDao.save(reporte);	
		
		
		
	}
	
	//@Test
	void getAllReportesAndDelete() {
		List<Reporte> reportes = reporteDao.getAllReporte();
		for (Reporte reporte : reportes) {
			reporteDao.delete(reporte);
		}
		
	}

}
