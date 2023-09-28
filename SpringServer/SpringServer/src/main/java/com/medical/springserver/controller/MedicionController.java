package com.medical.springserver.controller;
import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.medicion.Medicion;
import com.medical.springserver.model.medicion.MedicionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class MedicionController {
	
	@Autowired
	MedicionDao medicionDao;
	
	@GetMapping("/medicion/get-all")
	public List<Medicion> getAllMediciones(){
		return medicionDao.getAllMediciones();
	}
	
	@GetMapping("/medicion/{codMedicion}")
	public Medicion getByCodMedicion(@PathVariable int codMedicion) {
		return medicionDao.findByCodMedicion(codMedicion);
	}
	
	@PostMapping("/medicion/save")
	public Medicion save(@RequestBody Medicion medicion) {
		return medicionDao.save(medicion);
	}
	
	@GetMapping("/medicion/get-all-y-bajas")
	public List<Medicion> getAllMedicionesYBajas(){
		return medicionDao.getAllMedicionesYBajas();
	}
	
    @PutMapping("/medicion/baja/{codMedicion}")
    public Medicion bajaMedicion(@PathVariable int codMedicion) {
        return medicionDao.bajaMedicion(codMedicion);
    }

    @PutMapping("/medicion/recuperar/{codMedicion}")
    public Medicion recuperarMedicion(@PathVariable int codMedicion) {
        return medicionDao.recuperarMedicion(codMedicion);
    }

}
