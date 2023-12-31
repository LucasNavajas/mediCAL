package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.presentacionMed.PresentacionMed;
import com.medical.springserver.model.presentacionMed.PresentacionMedDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class PresentacionMedController {
	
	@Autowired
	PresentacionMedDao presentacionMedDao;
	
	@GetMapping("/presentacionMed/get-all")
	public List<PresentacionMed> getAllPresentacionesMed(){
		return presentacionMedDao.getAllPresentacionesMed();
	}
	
	@PostMapping("/presentacionMed/save")
	public PresentacionMed save(@RequestBody PresentacionMed presentacionMed) {
		return presentacionMedDao.save(presentacionMed);
	}
	
	@GetMapping("/presentacionmed/{codPresentacionMed}")
    public PresentacionMed getPresentacionMedByCod(@PathVariable int codPresentacionMed) {
        return presentacionMedDao.findByCodPresentacionMed(codPresentacionMed);
    }

    @GetMapping("/presentacionmed/administracion/{codAdministracionMed}")
    public List<PresentacionMed> getPresentacionesMedByCodAdministracionMed(@PathVariable int codAdministracionMed) {
        return presentacionMedDao.findByCodAdministracionMed(codAdministracionMed);
    }
    
    @GetMapping("/presentacionmed/get-all-y-bajas")
    public List<PresentacionMed> getAllPresentacionesMedicasYBajas() {
        return presentacionMedDao.getAllPresentacionesMedicasYBajas();
    }

    @PutMapping("/presentacionmed/baja/{codPresentacionMed}")
    public PresentacionMed bajaPresentacionMedica(@PathVariable int codPresentacionMed) {
        return presentacionMedDao.bajaPresentacionMedica(codPresentacionMed);
    }

    @PutMapping("/presentacionmed/recuperar/{codPresentacionMed}")
    public PresentacionMed recuperarPresentacionMedica(@PathVariable int codPresentacionMed) {
        return presentacionMedDao.recuperarPresentacionMedica(codPresentacionMed);
    }

}
