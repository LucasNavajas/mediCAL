package com.medical.springserver.model.profesionalsalud;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.medical.springserver.model.usuario.Usuario;
import com.medical.springserver.model.usuario.UsuarioDao;

@Service
public class ProfesionalSaludDao {
	@Autowired
	private ProfesionalSaludRepository repository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	public ProfesionalSaludDao(PasswordEncoder passwordEncoder) {
		this.passwordEncoder=passwordEncoder;
	}
	
	public ProfesionalSalud save(ProfesionalSalud profesionalSalud) throws FirebaseAuthException {
	    // Buscar el profesional de salud existente por su código (codProfesionalSalud)
	    ProfesionalSalud existingProfesionalSalud = repository.findById(profesionalSalud.getCodUsuario()).orElse(null);

	    if (existingProfesionalSalud != null) {
	        // Actualizar los campos uno por uno
	        if (profesionalSalud.getApellidoUsuario() != null && !profesionalSalud.getApellidoUsuario().equals("")) {
	            existingProfesionalSalud.setApellidoUsuario(profesionalSalud.getApellidoUsuario());
	        }
	        if (profesionalSalud.getContraseniaUsuario() != null &&  profesionalSalud.getContraseniaUsuario().length()<16) {
	            usuarioDao.modificarContrasenia(profesionalSalud.getCodUsuario(), profesionalSalud.getContraseniaUsuario());
	        }
	        if (profesionalSalud.getFechaAltaUsuario() != null) {
	            existingProfesionalSalud.setFechaAltaUsuario(profesionalSalud.getFechaAltaUsuario());
	        }
	        if (profesionalSalud.getFechaNacimientoUsuario() != null) {
	            existingProfesionalSalud.setFechaNacimientoUsuario(profesionalSalud.getFechaNacimientoUsuario());
	        }
	        if (profesionalSalud.getGeneroUsuario() != null) {
	            existingProfesionalSalud.setGeneroUsuario(profesionalSalud.getGeneroUsuario());
	        }
	        if (profesionalSalud.getMailUsuario() != null && !profesionalSalud.getMailUsuario().equals(existingProfesionalSalud.getMailUsuario())) {
	            usuarioDao.modificarMail(profesionalSalud.getCodUsuario(), existingProfesionalSalud.getMailUsuario(), profesionalSalud.getMailUsuario());
	            existingProfesionalSalud.setMailUsuario(profesionalSalud.getMailUsuario());
	        }
	        if(profesionalSalud.getNombreInstitucion()!=null && !profesionalSalud.getNombreInstitucion().equals("")) {
	        existingProfesionalSalud.setNombreInstitucion(profesionalSalud.getNombreInstitucion());
	        }
	        if (profesionalSalud.getNombreUsuario() != null && !profesionalSalud.getNombreUsuario().equals("")) {
	            existingProfesionalSalud.setNombreUsuario(profesionalSalud.getNombreUsuario());
	        }
	        if (profesionalSalud.getTelefonoUsuario() != null && !profesionalSalud.getTelefonoUsuario().equals("")) {
	            existingProfesionalSalud.setTelefonoUsuario(profesionalSalud.getTelefonoUsuario());
	        }
	        if (profesionalSalud.getUsuarioUnico() != null) {
	            existingProfesionalSalud.setUsuarioUnico(profesionalSalud.getUsuarioUnico());
	        }
	        if (profesionalSalud.getToken() != null) {
	            existingProfesionalSalud.setToken(profesionalSalud.getToken());
	        }
	        if (profesionalSalud.getPerfil() != null) {
	            existingProfesionalSalud.setPerfil(profesionalSalud.getPerfil());
	        }
	        existingProfesionalSalud.setCodigoVerificacion(profesionalSalud.getCodigoVerificacion());
	        // Agregar los atributos dni y matricula
	        existingProfesionalSalud.setDni(profesionalSalud.getDni());
	        existingProfesionalSalud.setMatricula(profesionalSalud.getMatricula());

	        // Guardar el profesional de salud actualizado
	        return repository.save(existingProfesionalSalud);
	    } else {
	        // Si no se encuentra el profesional de salud existente, crea uno nuevo
	        return repository.save(profesionalSalud);
	    }
	}
	
	public List<ProfesionalSalud> getAllProfesionalesSalud() {
	    Streamable<ProfesionalSalud> streamableProfesionalesSalud = Streamable.of(repository.findAll());
	    List<ProfesionalSalud> profesionalessalud = new ArrayList<>();
	    streamableProfesionalesSalud.forEach(profesionalessalud::add);
	    return profesionalessalud;
	}
	
	public void delete(ProfesionalSalud profesionalsalud) {
		repository.delete(profesionalsalud);
	}
	
	public ProfesionalSalud saveConHash(ProfesionalSalud profesionalsalud) {
		String hashedPassword = passwordEncoder.encode(profesionalsalud.getContraseniaUsuario());
		profesionalsalud.setContraseniaUsuario(hashedPassword);
		return repository.save(profesionalsalud);
	}

	public ProfesionalSalud saveAdmin(ProfesionalSalud profesionalsalud) {
		CreateRequest request = new CreateRequest()
	            .setEmail(profesionalsalud.getMailUsuario())
	            .setPassword(profesionalsalud.getContraseniaUsuario())
	            .setEmailVerified(true);
		try {
	        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
	        System.out.println("Successfully created new user: " + userRecord.getUid());
	    } catch (Exception e) {
	        System.err.println("Error creating user: " + e.getMessage());
	    }
		return saveConHash(profesionalsalud);
	}
	
}