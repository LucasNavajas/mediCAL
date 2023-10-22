package com.medical.springserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.consejo.Consejo;
import com.medical.springserver.model.consejo.ConsejoDao;
import com.medical.springserver.model.tipoconsejo.TipoConsejo;
import com.medical.springserver.model.tipoconsejo.TipoConsejoDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsTipoConsejo {
	
	@Autowired
	private TipoConsejoDao tipoConsejoDao;
	@Autowired
	private ConsejoDao consejoDao;
	
	@Test
	void addTipoConsejoTest() {
		
		TipoConsejo tipoConsejo = new TipoConsejo();
		tipoConsejo.setNombreTipoConsejo("Medico"); // consejos médicos respaldados por fuentes (link a video)
		tipoConsejoDao.save(tipoConsejo);
		
		TipoConsejo tipoConsejo2 = new TipoConsejo();
		tipoConsejo2.setNombreTipoConsejo("Binestar y Salud"); // consejos normales sobre bienestar (link a noticia)
		tipoConsejoDao.save(tipoConsejo2);
		
		TipoConsejo tipoConsejo3 = new TipoConsejo();
		tipoConsejo3.setNombreTipoConsejo("Sobre la App"); // consejos sobre el uso de la app (link a manual de usuario)
		tipoConsejoDao.save(tipoConsejo3);
		
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		// Inicializar Lista de Likeados y agregar primer valor "0"
		// Los números serán agregados junto con una coma de separación, SIN espacios
		String listaLikeados = "0";
		
		Consejo consejo = new Consejo();
		consejo.setNombreConsejo("Su consejo diario de MediCAL!");
		// Colocar luego link a Manual de Usuario
		consejo.setLinkConsejo("null");
		consejo.setFechaAltaConsejo(fechaHoy);
		consejo.setAuspiciante("MediCAL");
		consejo.setDescConsejo("¡Es fácil añadir un contacto a supervisar! " + " Acceda al menú desplegable, seleccione “Añadir contacto” y busque por el nombre de usuario. ");
		consejo.setTipoConsejo(tipoConsejo3);
		consejo.setCantLikes(100);
		consejo.setListaLikeados(listaLikeados);
		consejoDao.save(consejo);
		
		
		Consejo consejo1 = new Consejo();
		consejo1.setNombreConsejo("Consejo Diario de Bienestar y Salud");;
		// Link a noticia relacionada: 
		consejo1.setLinkConsejo("https://www.nhlbi.nih.gov/es/salud/sueno/por-que-el-sueno-es-importante");
		consejo1.setFechaAltaConsejo(fechaHoy);
		consejo1.setDescConsejo("Dormir bien es muy importante. No dormir lo suficiente puede afectar tus hormonas, así como tu salud física y mental.");
		consejo1.setTipoConsejo(tipoConsejo2);
		consejo1.setCantLikes(150);
		consejo1.setListaLikeados(listaLikeados);
		consejoDao.save(consejo1);		
		
		Consejo consejo2 = new Consejo();
		consejo2.setNombreConsejo("Consejo Médico Auspiciado");
		// Link a video relacionado:
		consejo2.setLinkConsejo("https://www.youtube.com/watch?v=a2EuM2w4LrI");
		consejo2.setFechaAltaConsejo(fechaHoy);
		consejo2.setAuspiciante("Bagó");
		consejo2.setDescConsejo("7 Cuidados básicos para personas con hipertensión.");
		consejo2.setFotoConsejo("https://img.youtube.com/vi/a2EuM2w4LrI/maxresdefault.jpg");
		consejo2.setTipoConsejo(tipoConsejo);
		consejo2.setCantLikes(200);
		consejo2.setListaLikeados(listaLikeados);
		consejoDao.save(consejo2);
		
		Consejo consejo3 = new Consejo();
		consejo3.setNombreConsejo("Su consejo diario de MediCAL!");
		// Colocar luego link a Manual de Usuario
		consejo3.setLinkConsejo("null");
		consejo3.setFechaAltaConsejo(fechaHoy);
		consejo3.setAuspiciante("MediCAL");
		consejo3.setDescConsejo("¡Es fácil añadir un síntoma nuevo! " + " Pulse en “Más” de la barra inferior de herramientas, seleccione “Mediciones y Síntomas”, luego Agregue un Seguimiento sobre Síntomas y seleccione entre las opciones aquellos síntomas que desee guardar. ");
		consejo3.setTipoConsejo(tipoConsejo3);
		consejo3.setCantLikes(125);
		consejo3.setListaLikeados(listaLikeados);
		consejoDao.save(consejo3);
		
		
		Consejo consejo4 = new Consejo();
		consejo4.setNombreConsejo("Consejo Diario de Bienestar y Salud");;
		// Link a noticia relacionada: 
		consejo4.setLinkConsejo("https://www.mayoclinic.org/es/healthy-lifestyle/nutrition-and-healthy-eating/in-depth/water/art-20044256#:~:text=%C2%BFCu%C3%A1les%20son%20los%20beneficios%20para,necesita%20agua%20para%20funcionar%20correctamente.");
		consejo4.setFechaAltaConsejo(fechaHoy);
		consejo4.setDescConsejo("El agua es esencial para una buena salud. ¿Estás consumiendo lo suficiente? Estas pautas pueden ayudarte a averiguarlo.");
		consejo4.setTipoConsejo(tipoConsejo2);
		consejo4.setCantLikes(77);
		consejo4.setListaLikeados(listaLikeados);
		consejoDao.save(consejo4);
		
		Consejo consejo5 = new Consejo();
		consejo5.setNombreConsejo("Consejo Médico Auspiciado");
		// Link a video relacionado:
		consejo5.setLinkConsejo("https://youtu.be/TZh2EOK_Hms");
		consejo5.setFechaAltaConsejo(fechaHoy);
		consejo5.setAuspiciante("OSEP");
		consejo5.setDescConsejo("¡Taller de la Memoria! La doctora Paula Fachinelli, del Departamento de Gestión de Políticas Sociales para el Adulto Mayor de OSEP, nos brinda lo mejor del Taller de Estimulación Cognitiva");
		consejo5.setFotoConsejo("https://img.youtube.com/vi/TZh2EOK_Hms/maxresdefault.jpg");
		consejo5.setTipoConsejo(tipoConsejo);
		consejo5.setCantLikes(177);
		consejo5.setListaLikeados(listaLikeados);
		consejoDao.save(consejo5);
		
		//System.out.println(tipoConsejo.getVarconsejo());
		
	}
	
	//@Test
	void getAllTipoConsejosAndDelete() {
		List<TipoConsejo> tipoConsejos = tipoConsejoDao.getAllTipoConsejo();
		for (TipoConsejo tipoConsejo : tipoConsejos) {
			tipoConsejoDao.delete(tipoConsejo);
		}
		
	}

}