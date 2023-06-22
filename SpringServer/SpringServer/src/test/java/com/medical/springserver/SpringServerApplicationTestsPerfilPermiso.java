package com.medical.springserver;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
