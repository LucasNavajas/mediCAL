package com.medical.springserver;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.administracionmed.AdministracionMed;
import com.medical.springserver.model.administracionmed.AdministracionMedDao;
import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.historialfinvigencia.HistorialFinVigencia;
import com.medical.springserver.model.presentacionMed.PresentacionMed;
import com.medical.springserver.model.presentacionMed.PresentacionMedDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
public class SpringServerApplicationTestsPresentacionMed {
	
	@Autowired
	private PresentacionMedDao presentacionMedDao;
	
	@Autowired
	private AdministracionMedDao adminMedDao;
	
	@Test
	void addPresentacionMed() {
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		
		//instancia de administracion med "De forma oral" //
		AdministracionMed administracionmedoral = new AdministracionMed();
		administracionmedoral.setDescAdministracionMed("El medicamento es introducido en el organismo a través de la boca, donde es deglutido, pasa al estómago y al intestino, donde es absorbido y desde donde ejerce su acción terapéutica.");
		administracionmedoral.setFechaAltaAdministracionMed(fechaHoy);
		administracionmedoral.setFechaFinVigenciaAM(null);
		administracionmedoral.setNombreAdministracionMed("De forma oral");
			//presentacion "De forma oral" - "Pastilla"//
			PresentacionMed presentacionMedoral1 = new PresentacionMed();
			presentacionMedoral1.setDescPresentacionMed("Porción pequeña de sustancia medicinal. Es la forma coloquial de referirse al comprimido");
			presentacionMedoral1.setNombrePresentacionMed("Pastilla");
			presentacionMedoral1.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedoral1.setFechaFinVigenciaPM(null);
			presentacionMedoral1.setVaradministracionmed(administracionmedoral);
			//presentacion "De forma oral" - "Comprimido"//
			PresentacionMed presentacionMedoral2 = new PresentacionMed();
			presentacionMedoral2.setDescPresentacionMed("Es una mezcla del principio activo (medicamento) y una serie de sustancias que permiten que no se modifique su forma ni sus características con el tiempo.");
			presentacionMedoral2.setNombrePresentacionMed("Comprimido");
			presentacionMedoral2.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedoral2.setFechaFinVigenciaPM(null);
			presentacionMedoral2.setVaradministracionmed(administracionmedoral);
			//presentacion "De forma oral" - "Cápsula"//
			PresentacionMed presentacionMedoral3 = new PresentacionMed();
			presentacionMedoral3.setDescPresentacionMed("Se trata de un envoltorio inerte de gelatina, que consta de dos partes, llamadas cuerpo y tapa, que se encastran a la perfección. A diferencia de los comprimidos, el contenido en su interior no está comprimido sino suelto.");
			presentacionMedoral3.setNombrePresentacionMed("Cápsula");
			presentacionMedoral3.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedoral3.setFechaFinVigenciaPM(null);
			presentacionMedoral3.setVaradministracionmed(administracionmedoral);
			//presentacion "De forma oral" - "Jarabe"//
			PresentacionMed presentacionMedoral4 = new PresentacionMed();
			presentacionMedoral4.setDescPresentacionMed("Medicamento en forma de líquido espeso");
			presentacionMedoral4.setNombrePresentacionMed("Jarabe");
			presentacionMedoral4.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedoral4.setFechaFinVigenciaPM(null);
			presentacionMedoral4.setVaradministracionmed(administracionmedoral);
			//presentacion "De forma oral" - "Solución(Líquido)"//
			PresentacionMed presentacionMedoral5 = new PresentacionMed();
			presentacionMedoral5.setDescPresentacionMed("Medicamento en forma de líquido de consistencia acuosa");
			presentacionMedoral5.setNombrePresentacionMed("Solución(Líquido)");
			presentacionMedoral5.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedoral5.setFechaFinVigenciaPM(null);
			presentacionMedoral5.setVaradministracionmed(administracionmedoral);
			//presentacion "De forma oral" - "Gotas"//
			PresentacionMed presentacionMedoral6 = new PresentacionMed();
			presentacionMedoral6.setDescPresentacionMed("Las gotas orales tienen mayor concentracion del medicamento respecto a la solucion donde es menor.");
			presentacionMedoral6.setNombrePresentacionMed("Gotas");
			presentacionMedoral6.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedoral6.setFechaFinVigenciaPM(null);
			presentacionMedoral6.setVaradministracionmed(administracionmedoral);
			//presentacion "De forma oral" - "Polvo"//
			PresentacionMed presentacionMedoral7 = new PresentacionMed();
			presentacionMedoral7.setDescPresentacionMed("Forma farmacéutica cuyos componentes están pulverizados y que se presentan dosificados o no, puros o mezclados, con o sin adición de excipientes.");
			presentacionMedoral7.setNombrePresentacionMed("Polvo");
			presentacionMedoral7.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedoral7.setFechaFinVigenciaPM(null);
			presentacionMedoral7.setVaradministracionmed(administracionmedoral);
			//presentacion "De forma oral" - "Chicle"//
			PresentacionMed presentacionMedoral8 = new PresentacionMed();
			presentacionMedoral8.setDescPresentacionMed("Forma de dosificación oral que puede liberar un principio activo o ingrediente funcional gracias a la acción mecánica de masticar.");
			presentacionMedoral8.setNombrePresentacionMed("Chicle");
			presentacionMedoral8.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedoral8.setFechaFinVigenciaPM(null);
			presentacionMedoral8.setVaradministracionmed(administracionmedoral);
			//presentacion "De forma oral" - "Espray"//
			PresentacionMed presentacionMedoral9 = new PresentacionMed();
			presentacionMedoral9.setDescPresentacionMed("Son dispersiones de partículas muy finas de un líquido o de un sólido en el aire o en un gas");
			presentacionMedoral9.setNombrePresentacionMed("Espray");
			presentacionMedoral9.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedoral9.setFechaFinVigenciaPM(null);
			presentacionMedoral9.setVaradministracionmed(administracionmedoral);
			
		//instancia de administracion med "Por la naríz / los ojos / los oídos" //
		AdministracionMed administracionmednoo = new AdministracionMed();
		administracionmednoo.setDescAdministracionMed("El medicamento es introducido en el organismo a través de la naríz, ojos u oídos");
		administracionmednoo.setFechaAltaAdministracionMed(fechaHoy);
		administracionmednoo.setFechaFinVigenciaAM(null);
		administracionmednoo.setNombreAdministracionMed("Por la naríz / los ojos / los oídos");	
			//presentacion "Por la naríz / los ojos / los oídos" - "Gotas"//
			PresentacionMed presentacionMednoo1 = new PresentacionMed();
			presentacionMednoo1.setDescPresentacionMed("Soluciones líquidas que se aplican directamente en estas áreas para proporcionar alivio o tratamiento en caso de diversas afecciones.");
			presentacionMednoo1.setNombrePresentacionMed("Gotas");
			presentacionMednoo1.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMednoo1.setFechaFinVigenciaPM(null);
			presentacionMednoo1.setVaradministracionmed(administracionmednoo);
			//presentacion "Por la naríz / los ojos / los oídos" - "Espray"//
			PresentacionMed presentacionMednoo2 = new PresentacionMed();
			presentacionMednoo2.setDescPresentacionMed("Se administran en forma líquida pulverizada utilizando un dispositivo de pulverización, generalmente una botella que contiene una solución líquida o suspensión finamente atomizada.");
			presentacionMednoo2.setNombrePresentacionMed("Espray");
			presentacionMednoo2.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMednoo2.setFechaFinVigenciaPM(null);
			presentacionMednoo2.setVaradministracionmed(administracionmednoo);
			//presentacion "Por la naríz / los ojos / los oídos" - "Crema"//
			PresentacionMed presentacionMednoo3 = new PresentacionMed();
			presentacionMednoo3.setDescPresentacionMed("Productos tópicos que se aplican en estas áreas del cuerpo para tratar afecciones específicas. Estas cremas están diseñadas para ser aplicadas en la piel alrededor de la nariz, los oídos o los ojos");
			presentacionMednoo3.setNombrePresentacionMed("Crema");
			presentacionMednoo3.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMednoo3.setFechaFinVigenciaPM(null);
			presentacionMednoo3.setVaradministracionmed(administracionmednoo);
			//presentacion "Por la naríz / los ojos / los oídos" - "Solución(Líquido)"//
			PresentacionMed presentacionMednoo4 = new PresentacionMed();
			presentacionMednoo4.setDescPresentacionMed("Medicamento en forma de líquido de consistencia acuosa para aplicar en ojos, naríz u oídos");
			presentacionMednoo4.setNombrePresentacionMed("Solución(Líquido)");
			presentacionMednoo4.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMednoo4.setFechaFinVigenciaPM(null);
			presentacionMednoo4.setVaradministracionmed(administracionmednoo);
			//presentacion "Por la naríz / los ojos / los oídos" - "Inhalador"//
			PresentacionMed presentacionMednoo5 = new PresentacionMed();
			presentacionMednoo5.setDescPresentacionMed("Dispositivo médico utilizado para suministrar un medicamento en forma de partículas de polvo al organismo a través de los pulmones, y de aquí a los tejidos blandos.");
			presentacionMednoo5.setNombrePresentacionMed("Inhalador");
			presentacionMednoo5.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMednoo5.setFechaFinVigenciaPM(null);
			presentacionMednoo5.setVaradministracionmed(administracionmednoo);
			
		//instancia de administracion med "De forma inyectable" //
		AdministracionMed administracionmedinyectable = new AdministracionMed();
		administracionmedinyectable.setDescAdministracionMed("Consiste en la introducción de sustancias medicamentosas en el tejido muscular, usada principalmente en aquellos casos en que se quiere una mayor rapidez, pero no puede ser administrado por la vía venosa, como por ejemplo, las sustancias liposolubles.");
		administracionmedinyectable.setFechaAltaAdministracionMed(fechaHoy);
		administracionmedinyectable.setFechaFinVigenciaAM(null);
		administracionmedinyectable.setNombreAdministracionMed("De forma inyectable");	
			//presentacion "De forma inyectable" - "Inyección"//
			PresentacionMed presentacionMedi1 = new PresentacionMed();
			presentacionMedi1.setDescPresentacionMed("Introducción de medicamento o productos biológicos al sitio de acción mediante la punción a presión en diferentes tejidos corporales a través de una jeringa y una aguja hipodérmica o de inyección.");
			presentacionMedi1.setNombrePresentacionMed("Inyección");
			presentacionMedi1.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedi1.setFechaFinVigenciaPM(null);
			presentacionMedi1.setVaradministracionmed(administracionmedinyectable);
			//presentacion "De forma inyectable" - "Ampolla"//
			PresentacionMed presentacionMedi2 = new PresentacionMed();
			presentacionMedi2.setDescPresentacionMed("Las ampollas contienen dosis unitarias de medicamento inyectable en forma líquida y están disponibles en diferentes dosis. Las ampollas se fabrican con vidrio y presentan un cuello en embudo prelijado, que debe romperse para obtener el medicamento.");
			presentacionMedi2.setNombrePresentacionMed("Ampolla");
			presentacionMedi2.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedi2.setFechaFinVigenciaPM(null);
			presentacionMedi2.setVaradministracionmed(administracionmedinyectable);	
			//presentacion "De forma inyectable" - "Suero"//
			PresentacionMed presentacionMedi3 = new PresentacionMed();
			presentacionMedi3.setDescPresentacionMed("Un suero es la disolución de sales u otras sustancias en agua, que se inyecta con fin curativo.");
			presentacionMedi3.setNombrePresentacionMed("Suero");
			presentacionMedi3.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedi3.setFechaFinVigenciaPM(null);
			presentacionMedi3.setVaradministracionmed(administracionmedinyectable);	
			//presentacion "De forma inyectable" - "Solución (Líquido)"//
			PresentacionMed presentacionMedi4 = new PresentacionMed();
			presentacionMedi4.setDescPresentacionMed("Una solución inyectable es una forma farmacéutica que se presenta en forma líquida y está diseñada para ser administrada mediante una inyección, ya sea intramuscular, intravenosa, subcutánea u otra vía.");
			presentacionMedi4.setNombrePresentacionMed("Solución (Líquido)");
			presentacionMedi4.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedi4.setFechaFinVigenciaPM(null);
			presentacionMedi4.setVaradministracionmed(administracionmedinyectable);	
		
		//instancia de administracion med "De manera tópica (sobre la piel)" //
		AdministracionMed administracionmedtop = new AdministracionMed();
		administracionmedtop.setDescAdministracionMed("Se refiere a la aplicación de medicamentos, cremas, ungüentos o soluciones directamente sobre la superficie de la piel. Esta vía de administración permite que el medicamento actúe localmente en la piel o en las áreas cercanas al lugar de aplicación.");
		administracionmedtop.setFechaAltaAdministracionMed(fechaHoy);
		administracionmedtop.setFechaFinVigenciaAM(null);
		administracionmedtop.setNombreAdministracionMed("De manera tópica (sobre la piel)");	
			//presentacion "De manera tópica (sobre la piel)" - "Crema" //
			PresentacionMed presentacionMedt1 = new PresentacionMed();
			presentacionMedt1.setDescPresentacionMed("Se presenta en forma de crema y se aplica directamente sobre la piel. Estas cremas están diseñadas para ser absorbidas a través de la piel y actuar localmente en el área de aplicación.");
			presentacionMedt1.setNombrePresentacionMed("Crema");
			presentacionMedt1.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedt1.setFechaFinVigenciaPM(null);
			presentacionMedt1.setVaradministracionmed(administracionmedtop);
			//presentacion "De manera tópica (sobre la piel)" - "Champu" //
			PresentacionMed presentacionMedt2 = new PresentacionMed();
			presentacionMedt2.setDescPresentacionMed("Se presenta en forma de champú y se aplica sobre el cabello y el cuero cabelludo.");
			presentacionMedt2.setNombrePresentacionMed("Champú");
			presentacionMedt2.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedt2.setFechaFinVigenciaPM(null);
			presentacionMedt2.setVaradministracionmed(administracionmedtop);
			//presentacion "De manera tópica (sobre la piel)" - "Solucion (Liquido)" //
			PresentacionMed presentacionMedt3 = new PresentacionMed();
			presentacionMedt3.setDescPresentacionMed("Se presenta en forma de solución líquida y se aplica directamente sobre la piel o la zona afectada.");
			presentacionMedt3.setNombrePresentacionMed("Solución (Líquido)");
			presentacionMedt3.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedt3.setFechaFinVigenciaPM(null);
			presentacionMedt3.setVaradministracionmed(administracionmedtop);
			//presentacion "De manera tópica (sobre la piel)" - "Parche"//
			PresentacionMed presentacionMedt4 = new PresentacionMed();
			presentacionMedt4.setDescPresentacionMed("Se presenta en forma de parche adhesivo que se aplica sobre la piel para liberar el medicamento gradualmente.");
			presentacionMedt4.setNombrePresentacionMed("Parche");
			presentacionMedt4.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedt4.setFechaFinVigenciaPM(null);
			presentacionMedt4.setVaradministracionmed(administracionmedtop);
			//presentacion "De manera tópica (sobre la piel)" - "Aposito" //
			PresentacionMed presentacionMedt5 = new PresentacionMed();
			presentacionMedt5.setDescPresentacionMed("Se presenta en forma de apósito que se coloca sobre la piel para tratar heridas o afecciones.");
			presentacionMedt5.setNombrePresentacionMed("Apósito");
			presentacionMedt5.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedt5.setFechaFinVigenciaPM(null);
			presentacionMedt5.setVaradministracionmed(administracionmedtop);
			//presentacion "De manera tópica (sobre la piel)" - "Laca" //
			PresentacionMed presentacionMedt6 = new PresentacionMed();
			presentacionMedt6.setDescPresentacionMed("Se presenta en forma de laca y se aplica sobre la piel para fijar y proteger.");
			presentacionMedt6.setNombrePresentacionMed("Laca");
			presentacionMedt6.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedt6.setFechaFinVigenciaPM(null);
			presentacionMedt6.setVaradministracionmed(administracionmedtop);
			//presentacion "De manera tópica (sobre la piel)" - "Espuma" //
			PresentacionMed presentacionMedt7 = new PresentacionMed();
			presentacionMedt7.setDescPresentacionMed("Se presenta en forma de espuma y se aplica sobre la piel para tratar diversas afecciones.");
			presentacionMedt7.setNombrePresentacionMed("Espuma");
			presentacionMedt7.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedt7.setFechaFinVigenciaPM(null);
			presentacionMedt7.setVaradministracionmed(administracionmedtop);
			//presentacion "De manera tópica (sobre la piel)" - "Espray" //
			PresentacionMed presentacionMedt8 = new PresentacionMed();
			presentacionMedt8.setDescPresentacionMed("Se presenta en forma de espray y se aplica sobre la piel para un efecto rápido.");
			presentacionMedt8.setNombrePresentacionMed("Espray");
			presentacionMedt8.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedt8.setFechaFinVigenciaPM(null);
			presentacionMedt8.setVaradministracionmed(administracionmedtop);
			//presentacion "De manera tópica (sobre la piel)" - "Polvo" //
			PresentacionMed presentacionMedt9 = new PresentacionMed();
			presentacionMedt9.setDescPresentacionMed("Se presenta en forma de polvo y se aplica sobre la piel para tratar afecciones específicas.");
			presentacionMedt9.setNombrePresentacionMed("Polvo");
			presentacionMedt9.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedt9.setFechaFinVigenciaPM(null);
			presentacionMedt9.setVaradministracionmed(administracionmedtop);
			//presentacion "De manera tópica (sobre la piel)" - "Barra" //
			PresentacionMed presentacionMedt10 = new PresentacionMed();
			presentacionMedt10.setDescPresentacionMed("Se presenta en forma de barra y se aplica sobre la piel para tratar afecciones específicas.");
			presentacionMedt10.setNombrePresentacionMed("Barra");
			presentacionMedt10.setFechaAltaPresentacionMed(LocalDate.now());
			presentacionMedt10.setFechaFinVigenciaPM(null);
			presentacionMedt10.setVaradministracionmed(administracionmedtop);

			
		//guardar administracion oral//
		adminMedDao.save(administracionmedoral);
		//guardar presentciones orales//
		presentacionMedDao.save(presentacionMedoral1);
		presentacionMedDao.save(presentacionMedoral2);
		presentacionMedDao.save(presentacionMedoral3);
		presentacionMedDao.save(presentacionMedoral4);
		presentacionMedDao.save(presentacionMedoral5);
		presentacionMedDao.save(presentacionMedoral6);
		presentacionMedDao.save(presentacionMedoral7);
		presentacionMedDao.save(presentacionMedoral8);
		presentacionMedDao.save(presentacionMedoral9);
		
		//guardar administracion nariz ojos oidos//
		adminMedDao.save(administracionmednoo);
		//guardar presentciones nariz ojos oidos//
		presentacionMedDao.save(presentacionMednoo1);
		presentacionMedDao.save(presentacionMednoo2);
		presentacionMedDao.save(presentacionMednoo3);
		presentacionMedDao.save(presentacionMednoo4);
		presentacionMedDao.save(presentacionMednoo5);
		
		//guardar administracion inyectable//
		adminMedDao.save(administracionmedinyectable);
		//guardar presentciones inyectable//
		presentacionMedDao.save(presentacionMedi1);
		presentacionMedDao.save(presentacionMedi2);
		presentacionMedDao.save(presentacionMedi3);
		presentacionMedDao.save(presentacionMedi4);
		
		//guardar administracion inyectable//
		adminMedDao.save(administracionmedtop);
		//guardar presentciones inyectable//
		presentacionMedDao.save(presentacionMedt1);
		presentacionMedDao.save(presentacionMedt2);
		presentacionMedDao.save(presentacionMedt3);
		presentacionMedDao.save(presentacionMedt4);
		presentacionMedDao.save(presentacionMedt5);
		presentacionMedDao.save(presentacionMedt6);
		presentacionMedDao.save(presentacionMedt7);
		presentacionMedDao.save(presentacionMedt8);
		presentacionMedDao.save(presentacionMedt9);
		presentacionMedDao.save(presentacionMedt10);
		
	}
	
	//@Test
	void getAllPresentacionesMedAndDelete() {
		List<PresentacionMed> presentacionesMed = presentacionMedDao.getAllPresentacionesMed();
		for (PresentacionMed presentacionMed : presentacionesMed) {
			presentacionMedDao.delete(presentacionMed);
		}
	}

}
