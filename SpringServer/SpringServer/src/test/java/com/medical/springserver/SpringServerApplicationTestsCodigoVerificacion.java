package com.medical.springserver;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.codigoverificacion.CodigoVerificacion;
import com.medical.springserver.model.codigoverificacion.CodigoVerificacionDao;
import com.medical.springserver.model.codigoverificacion.CodigoVerificacionRepository;
import com.medical.springserver.model.usuario.Usuario;


@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
public class SpringServerApplicationTestsCodigoVerificacion {
	
	@Autowired 
	private CodigoVerificacionDao codigoverificacionDao;

	
	@Test
	void addCodigoVerificacionTest(){
		CodigoVerificacion codigoverificacion = new CodigoVerificacion();
		
		LocalDate fechaNacimiento = LocalDate.of(2001, 4, 3);
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
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
		codigoverificacionDao.save(codigoverificacion);
	}
	
	//@Test
	void getAllCodigosVerificacionAndDelete() {
		List<CodigoVerificacion> codigosverificacion = codigoverificacionDao.getAllCodigosVerificacion();
		for(CodigoVerificacion codigoverificacion : codigosverificacion) {
			codigoverificacionDao.delete(codigoverificacion);
		}
	}

}

