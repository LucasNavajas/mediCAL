package com.medical.springserver;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.firebase.auth.FirebaseAuthException;
import com.medical.springserver.model.profesionalsalud.ProfesionalSalud;
import com.medical.springserver.model.profesionalsalud.ProfesionalSaludDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsProfesionalSalud {
	
	@Autowired
	private ProfesionalSaludDao profesionalsaludDao;

	
	//@Test
	void addProfesionalSaludTest() throws FirebaseAuthException {
		// Assuming you have a Connection object named 'connection'
				LocalDate fechaNacimiento = LocalDate.of(2000, 11, 7);
		// Obtener la fecha de hoy
				LocalDate fechaHoy = LocalDate.now();
				
		ProfesionalSalud profesionalsalud = new ProfesionalSalud();
		profesionalsalud.setDni(43117905);
		profesionalsalud.setMatricula(46247);
		profesionalsalud.setUsuarioUnico("usuario2");
		profesionalsalud.setApellidoUsuario("Gomez");
		profesionalsalud.setContraseniaUsuario("1234");
		profesionalsalud.setFechaNacimientoUsuario(fechaNacimiento);
		profesionalsalud.setFechaAltaUsuario(fechaHoy);
		profesionalsalud.setGeneroUsuario("Mujer");
		profesionalsalud.setMailUsuario("mirandagomez@gmail.com");
		profesionalsalud.setNombreInstitucion("Hospital Italiano");
		profesionalsalud.setNombreUsuario("MirandaGM");
		profesionalsalud.setTelefonoUsuario("2612467606");



		
		profesionalsaludDao.save(profesionalsalud);
		
		
	}
	
	//@Test
	void getAllProfesionalesSaludAndDelete() {
		List<ProfesionalSalud> profesionalessalud = profesionalsaludDao.getAllProfesionalesSalud();
		for (ProfesionalSalud profesionalsalud : profesionalessalud) {
			profesionalsaludDao.delete(profesionalsalud);
		}
		
	}

}