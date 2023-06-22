package com.medical.springserver.model.faq;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class FAQDao {
	@Autowired
	private FAQRepository repository;
	
	public FAQ save(FAQ FAQ) {
		return repository.save(FAQ);
	}
	
	public List<FAQ> getAllFAQ() {
	    Streamable<FAQ> streamableFAQ = Streamable.of(repository.findAll());
	    List<FAQ> FAQ = new ArrayList<>();
	    streamableFAQ.forEach(FAQ::add);
	    return FAQ;
	}
	
	public void delete(FAQ FAQ) {
		repository.delete(FAQ);
	}
	
}
