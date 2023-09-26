package com.medical.springserver.controller;
import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.medicamento.Medicamento;
import com.medical.springserver.model.medicamento.MedicamentoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class MedicamentoController {
	
	@Autowired
	MedicamentoDao medicamentoDao;
	
	@GetMapping("/medicamento/get-all")
	public List<Medicamento> getAllMedicamentos(){
		return medicamentoDao.getAllMedicamentos();
	}
	
	@PostMapping("/medicamento/save")
	public Medicamento save(@RequestBody Medicamento medicamento) {
		return medicamentoDao.save(medicamento);
	}
	
	@GetMapping("/medicamento/get-all-genericos")
	public List<Medicamento> getAllMedicamentosGenericos(){
		return medicamentoDao.obtenerMedicamentosGenericos();
	}
	
	@GetMapping("/medicamento/get-all-genericos-y-bajas")
	public List<Medicamento> getAllMedicamentosGenericosYBajas(){
		return medicamentoDao.getAllMedicamentosGenericos();
	}
	
	@PutMapping("/medicamento/baja/{codMedicamento}")
	public Medicamento bajaMedicamento(@PathVariable int codMedicamento) {
	    return medicamentoDao.bajaMedicamento(codMedicamento);
	}
	
	@PutMapping("/medicamento/recuperar/{codMedicamento}")
	public Medicamento recuperarMedicamento(@PathVariable int codMedicamento) {
	    return medicamentoDao.recuperarMedicamento(codMedicamento);
	}

}
