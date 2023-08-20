package com.medical.springserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;
import com.medical.springserver.model.presentacionMed.PresentacionMed;
import com.medical.springserver.model.sintoma.Sintoma;
import com.medical.springserver.model.sintoma.SintomaDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsSintoma {
	
	@Autowired
	private SintomaDao sintomaDao;
	
	@Test
	void addSintomaTest() {
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		Sintoma sintoma = new Sintoma();
		sintoma.setFechaAltaSintoma(fechaHoy);
		sintoma.setFechaFinVigenciaS(null);
		sintoma.setNombreSintoma("Tos");
		
		  Sintoma ansiedad = new Sintoma();
	        ansiedad.setFechaAltaSintoma(fechaHoy);
	        ansiedad.setFechaFinVigenciaS(null);
	        ansiedad.setNombreSintoma("Ansiedad");

	        Sintoma cansancio = new Sintoma();
	        cansancio.setFechaAltaSintoma(fechaHoy);
	        cansancio.setFechaFinVigenciaS(null);
	        cansancio.setNombreSintoma("Cansancio");

	        Sintoma estres = new Sintoma();
	        estres.setFechaAltaSintoma(fechaHoy);
	        estres.setFechaFinVigenciaS(null);
	        estres.setNombreSintoma("Estres");

	        Sintoma cambiosDeAnimo = new Sintoma();
	        cambiosDeAnimo.setFechaAltaSintoma(fechaHoy);
	        cambiosDeAnimo.setFechaFinVigenciaS(null);
	        cambiosDeAnimo.setNombreSintoma("Cambios de ánimo");

	        Sintoma triste = new Sintoma();
	        triste.setFechaAltaSintoma(fechaHoy);
	        triste.setFechaFinVigenciaS(null);
	        triste.setNombreSintoma("Triste");

	        Sintoma feliz = new Sintoma();
	        feliz.setFechaAltaSintoma(fechaHoy);
	        feliz.setFechaFinVigenciaS(null);
	        feliz.setNombreSintoma("Feliz");

	        Sintoma sinControl = new Sintoma();
	        sinControl.setFechaAltaSintoma(fechaHoy);
	        sinControl.setFechaFinVigenciaS(null);
	        sinControl.setNombreSintoma("Sin control");

	        Sintoma irritabilidad = new Sintoma();
	        irritabilidad.setFechaAltaSintoma(fechaHoy);
	        irritabilidad.setFechaFinVigenciaS(null);
	        irritabilidad.setNombreSintoma("Irritabilidad");

	        Sintoma gratitud = new Sintoma();
	        gratitud.setFechaAltaSintoma(fechaHoy);
	        gratitud.setFechaFinVigenciaS(null);
	        gratitud.setNombreSintoma("Gratitud");

	        Sintoma indiferencia = new Sintoma();
	        indiferencia.setFechaAltaSintoma(fechaHoy);
	        indiferencia.setFechaFinVigenciaS(null);
	        indiferencia.setNombreSintoma("Indiferencia");

	        Sintoma exhausta = new Sintoma();
	        exhausta.setFechaAltaSintoma(fechaHoy);
	        exhausta.setFechaFinVigenciaS(null);
	        exhausta.setNombreSintoma("Exhausta");

	        Sintoma vitalidad = new Sintoma();
	        vitalidad.setFechaAltaSintoma(fechaHoy);
	        vitalidad.setFechaFinVigenciaS(null);
	        vitalidad.setNombreSintoma("Vitalidad");

	        Sintoma rabia = new Sintoma();
	        rabia.setFechaAltaSintoma(fechaHoy);
	        rabia.setFechaFinVigenciaS(null);
	        rabia.setNombreSintoma("Rabia");

	        Sintoma entusiasmo = new Sintoma();
	        entusiasmo.setFechaAltaSintoma(fechaHoy);
	        entusiasmo.setFechaFinVigenciaS(null);
	        entusiasmo.setNombreSintoma("Entusiasmo");

	        Sintoma dificultadParaDormir = new Sintoma();
	        dificultadParaDormir.setFechaAltaSintoma(fechaHoy);
	        dificultadParaDormir.setFechaFinVigenciaS(null);
	        dificultadParaDormir.setNombreSintoma("Dificultad para dormir");

	        Sintoma cansancioAlDespertar = new Sintoma();
	        cansancioAlDespertar.setFechaAltaSintoma(fechaHoy);
	        cansancioAlDespertar.setFechaFinVigenciaS(null);
	        cansancioAlDespertar.setNombreSintoma("Cansancio al despertar");

	        Sintoma sudoracionNocturna = new Sintoma();
	        sudoracionNocturna.setFechaAltaSintoma(fechaHoy);
	        sudoracionNocturna.setFechaFinVigenciaS(null);
	        sudoracionNocturna.setNombreSintoma("Sudoración nocturna");

		
		
		// ¿Está bien que navegue desde Sintoma hacia CalendarioSintoma?
				
		// instancia de CalendarioSintoma
		CalendarioSintoma calendariosintoma = new CalendarioSintoma();
		calendariosintoma.setFechaCalendarioSintoma(fechaHoy);
		calendariosintoma.setFechaFinVigenciaCS(null);
		
		calendariosintoma.setSintoma(sintoma);
		List<CalendarioSintoma> varcalendariosintoma = new ArrayList<>();
		varcalendariosintoma.add(calendariosintoma);
		sintoma.setVarcalendariosintoma(varcalendariosintoma);
		
		
		
		System.out.println(sintoma.getVarcalendariosintoma());
		System.out.println(ansiedad.getVarcalendariosintoma());
		sintomaDao.save(sintoma);
		sintomaDao.save(ansiedad);
		sintomaDao.save(cansancio);
		sintomaDao.save(estres);
		sintomaDao.save(vitalidad);
		sintomaDao.save(cambiosDeAnimo);
		sintomaDao.save(entusiasmo);
		sintomaDao.save(feliz);
		sintomaDao.save(triste);
		sintomaDao.save(sudoracionNocturna);
		sintomaDao.save(dificultadParaDormir);
		sintomaDao.save(cansancioAlDespertar);
		sintomaDao.save(exhausta);
		sintomaDao.save(rabia);
		sintomaDao.save(irritabilidad);
		sintomaDao.save(gratitud);
		sintomaDao.save(indiferencia);
		
	}
	
	//@Test
	void getAllSintomasAndDelete() {
		List<Sintoma> sintomas = sintomaDao.getAllSintomas();
		for (Sintoma sintoma : sintomas) {
			sintomaDao.delete(sintoma);
		}
		
	}
	

}

