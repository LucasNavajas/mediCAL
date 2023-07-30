package com.medical.springserver;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.faq.FAQ;
import com.medical.springserver.model.faq.FAQDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsFAQ {
	
	@Autowired
	private FAQDao faqDao;
	
	@Test
	void addFAQTest() {
		
		
		// Obtener la fecha de hoy
		LocalDate fechaHoy = LocalDate.now();
		
		FAQ faq1 = new FAQ();
		faq1.setFechaActualizacionFAQ(fechaHoy);
		faq1.setPreguntatFAQ("¿Cómo protege Google mi privacidad y mantiene mi información segura?");
		faq1.setRespuestaFAQ("La seguridad de su información y la garantía de que podrá acceder a ella cuando lo necesite son nuestras prioridades.");
		faqDao.save(faq1);
		
		FAQ faq2 = new FAQ();
		faq2.setFechaActualizacionFAQ(fechaHoy);
		faq2.setPreguntatFAQ("¿Cómo puedo eliminar información acerca de mí de los resultados de búsqueda de Google?");
		faq2.setRespuestaFAQ("Los resultados de búsqueda de Google reflejan el contenido disponible públicamente en la Web. Los motores de búsqueda no pueden eliminar contenido de la Web directamente.");
		faqDao.save(faq2);
		
		FAQ faq3 = new FAQ();
		faq3.setFechaActualizacionFAQ(fechaHoy);
		faq3.setPreguntatFAQ("¿Mis consultas de búsqueda se envían a los sitios web cuando hago clic en los resultados de Búsqueda de Google?");
		faq3.setRespuestaFAQ("En algunos casos, sí. Cuando hace clic en un resultado de la Búsqueda de Google, el navegador web también puede enviar la dirección de Internet (URL) de la página de los resultados de la búsqueda a la página web de destino como la URL de referencia.");
		
		faqDao.save(faq3);
		
		
	}
	
	//@Test
	void getAllFAQAndDelete() {
		List<FAQ> faqs = faqDao.getAllFAQ();
		for (FAQ faq : faqs) {
			faqDao.delete(faq);
		}
		
	}

}
