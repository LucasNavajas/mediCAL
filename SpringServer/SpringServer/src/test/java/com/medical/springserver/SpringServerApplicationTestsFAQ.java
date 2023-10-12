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
		faq1.setPreguntatFAQ("¿Cómo puedo crear una cuenta en la aplicación MediCAL?");
		faq1.setRespuestaFAQ("Para crear una cuenta en MediCAL, abre la aplicación móvil y selecciona el botón \"CREAR CUENTA\" en la pantalla de inicio. Asegúrate de aceptar los Términos y Condiciones de Uso y la Política de Privacidad, y proporciona la información requerida, incluyendo un nombre de usuario único, una contraseña y un correo electrónico válido. Una vez validada la cuenta, podrás comenzar a utilizar la aplicación.");
		faqDao.save(faq1);
		
		FAQ faq2 = new FAQ();
		faq2.setFechaActualizacionFAQ(fechaHoy);
		faq2.setPreguntatFAQ("¿Qué debo hacer si olvidé mi contraseña para iniciar sesión en MediCAL?");
		faq2.setRespuestaFAQ("Si olvidaste tu contraseña, selecciona la opción \"¿Olvidaste tu contraseña?\" en la pantalla de inicio de sesión. Ingresa tu correo electrónico asociado a la cuenta y sigue las instrucciones para recibir un código de verificación. Luego, valida el código recibido y establece una nueva contraseña para acceder a tu cuenta.");
		faqDao.save(faq2);
		
		FAQ faq3 = new FAQ();
		faq3.setFechaActualizacionFAQ(fechaHoy);
		faq3.setPreguntatFAQ("¿Cómo puedo asociar un medicamento y establecer un recordatorio en la aplicación MediCAL?");
		faq3.setRespuestaFAQ("Para asociar un medicamento y establecer un recordatorio, selecciona el botón \"AGREGAR RECORDATORIO\" desde la pantalla principal. Sigue los pasos para vincular el medicamento, establecer la forma de administración, la frecuencia y el horario de la dosis. También puedes agregar instrucciones, imágenes y configurar recordatorios de recarga si es necesario. Una vez configurado, el recordatorio estará activo en tu calendario.");
		faqDao.save(faq3);
		
		FAQ faq4 = new FAQ();
		faq4.setFechaActualizacionFAQ(fechaHoy);
		faq4.setPreguntatFAQ("¿Puedo editar o eliminar un recordatorio una vez que lo he establecido en MediCAL?");
		faq4.setRespuestaFAQ("Sí, puedes editar o eliminar un recordatorio en cualquier momento. Ve a la sección de recordatorios, selecciona el recordatorio que deseas modificar y elige la opción correspondiente para realizar cambios. Si deseas eliminarlo, selecciona la opción de eliminar y confirma la acción. Recuerda que cualquier modificación se reflejará en tu calendario de MediCAL.");
		faqDao.save(faq4);
		
	}
	
	//@Test
	void getAllFAQAndDelete() {
		List<FAQ> faqs = faqDao.getAllFAQ();
		for (FAQ faq : faqs) {
			faqDao.delete(faq);
		}
		
	}

}
