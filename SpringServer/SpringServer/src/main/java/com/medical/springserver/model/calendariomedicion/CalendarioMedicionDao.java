package com.medical.springserver.model.calendariomedicion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.UpdateRequest;
import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;
import com.medical.springserver.model.usuario.Usuario;

@Service
public class CalendarioMedicionDao {
	@Autowired
	private CalendarioMedicionRepository repository;
	
	public CalendarioMedicion modificarCalendarioMedicion(int codCalendarioMedicion, Float nuevoValor) {
        // Paso 1: Obtener el CalendarioMedicion actual
        CalendarioMedicion calendarioMedicion = repository.findByCodCalendarioMedicion(codCalendarioMedicion);

        if (calendarioMedicion == null) {
            // Manejar la situación en la que no se encuentra el CalendarioMedicion
            throw new NoSuchElementException("El CalendarioMedicion no existe.");
        }

        // Paso 2: Modificar el campo que deseas cambiar
        calendarioMedicion.setValorCalendarioMedicion(nuevoValor); 
        // Paso 3: Guardar los cambios en la base de datos
        return repository.save(calendarioMedicion);
    }
	
	 public CalendarioMedicion eliminarCalendarioMedicion(int codCalendarioMedicion) {
	        // Paso 1: Obtener el CalendarioMedicion actual
	        CalendarioMedicion calendarioMedicion = repository.findByCodCalendarioMedicion(codCalendarioMedicion);

	        if (calendarioMedicion == null) {
	            // Manejar la situación en la que no se encuentra el CalendarioMedicion
	            throw new NoSuchElementException("El CalendarioMedicion no existe.");
	        }

	        // Paso 2: Modificar el atributo fechaFinVigenciaCM con la fecha actual
	        calendarioMedicion.setFechaFinVigenciaCM(LocalDate.now());

	        // Paso 3: Guardar los cambios en la base de datos
	        return repository.save(calendarioMedicion);
	    }
	
	
	public CalendarioMedicion save(CalendarioMedicion calendariomedicion) {
		return repository.save(calendariomedicion);
	}
	
	public CalendarioMedicion findByCodCalendarioMedicion(int codCalendarioMedicion) {
		return repository.findByCodCalendarioMedicion(codCalendarioMedicion);
	}
	
	
	public List<CalendarioMedicion> findByCodCalendario(int codCalendario){
		return repository.findByCodCalendario(codCalendario);
	}
	
	public List<CalendarioMedicion> findByCodMedicion(int codMedicion){
		return repository.findByCodMedicion(codMedicion);
	}
	
	
	public List<CalendarioMedicion> getAllCalendarioMediciones(){
		Streamable<CalendarioMedicion> streamableCalendarioMediciones = Streamable.of(repository.findAll());
		List<CalendarioMedicion> calendariomediciones = new ArrayList<>();
		streamableCalendarioMediciones.forEach(calendariomediciones::add);
		return calendariomediciones;
	}
	
	public void delete(CalendarioMedicion calendariomedicion) {
		repository.delete(calendariomedicion);
	}

}
