package com.medical.springserver.model.medicion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.medical.springserver.model.calendario.Calendario;

@Service
public class MedicionDao {
	@Autowired
	private MedicionRepository repository;
	
	public Medicion save(Medicion medicion) {
		return repository.save(medicion);
	}
	
	public Medicion findByCodMedicion(int codMedicion) {
		return repository.findByCodMedicion(codMedicion);
	}
	
	public List<Medicion> getAllMediciones(){
		Streamable<Medicion> streamableMediciones = Streamable.of(repository.findAll());
		List<Medicion> mediciones = new ArrayList<>();
		streamableMediciones.forEach(mediciones::add);
		return mediciones;
	}
	
	public void delete(Medicion medicion) {
		repository.delete(medicion);
	}

}
