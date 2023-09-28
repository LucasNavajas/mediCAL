package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.sintoma.Sintoma;
import com.medical.springserver.model.sintoma.SintomaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class SintomaController {
	
	@Autowired
	SintomaDao sintomaDao;
	
	@GetMapping("/sintoma/get-all")
	public List<Sintoma> getAllSintomas(){
		return sintomaDao.getAllSintomas();
	}
	
	@PostMapping("/sintoma/save")
	public Sintoma save(@RequestBody Sintoma sintoma) {
		return sintomaDao.save(sintoma);
	}
	
	@GetMapping("/sintoma/get-all-y-bajas")
    public List<Sintoma> getAllSintomasYBajas() {
        return sintomaDao.getAllSintomasYBajas();
    }

    @PutMapping("/sintoma/baja/{codSintoma}")
    public Sintoma bajaSintoma(@PathVariable int codSintoma) {
        return sintomaDao.bajaSintoma(codSintoma);
    }

    @PutMapping("/sintoma/recuperar/{codSintoma}")
    public Sintoma recuperarSintoma(@PathVariable int codSintoma) {
        return sintomaDao.recuperarSintoma(codSintoma);
    }

}
