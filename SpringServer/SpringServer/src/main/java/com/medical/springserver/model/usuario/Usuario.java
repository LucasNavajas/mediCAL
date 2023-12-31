package com.medical.springserver.model.usuario;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.codigoverificacion.CodigoVerificacion;
import com.medical.springserver.model.historialfinvigencia.HistorialFinVigencia;
import com.medical.springserver.model.perfil.Perfil;
import com.medical.springserver.model.reporte.Reporte;
import com.medical.springserver.model.solicitud.Solicitud;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codUsuario;
	private String usuarioUnico;
	private String contraseniaUsuario;
	private String nombreUsuario;
	private String apellidoUsuario;
	private LocalDate fechaAltaUsuario;
	private LocalDate fechaNacimientoUsuario;
	private String generoUsuario;
	private String mailUsuario;
	private String nombreInstitucion;
	private String telefonoUsuario;
	
	private String token;
	
	// relacion historial fin vigencia
	@OneToMany (mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<HistorialFinVigencia> historial;
	
	// relacion calendario
	@OneToMany (mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Calendario> varcalendario;
	
	// relacion con reportes
	@OneToMany (mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Reporte> reportes;
	
	// relacion con Perfil
	@ManyToOne 
	@JoinColumn(name = "codPerfil")
	private Perfil perfil;
	
	// relacion con Solicitud Controlador
	@OneToMany (mappedBy = "usuarioControlador", cascade = CascadeType.MERGE)
	@JsonIgnore
	private List<Solicitud> solicitudControlador;
	
	@OneToMany(mappedBy = "usuarioControlado", cascade = CascadeType.MERGE)
	@JsonIgnore
    private List<Solicitud> solicitudControlado;
	
	// relacion con CodVerificacion
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "codVerificacion")
    private CodigoVerificacion codigoVerificacion;
	
		
	public List<Solicitud> getSolicitudControlador() {
		return solicitudControlador;
	}
	public void setSolicitudControlador(List<Solicitud> solicitudControlador) {
		this.solicitudControlador = solicitudControlador;
	}
	public CodigoVerificacion getCodigoVerificacion() {
		return codigoVerificacion;
	}
	public void setCodigoVerificacion(CodigoVerificacion codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
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
				+ telefonoUsuario + ", usuarioUnico=" + usuarioUnico + ", token=" + token + ", perfil=" + perfil
				+ ", codigoVerificacion=" + codigoVerificacion + "]";
	}
	
	// Relaciones
	
	public List<HistorialFinVigencia> getHistorial() {
		return historial;
	}
	public void setHistorial(List<HistorialFinVigencia> historial) {
		this.historial = historial;
	}
	public List<Calendario> getVarcalendario() {
		return varcalendario;
	}
	public void setVarcalendario(List<Calendario> varcalendario) {
		this.varcalendario = varcalendario;
	}
	public List<Reporte> getReportes() {
		return reportes;
	}
	public void setReportes(List<Reporte> reportes) {
		this.reportes = reportes;
	}
	public List<Solicitud> getSolicitudControlado() {
		return solicitudControlado;
	}
	public void setSolicitudControlado(List<Solicitud> solicitudControlado) {
		this.solicitudControlado = solicitudControlado;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
