package com.example.medical.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class Usuario {

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

	/*private List<HistorialFinVigencia> historial;
*/
	private List<Calendario> varcalendario;
/*
	private List<Reporte> reportes;

	private List<Perfil> perfil;
*/
	private List<Solicitud> solicitudControlador;

	private CodigoVerificacion codigoVerificacion;

	private List<Solicitud> solicitudControlado;

	public List<Solicitud> getSolicitudControlador() {
		return solicitudControlador;
	}
	public void setSolicitudControlador(List<Solicitud> solicitudControlador) {
		this.solicitudControlador = solicitudControlador;
	}
	public List<Solicitud> getSolicitudControlado() {
		return solicitudControlado;
	}
	public void setSolicitudControlado(List<Solicitud> solicitudControlado) {
		this.solicitudControlado = solicitudControlado;
	}
	public CodigoVerificacion getCodigoVerificacion() {
		return codigoVerificacion;
	}
	public void setCodigoVerificacion(CodigoVerificacion codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}
	/*
	public List<Perfil> getPerfil() {
		return perfil;
	}
	public void setPerfil(List<Perfil> perfil) {
		this.perfil = perfil;
	}*/
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

	// Relaciones

	/*public List<HistorialFinVigencia> getHistorial() {
		return historial;
	}
	public void setHistorial(List<HistorialFinVigencia> historial) {
		this.historial = historial;
	}*/
	public List<Calendario> getVarcalendario() {
		return varcalendario;
	}
	public void setVarcalendario(List<Calendario> varcalendario) {
		this.varcalendario = varcalendario;
	}/*
	public List<Reporte> getReportes() {
		return reportes;
	}
	public void setReportes(List<Reporte> reportes) {
		this.reportes = reportes;
	}
*/

}