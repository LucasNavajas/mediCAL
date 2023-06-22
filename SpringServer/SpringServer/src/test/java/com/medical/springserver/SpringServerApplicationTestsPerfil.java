package com.medical.springserver;


import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.perfil.Perfil;
import com.medical.springserver.model.perfil.PerfilDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsPerfil {
	
	@Autowired
	private PerfilDao perfilDao;
	
	@Test
	void addPerfilTest() {
				
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
	    LocalDate fechaNula = null;
		
		com.medical.springserver.model.perfil.Perfil perfil = new Perfil();
		
		perfil.setDescPerfil("Usuarios que utilizan la app desde sus celulares");
		perfil.setFechaAltaPerfil(fechaHoy);
		perfil.setFechaFinVigenciaP(fechaNula);
		perfil.setMotivoFinVigenciaP(null);
		perfil.setNombrePerfil("Particular");
		
		perfilDao.save(perfil);
		
		
	}
	
	//@Test
		void getAllPerfilesAndDelete() {
			List<Perfil> perfiles = perfilDao.getAllPerfiles();
			for (Perfil perfil : perfiles) {
				perfilDao.delete(perfil);
			}
			
	}

}
