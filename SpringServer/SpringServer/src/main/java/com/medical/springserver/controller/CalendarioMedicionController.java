package com.medical.springserver.controller;
import com.google.firebase.auth.FirebaseAuthException;
import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.calendariomedicion.CalendarioMedicion;
import com.medical.springserver.model.calendariomedicion.CalendarioMedicionDao;
import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;
import com.medical.springserver.model.usuario.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@RestController
public class CalendarioMedicionController {
	
	@Autowired
	CalendarioMedicionDao calendariomedicionDao;
	
	@PostMapping("/calendariomedicion/modificar")
	public ResponseEntity<CalendarioMedicion> modificarCalendarioMedicion(@RequestBody CalendarioMedicion calendariomedicion)  {
	    int codCalendarioMedicion = calendariomedicion.getCodCalendarioMedicion();
	    float nuevoValor = calendariomedicion.getValorCalendarioMedicion();

	    CalendarioMedicion modifiedCalendarioMedicion= calendariomedicionDao.modificarCalendarioMedicion(codCalendarioMedicion, nuevoValor);

	    if (modifiedCalendarioMedicion != null) {
	        return new ResponseEntity<>(modifiedCalendarioMedicion, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	

	@PostMapping("/calendariomedicion/eliminar")
	public ResponseEntity<CalendarioMedicion> eliminarCalendarioMedicion(@RequestBody CalendarioMedicion calendariomedicion) {
	    int codCalendarioMedicion = calendariomedicion.getCodCalendarioMedicion();

	    // Obtén el CalendarioMedicion actual
	    CalendarioMedicion calendarioMedicion = calendariomedicionDao.findByCodCalendarioMedicion(codCalendarioMedicion);

	    if (calendarioMedicion == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }

	    // Actualiza el atributo fechaFinVigenciaCM con la fecha actual
	    calendarioMedicion.setFechaFinVigenciaCM(LocalDate.now()); // Usa LocalDate.now() para obtener la fecha actual

	    // Guarda los cambios en la base de datos
	    calendariomedicionDao.save(calendarioMedicion);

	    return new ResponseEntity<>(calendarioMedicion, HttpStatus.OK);
	}

	
	@GetMapping("/calendariomedicion/get-all")
	public List<CalendarioMedicion> getAllCalendarioMediciones(){
		return calendariomedicionDao.getAllCalendarioMediciones();
	}
	
	@GetMapping("/calendariomedicion/{codCalendarioMedicion}")
	public CalendarioMedicion getByCodCalendarioM(@PathVariable int codCalendarioMedicion) {
		return calendariomedicionDao.findByCodCalendarioMedicion(codCalendarioMedicion);
	}
	
	@GetMapping("/calendariomedicion/calendario/{codCalendario}")
	public List<CalendarioMedicion> getByCodCalendarioMedicion(@PathVariable int codCalendario){
		return calendariomedicionDao.findByCodCalendario(codCalendario);
	}
	
	@GetMapping("/calendariomedicion/medicion/{codMedicion}")
	public List<CalendarioMedicion> getByCodMedicion(@PathVariable int codMedicion){
		return calendariomedicionDao.findByCodMedicion(codMedicion);
	}

	
	@PostMapping("/calendariomedicion/save")
	public CalendarioMedicion save(@RequestBody CalendarioMedicion calendariomedicion) {
		return calendariomedicionDao.save(calendariomedicion);
	}
	
	@DeleteMapping("/calendariomedicion/delete/{codCalendarioMedicion}")
	 public ResponseEntity<String> deleteCalendarioMedicion(@PathVariable int codCalendarioMedicion) {
        CalendarioMedicion calendarioMedicion = calendariomedicionDao.findByCodCalendarioMedicion(codCalendarioMedicion);

        if (calendarioMedicion == null) {
            return ResponseEntity.notFound().build();
        }

        calendariomedicionDao.delete(calendarioMedicion);

        return ResponseEntity.ok("CalendarioMedicion eliminado exitosamente");
    }

}