package com.medical.springserver;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.perfil.Perfil;
import com.medical.springserver.model.perfilpermiso.PerfilPermiso;
import com.medical.springserver.model.perfilpermiso.PerfilPermisoDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsPerfilPermiso {
	
	@Autowired
	private PerfilPermisoDao perfilpermisoDao;
	
	@Test
	void addPerfilPermisoTest() {
				
		
		PerfilPermiso perfilpermiso = new PerfilPermiso();
// Problema: el unico atributo que tiene es el id que se genera 
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		// instancia de Perfil
		Perfil perfil = new Perfil();
		perfil.setDescPerfil("usuario particular");
		perfil.setFechaAltaPerfil(fechaHoy);
		perfil.setFechaFinVigenciaP(null);
		perfil.setMotivoFinVigenciaP(null);
		perfil.setNombrePerfil("particular");
		
		
		System.out.println(perfilpermiso.getPerfil());
		
		
		perfilpermisoDao.save(perfilpermiso);
		
		
		
	}
	
	//@Test
	void getAllPerfilPermisosAndDelete() {
		List<PerfilPermiso> perfilpermisos = perfilpermisoDao.getAllPerfilPermisos();
		for (PerfilPermiso perfilpermiso : perfilpermisos) {
			perfilpermisoDao.delete(perfilpermiso);
		}
		
	}

}
