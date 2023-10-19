package com.medical.springserver.controller;
import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.calendario.CalendarioDao;
import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@RestController
public class CalendarioController {
	
	@Autowired
	CalendarioDao calendarioDao;
	
	@GetMapping("/calendario/get-all")
	public List<Calendario> getAllCalendarios(){
		return calendarioDao.getAllCalendarios();
	}
	
	@PostMapping("/calendario/save")
	public Calendario save(@RequestBody Calendario calendario) {
		return calendarioDao.save(calendario);
	}
	
	@GetMapping("/calendario/usuario/{codUsuario}")
	public List<Calendario> getByCodUsuario(@PathVariable int codUsuario){
		return calendarioDao.findByCodUsuario(codUsuario);
	}
	
	@GetMapping("/calendario/{codCalendario}")
	public Calendario getByCodCalendario(@PathVariable int codCalendario) {
		return calendarioDao.findByCodCalendario(codCalendario);
	}
	
	@PostMapping("/calendario/modificar/{codCalendario}")
	public Calendario modificarCalendario(@PathVariable int codCalendario, @RequestBody Calendario calendario) {
		return calendarioDao.modificarCalendario(codCalendario, calendario.getNombreCalendario(), calendario.getRelacionCalendario(), calendario.getNombrePaciente());
		//codCalendario, calendario.getFechaAltaCalendario(), calendario.getFechaFinVigenciaC(),
		//calendario.getNombreCalendario(), calendario.getRelacionCalendario(), calendario.getNombrePaciente());
	}
	
	@PostMapping("/calendario/eliminar/{codCalendario}")
	public void eliminarCalendario(@PathVariable int codCalendario) {
		calendarioDao.eliminarCalendario(codCalendario);
	}
	
	@PostMapping("/calendario/recuperar/{codCalendario}")
	public void recuperarCalendario(@PathVariable int codCalendario) {
		calendarioDao.recuperarCalendario(codCalendario);
	}
	
	@GetMapping("/calendario/get-all/{nombreInstitucion}")
	public List<Calendario> findByInstitucion(@PathVariable String nombreInstitucion){
		return calendarioDao.findByInstitucion(nombreInstitucion);
	}
		
	@GetMapping("/calendario/filtrados")
	public List<Calendario> obtenerCalendariosFiltrados(
	        @RequestParam(name = "codUsuario", required = false, defaultValue = "0") int codUsuario,
	        @RequestParam(name = "codMedicamento", required = false, defaultValue = "0") int codMedicamento,
	        @RequestParam(name = "codSintoma", required = false, defaultValue = "0") int codSintoma,
	        @RequestParam(name = "nombreInstitucion") String nombreInstitucion,
	        @RequestParam(name = "fechadesde", required = false) LocalDate fechaDesde,
	        @RequestParam(name = "fechahasta", required = false) LocalDate fechaHasta
	) {
        return calendarioDao.obtenerCalendariosFiltrados(codUsuario, codMedicamento, codSintoma, nombreInstitucion, fechaDesde, fechaHasta);
    }
	
	@GetMapping("/registroRecordatorio/filtrados")
	public List<RegistroRecordatorio> obtenerRegistrosFiltrados(@RequestParam(name = "codUsuario", required = false, defaultValue = "0") int codUsuario,
	        @RequestParam(name = "codMedicamento", required = false, defaultValue = "0") int codMedicamento,
	        @RequestParam(name = "nombreInstitucion") String nombreInstitucion,
	        @RequestParam(name = "fechadesde", required = false) LocalDate fechaDesde,
	        @RequestParam(name = "fechahasta", required = false) LocalDate fechaHasta
	)
	{
		return calendarioDao.obtenerRegistrosFiltrados(codUsuario, codMedicamento, nombreInstitucion, fechaDesde, fechaHasta);
	}
	
	@GetMapping("/calendarioSintoma/filtrados")
	public List<CalendarioSintoma> obtenerSintomasFiltrados(
	        @RequestParam(name = "nombreInstitucion") String nombreInstitucion,
	        @RequestParam(name = "fechadesde", required = false) LocalDate fechaDesde,
	        @RequestParam(name = "fechahasta", required = false) LocalDate fechaHasta
	)
	{
		return calendarioDao.obtenerSintomasFiltrados(nombreInstitucion, fechaDesde, fechaHasta);
	}
	

}
