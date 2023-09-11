package com.medical.springserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.perfilpermiso.PerfilPermiso;
import com.medical.springserver.model.permiso.Permiso;
import com.medical.springserver.model.permiso.PermisoDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsPermiso {
	
	@Autowired
	private PermisoDao permisoDao;
	
	//@Test
	void addPermisoTest() {
				
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		LocalDate fechaFinVigPermiso = LocalDate.of(2023, 11, 7);
		
		Permiso permiso = new Permiso();
		permiso.setDescPermiso("se puede agregar un nuevo usuario");
		permiso.setFechaAltaPermiso(fechaHoy);
		permiso.setFechaFinVigenciaP(fechaFinVigPermiso);
		permiso.setNombrePermiso("Aniadir Usuario");
		
		// instancia de PerfilPermiso
		PerfilPermiso perfilPermiso = new PerfilPermiso();
		perfilPermiso.setIdPerfilPermiso(0);
		
		perfilPermiso.setPermiso(permiso);
		List<PerfilPermiso> varperfilPermiso = new ArrayList<>();
		varperfilPermiso.add(perfilPermiso);
		permiso.setPerfilPermiso(varperfilPermiso);
		
		System.out.println(permiso.getPerfilPermiso());
		
		permisoDao.save(permiso);
		
		
	}
	
	//@Test
	void getAllPermisosAndDelete() {
		List<Permiso> permisos = permisoDao.getAllPermisos();
		for (Permiso permiso : permisos) {
			permisoDao.delete(permiso);
		}
		
	}

}
