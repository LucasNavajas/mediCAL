package com.medical.springserver.model.calendario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;
import com.medical.springserver.model.calendariosintoma.CalendarioSintomaDao;
import com.medical.springserver.model.recordatorio.Recordatorio;
import com.medical.springserver.model.recordatorio.RecordatorioDao;
import com.medical.springserver.model.usuario.Usuario;
import com.medical.springserver.model.usuario.UsuarioDao;

@Service

public class CalendarioDao {
	@Autowired
	private CalendarioRepository repository;
	@Autowired 
	private UsuarioDao usuarioDao;
	@Autowired
	private RecordatorioDao recordatorioDao;
	@Autowired
	private CalendarioSintomaDao calendarioSintomaDao;
	
	public Calendario save(Calendario calendario) {
	    // Buscar la entidad existente por su código
	    Calendario existingCalendario = repository.findById(calendario.getCodCalendario()).orElse(null);

	    if (existingCalendario != null) {
	        if (calendario.getFechaAltaCalendario() != null) {
	            existingCalendario.setFechaAltaCalendario(calendario.getFechaAltaCalendario());
	        }
	        if (calendario.getFechaFinVigenciaC() != null) {
	            existingCalendario.setFechaFinVigenciaC(calendario.getFechaFinVigenciaC());
	        }
	        if (calendario.getNombreCalendario() != null) {
	            existingCalendario.setNombreCalendario(calendario.getNombreCalendario());
	        }
	        if (calendario.getNombrePaciente() != null) {
	            existingCalendario.setNombrePaciente(calendario.getNombrePaciente());
	        }
	        if (calendario.getRelacionCalendario() != null) {
	            existingCalendario.setRelacionCalendario(calendario.getRelacionCalendario());
	        }

	        // Guardar la entidad actualizada
	        return repository.save(existingCalendario);
	    } else {
	        // Si no se encuentra la entidad existente, crea una nueva
	        return repository.save(calendario);
	    }
	}

	
	public List<Calendario> getAllCalendarios(){
		Streamable<Calendario> streamableCalendarios = Streamable.of(repository.findAll());
		List<Calendario> calendarios = new ArrayList<>();
		streamableCalendarios.forEach(calendarios::add);
		return calendarios;
	}
	
	public void delete(Calendario calendario) {
		repository.delete(calendario);
	}
	
	public List<Calendario> findByCodUsuario(int codUsuario){
		return repository.findByCodUsuario(codUsuario);
	}
	
	public Calendario findByCodCalendario(int codCalendario) {
		return repository.findByCodCalendario(codCalendario);
	}
	
	 public Calendario modificarCalendario(int codCalendario, String nombre, String relacion, String paciente) {
			 	//int id, LocalDate fechaAlta, LocalDate fechaFin, String nombre, String relacion, String paciente) {
	        	Calendario calendario = repository.findByCodCalendario(codCalendario);
	            //calendario.setFechaAltaCalendario(fechaAlta);
	            //calendario.setFechaFinVigenciaC(fechaFin);
	            calendario.setNombreCalendario(nombre);
	            calendario.setRelacionCalendario(relacion);
	            calendario.setNombrePaciente(paciente);
	            return save(calendario);
	        
	    }
	 
	 public void eliminarCalendario(int codCalendario) {
		 Calendario calendario = repository.findByCodCalendario(codCalendario);
		 calendario.setFechaFinVigenciaC(LocalDate.now());
		 save(calendario);
		 
	 }
	 
	 public void recuperarCalendario(int codCalendario) {
		 Calendario calendario = repository.findByCodCalendario(codCalendario);
		 calendario.setFechaFinVigenciaC(null);
		 save(calendario);
	 }
	 
	 public List<Calendario> findByInstitucion(String nombreInstitucion) {
		 return repository.findByInstitucion(nombreInstitucion);
	 }
	 
	 public List<Calendario> obtenerCalendariosFiltrados(int codUsuario, int codMedicamento, int codSintoma, String nombreInstitucion){
		 List<Calendario> pacientes = new ArrayList<>();
		 List<Calendario> pacientesFiltradosMed = new ArrayList<>();
		 List<Calendario> pacientesFiltradosSintomas = new ArrayList<>();
		 
		 if (codUsuario == 0) {
			List<Usuario> profesionales = usuarioDao.findByInstitucion(nombreInstitucion);
			for (Usuario profesional : profesionales) {
				pacientes.addAll(findByCodUsuario(profesional.getCodUsuario()));
			}
		 }
		 else {
			 pacientes.addAll(findByCodUsuario(codUsuario));
		 }
		 if(codMedicamento == 0 && codSintoma == 0) {
			 return pacientes;
		 }
		 if(codMedicamento != 0) {
			 for(Calendario paciente : pacientes) {
				 List<Recordatorio> recordatorios = recordatorioDao.findByCodCalendario(paciente.getCodCalendario());
				 for (Recordatorio recordatorio : recordatorios) {
					 if(recordatorio.getMedicamento().getCodMedicamento() == codMedicamento) {
						 pacientesFiltradosMed.add(paciente);
					 }
				 }
			 }
			 if(codSintoma != 0) {
				 for(Calendario paciente: pacientesFiltradosMed) {
					 List<CalendarioSintoma> sintomas = calendarioSintomaDao.findByCodCalendario(paciente.getCodCalendario());
					 for(CalendarioSintoma sintoma : sintomas) {
						 if(sintoma.getSintoma().getCodSintoma()==codSintoma) {
							 pacientesFiltradosSintomas.add(paciente);
						 }
					 }
				 }
				 return pacientesFiltradosSintomas;
			 }
			 else {
				 return pacientesFiltradosMed;
			 }
		 }
		 
		 if(codSintoma != 0) {
			 for(Calendario paciente: pacientes) {
				 List<CalendarioSintoma> sintomas = calendarioSintomaDao.findByCodCalendario(paciente.getCodCalendario());
				 for(CalendarioSintoma sintoma : sintomas) {
					 if(sintoma.getSintoma().getCodSintoma()==codSintoma) {
						 pacientesFiltradosSintomas.add(paciente);
					 }
				 }
			 }
			 return pacientesFiltradosSintomas;
		 }
		 else {
			 return pacientes;
		 }
	 }

}
