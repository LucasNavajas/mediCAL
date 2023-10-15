package com.medical.springserver.model.usuario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;
import com.medical.springserver.model.estadosolicitud.EstadoSolicitud;
import com.medical.springserver.model.estadosolicitud.EstadoSolicitudDao;
import com.medical.springserver.model.historialfinvigencia.HistorialFinVigencia;
import com.medical.springserver.model.historialfinvigencia.HistorialFinVigenciaRepository;
import com.medical.springserver.model.solicitud.Solicitud;
import com.medical.springserver.model.solicitud.SolicitudDao;

import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class UsuarioDao {
	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private HistorialFinVigenciaRepository historialRepository;
	@Autowired
	private SolicitudDao solicitudDao;
	@Autowired 
	private EstadoSolicitudDao estadoSolicitudDao;
	private PasswordEncoder passwordEncoder;
	
	public UsuarioDao(PasswordEncoder passwordEncoder) {
		this.passwordEncoder=passwordEncoder;
	}
	
	public Usuario saveConHash(Usuario usuario) {
		String hashedPassword = passwordEncoder.encode(usuario.getContraseniaUsuario());
	    usuario.setContraseniaUsuario(hashedPassword);
		return repository.save(usuario);
	}
	
	public Usuario save(Usuario usuario) throws FirebaseAuthException {
        // Buscar el usuario existente por su código (codUsuario)
        Usuario existingUsuario = repository.findById(usuario.getCodUsuario()).orElse(null);

        if (existingUsuario != null) {
            // Actualizar los campos uno por uno
            if (usuario.getApellidoUsuario() != null && !usuario.getApellidoUsuario().equals("")) {
                existingUsuario.setApellidoUsuario(usuario.getApellidoUsuario());
            }
            if (usuario.getContraseniaUsuario() != null && usuario.getContraseniaUsuario().length()<16) {
                modificarContrasenia(usuario.getCodUsuario(), usuario.getContraseniaUsuario());
            }
            if (usuario.getFechaAltaUsuario() != null) {
                existingUsuario.setFechaAltaUsuario(usuario.getFechaAltaUsuario());
            }
            if (usuario.getFechaNacimientoUsuario() != null) {
                existingUsuario.setFechaNacimientoUsuario(usuario.getFechaNacimientoUsuario());
            }
            if (usuario.getGeneroUsuario() != null) {
                existingUsuario.setGeneroUsuario(usuario.getGeneroUsuario());
            }
            if (usuario.getMailUsuario() != null ) {
            	if(!usuario.getMailUsuario().equals(existingUsuario.getMailUsuario())) {
            	modificarMail(usuario.getCodUsuario() ,existingUsuario.getMailUsuario(), usuario.getMailUsuario());
            	existingUsuario.setMailUsuario(usuario.getMailUsuario());
            	}
            }
            if(usuario.getNombreInstitucion()!=null && !usuario.getNombreInstitucion().equals("")) {
                existingUsuario.setNombreInstitucion(usuario.getNombreInstitucion());
            }
            if (usuario.getNombreUsuario() != null && !usuario.getNombreUsuario().equals("")) {
                existingUsuario.setNombreUsuario(usuario.getNombreUsuario());
            }
            if (usuario.getTelefonoUsuario() != null && !usuario.getTelefonoUsuario().equals("")) {
                existingUsuario.setTelefonoUsuario(usuario.getTelefonoUsuario());
            }
            if (usuario.getUsuarioUnico() != null) {
                existingUsuario.setUsuarioUnico(usuario.getUsuarioUnico());
            }
            if (usuario.getToken() != null) {
                existingUsuario.setToken(usuario.getToken());
            }
            if (usuario.getPerfil() != null) {
            	existingUsuario.setPerfil(usuario.getPerfil());
            }
            if(usuario.getCodigoVerificacion() !=null) {
            	existingUsuario.setCodigoVerificacion(usuario.getCodigoVerificacion());
            }
            

            // Guardar el usuario actualizado
            return repository.save(existingUsuario);
        } else {
            // Si no se encuentra el usuario existente, crea uno nuevo
            return repository.save(usuario);
        }
    }
	public List<Usuario> getAllUsuarios() {
	    Streamable<Usuario> streamableUsuarios = Streamable.of(repository.findAll());
	    List<Usuario> usuarios = new ArrayList<>();
	    streamableUsuarios.forEach(usuarios::add);
	    return usuarios;
	}
	
	public void delete(Usuario usuario) {
		repository.delete(usuario);
	}
	
	public Usuario modificarUsuario(int codUsuario, String nuevoNombre, String nuevoApellido,
            String nuevaContrasenia, LocalDate nuevaFechaAlta, 
            LocalDate nuevaFechaNacimiento, String nuevoGenero,
            String nuevoMail, String nuevoNombreInstitucion,
            String nuevoTelefono, String nuevoUsuarioUnico) throws FirebaseAuthException {
			// Paso 1 (opcional): Buscar el usuario por su ID
			Optional<Usuario> optionalUsuario = repository.findByCodUsuario(codUsuario);
			Usuario usuario;
			if (optionalUsuario.isPresent()) {
			usuario = optionalUsuario.get();
			} else {
			// Si no se encontró el usuario, puedes crear uno nuevo (opcional)
			usuario = new Usuario();
			}
			if(!usuario.getMailUsuario().equals(nuevoMail)) {
				UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(usuario.getMailUsuario());
		        String uid = userRecord.getUid();
		        UpdateRequest request = new UpdateRequest(uid).setPassword(nuevaContrasenia);
		        UpdateRequest request2 = new UpdateRequest(uid).setEmail(nuevoMail);
		        UserRecord userRecord2 = FirebaseAuth.getInstance().updateUser(request);
		        FirebaseAuth.getInstance().updateUser(request2);
			}
			// Paso 2: Realizar los cambios necesarios
			usuario.setNombreUsuario(nuevoNombre);
			usuario.setApellidoUsuario(nuevoApellido);
			String hashedPassword = passwordEncoder.encode(nuevaContrasenia);
		    usuario.setContraseniaUsuario(hashedPassword);
			usuario.setFechaAltaUsuario(nuevaFechaAlta);
			usuario.setFechaNacimientoUsuario(nuevaFechaNacimiento);
			usuario.setGeneroUsuario(nuevoGenero);
			usuario.setMailUsuario(nuevoMail);
			usuario.setNombreInstitucion(nuevoNombreInstitucion);
			usuario.setTelefonoUsuario(nuevoTelefono);
			usuario.setUsuarioUnico(nuevoUsuarioUnico);
			
			// Paso 3: Guardar los cambios en la base de datos
			return repository.save(usuario);
			}
	
	public Usuario modificarContrasenia(int codUsuario, String nuevaContrasenia) throws FirebaseAuthException {
        Optional<Usuario> optionalUsuario = repository.findByCodUsuario(codUsuario);
        Usuario usuario;
        nuevaContrasenia = nuevaContrasenia.replace("\"", "");
        if (optionalUsuario.isPresent()) {
            usuario = optionalUsuario.get();
            usuario.setCodigoVerificacion(null);
        } else {
            throw new NoSuchElementException("El usuario no existe.");
        }
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(usuario.getMailUsuario());
        String uid = userRecord.getUid();
        UpdateRequest request = new UpdateRequest(uid).setPassword(nuevaContrasenia);
        UserRecord userRecord2 = FirebaseAuth.getInstance().updateUser(request);
        String hashedPassword = passwordEncoder.encode(nuevaContrasenia);
        usuario.setContraseniaUsuario(hashedPassword);
        return repository.save(usuario);
    }
	
	public void modificarMail(int codUsuario, String mail ,String nuevoMail) throws FirebaseAuthException{
        Optional<Usuario> optionalUsuario = repository.findByCodUsuario(codUsuario);
        Usuario usuario;
        if (optionalUsuario.isPresent()) {
            usuario = optionalUsuario.get();
            usuario.setCodigoVerificacion(null);
        } else {
            throw new NoSuchElementException("El usuario no existe.");
        }
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(mail);
        String uid = userRecord.getUid();
        UpdateRequest request = new UpdateRequest(uid).setEmail(nuevoMail);
        UserRecord userRecord2 = FirebaseAuth.getInstance().updateUser(request);
	}
	public Usuario obtenerUsuariosPorUsuarioUnico(String usuarioUnico) {
	    List<Usuario> usuariosConMismoUsuario = repository.findByUsuarioUnico(usuarioUnico);
	    if (usuariosConMismoUsuario.isEmpty()) {
	        return null;
	    }
	    
	    List<Usuario> usuariosVigentes = new ArrayList<>();
	    
	    for (Usuario usuario : usuariosConMismoUsuario) {
	        if (estaVigente(usuario)) {
	            usuariosVigentes.add(usuario);
	        }
	    }
	    
	    if (usuariosVigentes.isEmpty()) {
	        return null;
	    }
	    
	    return usuariosVigentes.get(0);
	}
	
	public List<String> obtenerUsuariosUnicos() {
	    List<String> usuarios = repository.findAllDistinctUsuarioUnico();
	    List<String> usuariosVigentes = new ArrayList<>();

	    for (String usuario : usuarios) {
	        Usuario temporal = obtenerUsuariosPorUsuarioUnico(usuario);

	        // Verificar si temporal es nulo antes de llamar a métodos en él
	        if (temporal != null && estaVigente(temporal)) {
	            usuariosVigentes.add(usuario);
	        }
	    }
	    
	    return usuariosVigentes;
	}

	
	public boolean estaVigente(Usuario usuario) {
		List<HistorialFinVigencia> historiales = historialRepository.findHistorialByUsuarioCod(usuario.getCodUsuario());
		if(historiales.size()==0) {return true;}//Si no tiene historial esta vigente
		if(historiales.get(historiales.size()-1).getFechaHastaFV()==null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public List<String> obtenerMailsUnicos() {
	    List<String> mails = repository.findAllDistinctMailUsuario();
	    List<String> mailsVigentes = new ArrayList<>();

	    for (String mail : mails) {
	        Usuario temporal = getByMailUsuario(mail);

	        // Verificar si temporal es nulo antes de llamar a métodos en él
	        if (temporal != null && estaVigente(temporal)) {
	            mailsVigentes.add(mail);
	        }
	    }

	    return mailsVigentes;
	}

	public Optional<Usuario> getByCodUsuario(int codUsuario) {
	    return repository.findByCodUsuario(codUsuario);
	}

	public Usuario getByMailUsuario(String mailUsuario) {
	    List<Usuario> usuarios = repository.findByMailUsuario(mailUsuario);
	    List<Usuario> usuariosVigentes = new ArrayList<>();

	    for (Usuario usuario : usuarios) {
	        if (estaVigente(usuario)) {
	            usuariosVigentes.add(usuario);
	        }
	    }

	    if (usuariosVigentes.isEmpty()) {
	        return null;
	    }

	    return usuariosVigentes.get(usuariosVigentes.size() - 1);
	}

	
	public List<String> obtenerMailsCuentas() {
	    List<String> mailsCuentas = repository.findAllDistinctMailCuentas();
	    List<String> mailsCuentasVigentes = new ArrayList<>();

	    for (String mailCuenta : mailsCuentas) {
	        Usuario temporal = getByMailUsuario(mailCuenta);

	        // Verificar si temporal es nulo antes de llamar a métodos en él
	        if (temporal != null && estaVigente(temporal)) {
	            mailsCuentasVigentes.add(mailCuenta);
	        }
	    }

	    return mailsCuentasVigentes;
	}

	public boolean verificarMismaContrasenia(int codUsuario, String nuevaContrasenia) {
		Optional<Usuario> optionalUsuario = repository.findByCodUsuario(codUsuario);
        Usuario usuario;
        nuevaContrasenia = nuevaContrasenia.replace("\"", "");
        if (optionalUsuario.isPresent()) {
            usuario = optionalUsuario.get();
        } else {
            throw new NoSuchElementException("El usuario no existe.");
        }
        return(passwordEncoder.matches(nuevaContrasenia, usuario.getContraseniaUsuario()));
	}

	public void eliminarUsuario(int codUsuario, String motivoFinVigencia) throws FirebaseAuthException {//Para usuarios particulares, despues implementar para enfermeros
		Optional<Usuario> usuarioOpt = repository.findByCodUsuario(codUsuario);
		if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            HistorialFinVigencia historialUsuario= new HistorialFinVigencia();
            historialUsuario.setFechaDesdeFV(LocalDate.now());
            historialUsuario.setUsuario(usuario);
            historialUsuario.setMotivoFV(motivoFinVigencia);
            historialRepository.save(historialUsuario);
            List<Solicitud> solicitudesUsuario = solicitudDao.obtenerTodasSolicitudesUsuario(codUsuario);
            EstadoSolicitud baja = estadoSolicitudDao.findByCodEstadoSolicitud(3);
            for(Solicitud solicitud : solicitudesUsuario) {
            	solicitud.setEstadoSolicitud(baja);
            	solicitudDao.save(solicitud);
            }
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(usuario.getMailUsuario());
            String uid = userRecord.getUid();
            userRecord = FirebaseAuth.getInstance().updateUser(new UserRecord.UpdateRequest(uid).setDisabled(true));
        } else {
            throw new NoSuchElementException("El usuario no existe.");
        }
		
	}
	
	
	public Usuario habilitarUsuario(int codUsuario) throws FirebaseAuthException {
	    Optional<Usuario> usuarioOpt = repository.findByCodUsuario(codUsuario);
	    
	    if (usuarioOpt.isPresent()) {
	        Usuario usuario = usuarioOpt.get();
	        
	        // Buscar el último historial de fin de vigencia
	        HistorialFinVigencia ultimoHistorial = findLastHistorialByUsuarioCod(codUsuario);
	        
	        // Verificar si el último historial ya tiene una fecha de finalización
	        if (ultimoHistorial != null && ultimoHistorial.getFechaHastaFV() == null) {
	            // Establecer la fecha de finalización en la fecha actual
	            ultimoHistorial.setFechaHastaFV(LocalDate.now());
	            historialRepository.save(ultimoHistorial);
	        }
	        
	        // Habilitar la cuenta en Firebase
	        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(usuario.getMailUsuario());
	        String uid = userRecord.getUid();
	        userRecord = FirebaseAuth.getInstance().updateUser(new UserRecord.UpdateRequest(uid).setDisabled(false));
	        return usuario;
	    } else {
	        throw new NoSuchElementException("El usuario no existe.");
	    }
	}
	
	public HistorialFinVigencia findLastHistorialByUsuarioCod(int codUsuario) {
	    List<HistorialFinVigencia> historiales = historialRepository.findHistorialByUsuarioCod(codUsuario);
	    
	    if (historiales.isEmpty()) {
	        return null; // No hay historiales para el usuario
	    } else {
	        // Ordena la lista de historiales por nroHistorialFV en orden ascendente
	        historiales.sort(Comparator.comparingInt(HistorialFinVigencia::getNroHistorialFV));
	        
	        // El último historial estará en el último elemento de la lista después de ordenar
	        return historiales.get(historiales.size() - 1);
	    }
	}


	public Usuario modificarToken(int codUsuario, String token) throws FirebaseAuthException {
		Optional<Usuario> optionalUsuario = repository.findByCodUsuario(codUsuario);
        Usuario usuario;
        if (optionalUsuario.isPresent()) {
            usuario = optionalUsuario.get();
            usuario.setToken(token);
           return save(usuario);
        } else {
            throw new NoSuchElementException("El usuario no existe.");
        }
	}
	
	public List<Usuario> findUsuariosByCodPerfil(int codPerfil) {
        return repository.findByCodPerfil(codPerfil);
    }

	public Usuario altaAdmin(Usuario usuario) {
		CreateRequest request = new CreateRequest()
	            .setEmail(usuario.getMailUsuario())
	            .setPassword(usuario.getContraseniaUsuario())
	            .setEmailVerified(true);
		try {
	        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
	        System.out.println("Successfully created new user: " + userRecord.getUid());
	    } catch (Exception e) {
	        System.err.println("Error creating user: " + e.getMessage());
	    }
		return saveConHash(usuario);
	}

	public void verificarMailExistente(String mail) throws FirebaseAuthException {
		List<Usuario> usuarios = repository.findByMailUsuario(mail);
	    if(usuarios.size()>0) {
	    	UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(usuarios.get(0).getMailUsuario());
	        String uid = userRecord.getUid();
	        FirebaseAuth.getInstance().deleteUser(uid);
	    }
	}
	
	public List<Usuario> findByInstitucion(String nombreInstitucion){
		return repository.findPorInstitucion(nombreInstitucion);
	}
}