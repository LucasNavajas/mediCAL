package com.medical.springserver;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorio;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorioDao;
import com.medical.springserver.model.solicitud.Solicitud;
import com.medical.springserver.model.solicitud.SolicitudDao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	@Autowired
	private RegistroRecordatorioDao registroDao;
	@Autowired
	private SolicitudDao solicitudDao;
    public void scheduleNotification(String token, String title, String body, RegistroRecordatorio registro) {
    	System.out.println("Se activo la funcion schedule notification");
    	body = "Tiene un recordatorio pendiente: "+ registro.getRecordatorio().getMedicamento().getNombreMedicamento() 
    			+ " del calendario "+ registro.getRecordatorio().getCalendario().getNombreCalendario();
    	 Notification notification = Notification
                 .builder()
                 .setTitle(title)
                 .setBody(body)
                 .build();
        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .putData("Authorization", "key=AAAACdR9ayg:APA91bHr0q58sCElsLKWv1iwXef6X_n4BVJ--KGqCBUJXfSXd2kNV6vYIEVuu8vIhmkB-qziYqE-65xhrFV654tZJpfmwExk9F2XVeFUyOCZ9SmAzMAF0qu_Hu27CBLWJk4i0HohbaEH")
                .putData("Content-Type", "application/json")
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            // Manejar errores
        }
    }
    //@Scheduled(cron = "0 * * * * *")
    public void enviarNotificaciones() {
    	System.out.println("Enviar Notificaciones activado");
    	List<RegistroRecordatorio> registrosActuales = registroDao.obtenerRegistrosActuales();
    	System.out.println("Registros actuales size:"+registrosActuales.size());
    	LocalDateTime now = LocalDateTime.now();
    	for (RegistroRecordatorio registro : registrosActuales) {
    		String tokenUsuario = registro.getRecordatorio().getCalendario().getUsuario().getToken().replace("\"", "");
    		String tokenSupervisor = "";
    		
    		// Obtén la fecha y hora de registro.getFechaTomaEsperada()
    		LocalDateTime fechaTomaEsperada = registro.getFechaTomaEsperada();
    		// Compara si la fecha y hora actual es igual a fechaTomaEsperada
    		boolean esIgual = now.isEqual(fechaTomaEsperada);

    		// Compara si la fecha y hora actual está dentro de los últimos 5 minutos de fechaTomaEsperada
    		boolean pasoHace5Minutos = fechaTomaEsperada.isBefore(now) && fechaTomaEsperada.plusMinutes(5).isEqual(now);
    		
    		// Compara si la fecha y hora actual está dentro de los últimos 15 minutos de fechaTomaEsperada
    		boolean pasoHace15Minutos = fechaTomaEsperada.isBefore(now) && fechaTomaEsperada.plusMinutes(15).isEqual(now);
    		
    		List<Solicitud> solicitudControlador = solicitudDao.obtenerSupervisor(registro.getRecordatorio().getCalendario().getUsuario().getCodUsuario());
    		if(solicitudControlador.size()>0) {
    			tokenSupervisor = solicitudControlador.get(0).getUsuarioControlador().getToken();
    		}
    		if(esIgual || pasoHace5Minutos || pasoHace15Minutos) {
    			scheduleNotification(tokenUsuario, "Notificación de Recordatorio", "", registro);
    			if(!tokenSupervisor.equals("")) {
    				scheduleNotification(tokenSupervisor, "Notificación de Recordatorio", "", registro);
    			}
    		}
    		
    		
    	}
    }
}
