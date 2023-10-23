package com.medical.springserver;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.perfil.Perfil;
import com.medical.springserver.model.perfil.PerfilDao;
import com.medical.springserver.model.perfilpermiso.PerfilPermiso;
import com.medical.springserver.model.perfilpermiso.PerfilPermisoDao;
import com.medical.springserver.model.permiso.Permiso;
import com.medical.springserver.model.permiso.PermisoDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsPerfil {
	
	@Autowired
	private PerfilDao perfilDao;
	@Autowired 
	private PermisoDao permisoDao;
	@Autowired 
	private PerfilPermisoDao perfilPermisoDao;
	
	@Test
	void addPerfilTest() {
				
		List<Permiso> permisosTotales = new ArrayList<>();
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		Permiso permiso = new Permiso();
		permiso.setNombrePermiso("Agregar Calendarios");
		permiso.setDescPermiso("Permite al usuario agregar un calendario");
		permiso.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso);
		permisoDao.save(permiso);
		
		Permiso permiso2 = new Permiso();
		permiso2.setNombrePermiso("Modificar Calendarios");
		permiso2.setDescPermiso("Permite al usuario modificar los calendarios existentes");
		permiso2.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso2);
		permisoDao.save(permiso2);
		
		Permiso permiso3 = new Permiso();
		permiso3.setNombrePermiso("Eliminar Calendarios");
		permiso3.setDescPermiso("Permite al usuario eliminar un calendario relacionado a su cuenta");
		permiso3.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso3);
		permisoDao.save(permiso3);
		
		Permiso permiso4 = new Permiso();
		permiso4.setNombrePermiso("Agregar Síntomas");
		permiso4.setDescPermiso("Permite al usuario agregar un síntoma");
		permiso4.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso4);
		permisoDao.save(permiso4);
		
		Permiso permiso5 = new Permiso();
		permiso5.setNombrePermiso("Eliminar Síntomas");
		permiso5.setDescPermiso("Permite al usuario eliminar un síntoma relacionado a su cuenta");
		permiso5.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso5);
		permisoDao.save(permiso5);
		
		Permiso permiso6 = new Permiso();
		permiso6.setNombrePermiso("Agregar Recordatorios");
		permiso6.setDescPermiso("Permite al usuario agregar un recordatorio");
		permiso6.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso6);
		permisoDao.save(permiso6);
		
		
		Permiso permiso7 = new Permiso();
		permiso7.setNombrePermiso("Modificar Recordatorios");
		permiso7.setDescPermiso("Permite al usuario modificar un recordatorio existente");
		permiso7.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso7);
		permisoDao.save(permiso7);
		
		Permiso permiso8 = new Permiso();
		permiso8.setNombrePermiso("Eliminar Recordatorios");
		permiso8.setDescPermiso("Permite al usuario eliminar un recordatorio relacionado a su cuenta");
		permiso8.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso8);
		permisoDao.save(permiso8);
		
		Permiso permiso9 = new Permiso();
		permiso9.setNombrePermiso("Agregar Usuario");
		permiso9.setDescPermiso("Permite al usuario crear usuarios");
		permiso9.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso9);
		permisoDao.save(permiso9);
		
		Permiso permiso10 = new Permiso();
		permiso10.setNombrePermiso("Modificar Usuario");
		permiso10.setDescPermiso("Permite al usuario modificar usuarios existentes");
		permiso10.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso10);
		permisoDao.save(permiso10);
		
		Permiso permiso11 = new Permiso();
		permiso11.setNombrePermiso("Eliminar Usuario");
		permiso11.setDescPermiso("Permite al usuario eliminar usuarios existentes");
		permiso11.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso11);
		permisoDao.save(permiso11);
		
		Permiso permiso12 = new Permiso();
		permiso12.setNombrePermiso("Agregar Mediciones");
		permiso12.setDescPermiso("Permite al usuario agregar una medición");
		permiso12.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso12);
		permisoDao.save(permiso12);
		
		Permiso permiso13 = new Permiso();
		permiso13.setNombrePermiso("Modificar Mediciones");
		permiso13.setDescPermiso("Permite al usuario modificar una medición existente");
		permiso13.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso13);
		permisoDao.save(permiso13);
		
		Permiso permiso14 = new Permiso();
		permiso14.setNombrePermiso("Eliminar Mediciones");
		permiso14.setDescPermiso("Permite al usuario eliminar una medición asociada a su cuenta");
		permiso14.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso14);
		permisoDao.save(permiso14);
		
		Permiso permiso15 = new Permiso();
		permiso15.setNombrePermiso("Generar reportes");
		permiso15.setDescPermiso("Permite al usuario generar reportes sobre un calendario");
		permiso15.setFechaAltaPermiso(fechaHoy);
		permisosTotales.add(permiso15);
		permisoDao.save(permiso15);
		
		
		Perfil perfil = new Perfil();
		
		perfil.setDescPerfil("Usuarios que utilizan la app desde sus celulares");
		perfil.setFechaAltaPerfil(fechaHoy);
		perfil.setFechaFinVigenciaP(null);
		perfil.setMotivoFinVigenciaP(null);
		perfil.setNombrePerfil("Usuario Particular");
		perfilDao.save(perfil);
		for(int i=0; i<15; i++) {
			if(i!=9) {
				PerfilPermiso perfilPermiso = new PerfilPermiso();
				perfilPermiso.setPermiso(permisosTotales.get(i));
				perfilPermiso.setPerfil(perfil);
				perfilPermisoDao.save(perfilPermiso);
			}
		}
		
	
		
		
		
		Perfil perfil2 = new Perfil();
		
		perfil2.setDescPerfil("Usuario administrador del sistema");
		perfil2.setFechaAltaPerfil(fechaHoy);
		perfil2.setFechaFinVigenciaP(null);
		perfil2.setMotivoFinVigenciaP(null);
		perfil2.setNombrePerfil("Administrador del Sistema");
		perfilDao.save(perfil2);
		for(int i=0; i<15; i++) {
				PerfilPermiso perfilPermiso = new PerfilPermiso();
				perfilPermiso.setPermiso(permisosTotales.get(i));
				perfilPermiso.setPerfil(perfil2);
				perfilPermisoDao.save(perfilPermiso);
		}
		
		
		
		Perfil perfil3 = new Perfil();
		
		perfil3.setDescPerfil("Usuarios administradores de instituciones de salud");
		perfil3.setFechaAltaPerfil(fechaHoy);
		perfil3.setFechaFinVigenciaP(null);
		perfil3.setMotivoFinVigenciaP(null);
		perfil3.setNombrePerfil("Administrador de Institución");
		perfilDao.save(perfil3);
		for(int i=0; i<15; i++) {
				PerfilPermiso perfilPermiso = new PerfilPermiso();
				perfilPermiso.setPermiso(permisosTotales.get(i));
				perfilPermiso.setPerfil(perfil3);
				perfilPermisoDao.save(perfilPermiso);
		}
		
		
		
		Perfil perfil4 = new Perfil();
		
		perfil4.setDescPerfil("Profesionales de la salud que trabajan en una institución");
		perfil4.setFechaAltaPerfil(fechaHoy);
		perfil4.setFechaFinVigenciaP(null);
		perfil4.setMotivoFinVigenciaP(null);
		perfil4.setNombrePerfil("Profesional de la salud");
		perfilDao.save(perfil4);
		for(int i=0; i<15; i++) {
			if(i<8 || i>10) {
				PerfilPermiso perfilPermiso = new PerfilPermiso();
				perfilPermiso.setPermiso(permisosTotales.get(i));
				perfilPermiso.setPerfil(perfil4);
				perfilPermisoDao.save(perfilPermiso);
			}
		}
		
		
	}
	
	//@Test
		void getAllPerfilesAndDelete() {
			List<Perfil> perfiles = perfilDao.getAllPerfiles();
			for (Perfil perfil : perfiles) {
				perfilDao.delete(perfil);
			}
			
	}

}
