package com.medical.springserver.controller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.springserver.model.faq.FAQ;
import com.medical.springserver.model.faq.FAQDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
public class FAQController {

	@Autowired
	FAQDao FAQDao;
	
	@GetMapping("/FAQ/get-all")
	public List<FAQ> getAllFAQ(){
		return FAQDao.getAllFAQ();
	}
	
	@PostMapping("/FAQ/save")
	public FAQ save(@RequestBody FAQ FAQ){
		return FAQDao.save(FAQ);
	}
	
	@GetMapping("/FAQ/get-all-y-bajas")
	public List<FAQ> getAllFAQS(){
		return FAQDao.getAllFAQS();
	}
	
	@PutMapping("/FAQ/baja/{codFAQ}")
	public FAQ bajaFAQ(@PathVariable int codFAQ) {
		return FAQDao.bajaFAQ(codFAQ);
	}
	
	@PutMapping("/FAQ/recuperar/{codFAQ}")
	public FAQ recuperarFAQ(@PathVariable int codFAQ) {
		return FAQDao.recuperarFAQ(codFAQ);
	}
}
