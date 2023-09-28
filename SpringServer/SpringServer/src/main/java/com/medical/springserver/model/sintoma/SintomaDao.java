package com.medical.springserver.model.sintoma;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class SintomaDao {
	@Autowired
	private SintomaRepository repository;
	
	public Sintoma save(Sintoma sintoma) {
	    // Buscar la entidad existente por su código
	    Sintoma existingSintoma = repository.findById(sintoma.getCodSintoma()).orElse(null);

	    if (existingSintoma != null) {
	        if (sintoma.getFechaAltaSintoma() != null) {
	            existingSintoma.setFechaAltaSintoma(sintoma.getFechaAltaSintoma());
	        }
	        existingSintoma.setFechaFinVigenciaS(sintoma.getFechaFinVigenciaS());
	        if (sintoma.getNombreSintoma() != null) {
	            existingSintoma.setNombreSintoma(sintoma.getNombreSintoma());
	        }

	        // Guardar la entidad actualizada
	        return repository.save(existingSintoma);
	    } else {
	        // Si no se encuentra la entidad existente, crea una nueva
	        return repository.save(sintoma);
	    }
	}

	
	public List<Sintoma> getAllSintomas(){
		Streamable<Sintoma> streamableSintomas = Streamable.of(repository.findAll());
		List<Sintoma> sintomas = new ArrayList<>();
		streamableSintomas.forEach(sintomas::add);
		return sintomas;
	}
	
	public void delete(Sintoma sintoma) {
		repository.delete(sintoma);
	}

	
    public List<Sintoma> getAllSintomasYBajas() {
        return repository.getAllSintomasYBajas();
    }

    public Sintoma bajaSintoma(int codSintoma) {
        Sintoma sintoma = repository.findByCodSintoma(codSintoma);
        sintoma.setFechaFinVigenciaS(LocalDate.now());
        repository.save(sintoma);
        return sintoma;
    }

    public Sintoma recuperarSintoma(int codSintoma) {
        Sintoma sintoma = repository.findByCodSintoma(codSintoma);
        sintoma.setFechaFinVigenciaS(null);
        repository.save(sintoma);
        return sintoma;
    }
}