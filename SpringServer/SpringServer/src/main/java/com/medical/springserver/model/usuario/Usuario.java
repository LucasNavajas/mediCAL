package com.medical.springserver.model.usuario;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//Comentario de prueba github
@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codUsuario;
	private String apellidoUsuario;
	private String contraseniaUsuario;
	private LocalDate fechaAltaUsuario;
	private LocalDate fechaNacimientoUsuario;
	private String generoUsuario;
	private String mailUsuario;
	private String nombreInstitucion;
	private String nombreUsuario;
	private String telefonoUsuario;
	private String usuarioUnico;
	
	public int getCodUsuario() {
		return codUsuario;
	}
	public void setCodUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
	}
	public String getApellidoUsuario() {
		return apellidoUsuario;
	}
	public void setApellidoUsuario(String apellidoUsuario) {
		this.apellidoUsuario = apellidoUsuario;
	}
	public String getContraseniaUsuario() {
		return contraseniaUsuario;
	}
	public void setContraseniaUsuario(String contraseniaUsuario) {
		this.contraseniaUsuario = contraseniaUsuario;
	}
	public LocalDate getFechaAltaUsuario() {
		return fechaAltaUsuario;
	}
	public void setFechaAltaUsuario(LocalDate fechaAltaUsuario) {
		this.fechaAltaUsuario = fechaAltaUsuario;
	}
	public LocalDate getFechaNacimientoUsuario() {
		return fechaNacimientoUsuario;
	}
	public void setFechaNacimientoUsuario(LocalDate fechaNacimientoUsuario) {
		this.fechaNacimientoUsuario = fechaNacimientoUsuario;
	}
	public String getGeneroUsuario() {
		return generoUsuario;
	}
	public void setGeneroUsuario(String generoUsuario) {
		this.generoUsuario = generoUsuario;
	}
	public String getMailUsuario() {
		return mailUsuario;
	}
	public void setMailUsuario(String mailUsuario) {
		this.mailUsuario = mailUsuario;
	}
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getTelefonoUsuario() {
		return telefonoUsuario;
	}
	public void setTelefonoUsuario(String telefonoUsuario) {
		this.telefonoUsuario = telefonoUsuario;
	}
	public String getUsuarioUnico() {
		return usuarioUnico;
	}
	public void setUsuarioUnico(String usuario) {
		usuarioUnico = usuario;
	}
	@Override
	public String toString() {
		return "Usuario [codUsuario=" + codUsuario + ", apellidoUsuario=" + apellidoUsuario + ", contraseniaUsuario="
				+ contraseniaUsuario + ", fechaAltaUsuario=" + fechaAltaUsuario + ", fechaNacimientoUsuario="
				+ fechaNacimientoUsuario + ", generoUsuario=" + generoUsuario + ", mailUsuario=" + mailUsuario
				+ ", nombreInstitucion=" + nombreInstitucion + ", nombreUsuario=" + nombreUsuario + ", telefonoUsuario="
				+ telefonoUsuario + ", Usuario=" + usuarioUnico + "]";
	}
	
	
}
