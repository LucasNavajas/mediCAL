package com.medical.springserver.controller;
import com.medical.springserver.NotificationService;
import com.medical.springserver.model.calendariomedicion.CalendarioMedicion;
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
	
	
	@GetMapping("/registroRecordatorio/recordatorio/{codRecordatorio}")
	public List<RegistroRecordatorio> getByCodRecordatorio(@PathVariable int codRecordatorio){
		return registroRecordatorioDao.findByCodRecordatorio(codRecordatorio);
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
	
	@GetMapping("/registroRecordatorio/notificacion/{codCalendario}")
	public List<RegistroRecordatorio> obtenerRegistrosCalendarioNotificacion(@PathVariable int codCalendario){
		return registroRecordatorioDao.obtenerRegistrosCalendarioNotificacion(codCalendario);
	}
	
	@PutMapping("/registroRecordatorio/baja/{codRegistroRecordatorio}")
	public void bajaRegistro(@PathVariable int codRegistroRecordatorio) {
		registroRecordatorioDao.bajaRegistro(codRegistroRecordatorio);
	}
	
	@GetMapping("/registroRecordatorio/{codRegistroRecordatorio}")
	public RegistroRecordatorio getByCodRegistroRecordatorio(@PathVariable int codRegistroRecordatorio) {
		return registroRecordatorioDao.getByCodRegistroRecordatorio(codRegistroRecordatorio);
	}
	

	
}
