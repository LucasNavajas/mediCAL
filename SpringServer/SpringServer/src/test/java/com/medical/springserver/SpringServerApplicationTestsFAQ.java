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
		
		FAQ faq = new FAQ();
		faq.setFechaActualizacionFAQ(fechaHoy);
		faq.setPreguntatFAQ("¿Presenta algun problema para registrar un recordatorio?");
		faq.setRespuestaFAQ("Para registrar un recordatorio debe realizar ...");
		
		faqDao.save(faq);
		
		
	}
	
	//@Test
	void getAllFAQAndDelete() {
		List<FAQ> faqs = faqDao.getAllFAQ();
		for (FAQ faq : faqs) {
			faqDao.delete(faq);
		}
		
	}

}
