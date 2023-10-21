package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuthException;
import com.medical.springserver.model.profesionalsalud.ProfesionalSalud;
import com.medical.springserver.model.profesionalsalud.ProfesionalSaludDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;



@RestController
public class ProfesionalSaludController {

	@Autowired
	ProfesionalSaludDao profesionalsaludDao;
	
	@GetMapping("/profesionalsalud/get-all")
	public List<ProfesionalSalud> getAllProfesionalesSalud(){
		return profesionalsaludDao.getAllProfesionalesSalud();
	}
	
	@PostMapping("/profesionalsalud/save")
	public ProfesionalSalud save(@RequestBody ProfesionalSalud profesionalsalud) throws FirebaseAuthException{
		return profesionalsaludDao.save(profesionalsalud);
	}
	
	@PostMapping("/profesionalsalud/save-desde-admin")
	public ProfesionalSalud saveAdmin(@RequestBody ProfesionalSalud profesionalsalud) {
		return profesionalsaludDao.saveAdmin(profesionalsalud);
	}
	
	@GetMapping("/profesionalsalud/dni-unico")
	public List<String> obtenerDnisUnicos(){
		return profesionalsaludDao.obtenerDniUnicos();
	}
	
	@GetMapping("/profesionalsalud/matriculas-unicas")
	public List<String> obtenerMatriculasUnicas(){
		return profesionalsaludDao.obtenerMatriculasUnicas();
	}
	
	@GetMapping("/profesionalsalud/filtrados")
	public List<ProfesionalSalud> getProfesionalesSaludByDateRange(
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate,
            @RequestParam(name = "nombreInstitucion") String nombreInstitucion) {
        return profesionalsaludDao.obtenerUsuariosPorFechas(startDate, endDate, nombreInstitucion);
    }
	
}