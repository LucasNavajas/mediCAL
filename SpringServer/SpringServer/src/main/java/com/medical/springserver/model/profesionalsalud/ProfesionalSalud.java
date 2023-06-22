package com.medical.springserver.model.profesionalsalud;

import java.time.LocalDate;

import com.medical.springserver.model.usuario.Usuario;

import jakarta.persistence.Entity;

@Entity
public class ProfesionalSalud extends Usuario {
	
	private long dni;
	private int matricula;
	
	public long getDni() {
		return dni;
	}

	public void setDni(long dni) {
		this.dni = dni;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	
	public String getNombreUsuario() {
		return super.getNombreUsuario();
	}
	
	public void setNombreUsuario(String nombreUsuario) {
		super.setNombreUsuario(nombreUsuario);
	}
	
	public String getApellidoUsuario() {
		return super.getApellidoUsuario();
	}
	
	public void setApellidoUsuario(String apellidoUsuario) {
		super.setApellidoUsuario(apellidoUsuario);
	}
	
	public String getContraseniaUsuario() {
		return super.getContraseniaUsuario();
	}
	
	public void setContraseniaUsuario(String contraseniaUsuario) {
		super.setContraseniaUsuario(contraseniaUsuario);
	}
	
	public LocalDate getFechaAltaUsuario() {
		return super.getFechaAltaUsuario();
	}
	
	public void setFechaAltaUsuario(LocalDate fechaAltaUsuario) {
		super.setFechaAltaUsuario(fechaAltaUsuario);
	}
	
	public LocalDate getFechaNacimientoUsuario() {
		return super.getFechaNacimientoUsuario();
	}
	
	public void setFechaNacimientoUsuario(LocalDate fechaNacimientoUsuario) {
		super.setFechaNacimientoUsuario(fechaNacimientoUsuario);
	}
	
	public String getGeneroUsuario() {
		return super.getGeneroUsuario();
	}
	
	public void setGeneroUsuario(String generoUsuario) {
		super.setGeneroUsuario(generoUsuario);
	}
	
	public String getMailUsuario() {
		return super.getMailUsuario();
	}
	
	public void setMailUsuario(String mailUsuario) {
		super.setMailUsuario(mailUsuario);
	}
	
	public String getNombreInstitucion() {
		return super.getNombreInstitucion();
	}
	
	public void setNombreInstitucion(String nombreInstitucion) {
		super.setNombreInstitucion(nombreInstitucion);
	}
	
	public String getTelefonoUsuario() {
		return super.getTelefonoUsuario();
	}
	
	public void setTelefonoUsuario(String telefonoUsuario) {
		super.setTelefonoUsuario(telefonoUsuario);
	}
	

	
	@Override
	public String toString() {
	    return "ProfesionalSalud [dni=" + dni + ", matricula=" + matricula + ", nombre=" + getNombreUsuario() + ", apellido=" + getApellidoUsuario() + ", ApellidoUsuario=" + getApellidoUsuario() + ", ContraseniaUsuario=" + getContraseniaUsuario() + ", FechaAltaUsuario=" + getFechaAltaUsuario() + ", FechaNacimientoUsuario=" + getFechaNacimientoUsuario() + ", GeneroUsuario=" + getGeneroUsuario() + ", MailUsuario=" + getMailUsuario() + ", NombreInstitucion=" + getNombreInstitucion() + ", NombreUsuario=" + getNombreUsuario() + ", TelefonoUsuario=" + getTelefonoUsuario() + "]";
	}
}

	
