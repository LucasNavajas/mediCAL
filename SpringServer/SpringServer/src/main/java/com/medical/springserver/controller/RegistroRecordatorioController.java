package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.NotificationService;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorio;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class RegistroRecordatorioController {
	@Autowired
	NotificationService notificacion;
	
	@Autowired
	RegistroRecordatorioDao registroRecordatorioDao;
	
	@GetMapping("/registroRecordatorio/get-all")
	public List<RegistroRecordatorio> getAllRegistrosRecordatorio(){
		return registroRecordatorioDao.getAllRegistrosRecordatorio();
	}
	
	@PostMapping("registroRecordatorio/save")
	public RegistroRecordatorio save(@RequestBody RegistroRecordatorio registroRecordatorio) {
		return registroRecordatorioDao.save(registroRecordatorio);
	}
	
	@GetMapping("registroRecordatorio/calendario/{codCalendario}")
	public List<RegistroRecordatorio> obtenerRegistrosCalendario(@PathVariable int codCalendario){
		return registroRecordatorioDao.obtenerRegistrosCalendario(codCalendario);
	}
	
	@GetMapping("/registroRecordatorio/notificaciones")
	public List<RegistroRecordatorio> obtenerRegistrosActuales(){
		return registroRecordatorioDao.obtenerRegistrosActuales();
	}
	
	/*@PostMapping("/enviarNotificacion")
	public void enviarNotificacion() {
		notificacion.scheduleNotification("dIDtsB4aQN2JTyl8FKjEsZ:APA91bHudxSF6MjqmbQcNgHJ7dTxV7ze_2EPKqlUUr4NiEDc2A7sjMDKnsPk6WywzlEN7lC_R1EHn7Ng4xO3Cbj2idnx5yvuUDc4xJmEx05o6wH39_8IPbI5AfOdYL5seIGLRGWHjlZz", 
				"Notificación de Recordatorio", 
				"", 
				registroRecordatorioDao.obtenerRegistrosActuales().get(0));
	}*/
}
