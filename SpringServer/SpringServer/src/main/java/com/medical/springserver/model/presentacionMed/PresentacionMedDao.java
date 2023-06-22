package com.medical.springserver.model.presentacionMed;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class PresentacionMedDao {

	@Autowired
	private PresentacionMedRepository repository;
	
	public PresentacionMed save(PresentacionMed presentacionMed) {
		return repository.save(presentacionMed);
	}
	
	public List<PresentacionMed> getAllPresentacionesMed(){
		Streamable<PresentacionMed> streamablePresentacionesMed = Streamable.of(repository.findAll());
		List<PresentacionMed> presentacionesMed = new ArrayList<>();
		streamablePresentacionesMed.forEach(presentacionesMed::add);
		return presentacionesMed;
	}
	
	public void delete(PresentacionMed presentacionMed) {
		repository.delete(presentacionMed);
	}
}
