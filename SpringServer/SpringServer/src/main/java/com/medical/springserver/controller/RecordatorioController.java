package com.medical.springserver.controller;
import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.recordatorio.Recordatorio;
import com.medical.springserver.model.recordatorio.RecordatorioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class RecordatorioController {
	
	@Autowired
	RecordatorioDao recordatorioDao;
	
	@GetMapping("/recordatorio/get-all")
	public List<Recordatorio> getAllRecordatorios(){
		return recordatorioDao.getAllRecordatorios();
	}
	
	@PostMapping("/recordatorio/save")
	public Recordatorio save(@RequestBody Recordatorio recordatorio) {
		return recordatorioDao.save(recordatorio);
	}
	
	@GetMapping("/recordatorio/calendario/{codCalendario}")
	public List<Recordatorio> getByCodCalendario(@PathVariable int codCalendario){
		return recordatorioDao.findByCodCalendario(codCalendario);
	}
	
	@GetMapping("/recordatorio/{codRecordatorio}")
	public Recordatorio getByCodRecordatorio(@PathVariable int codRecordatorio) {
		return recordatorioDao.getByCodRecordatorio(codRecordatorio);
	}
	
	@DeleteMapping("/recordatorio/eliminar/{codRecordatorio}")
	public void eliminarRecordatorio(@PathVariable int codRecordatorio) {
		Recordatorio r = recordatorioDao.getByCodRecordatorio(codRecordatorio);
		recordatorioDao.delete(r);
	}
	
	@PostMapping("/recordatorio/modificar")
	public Recordatorio modificarRecordatorio(@RequestBody Recordatorio recordatorio) {
		return recordatorioDao.modificarRecordatorio(recordatorio.getCodRecordatorio(), recordatorio);
	}

}
