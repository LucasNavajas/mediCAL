package com.medical.springserver.model.calendario;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medical.springserver.model.calendariomedicion.CalendarioMedicion;
import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;
import com.medical.springserver.model.recordatorio.Recordatorio;
import com.medical.springserver.model.usuario.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Calendario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codCalendario;
	private LocalDate fechaAltaCalendario;
	private LocalDate fechaFinVigenciaC;
	private String nombreCalendario;
	private String nombrePaciente;
	private String relacionCalendario;
	
	// Relacion con Usuario
	@ManyToOne
	@JoinColumn(name = "codUsuario")
	private Usuario usuario;
	
	// Relacion con Recordatorio
	@OneToMany (mappedBy = "calendario", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Recordatorio> recordatorio;
	
	// Relacion con CalendarioMedicion
	@OneToMany (mappedBy = "calendario", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CalendarioMedicion> varcalendariomedicion;
	
	// Relacion con CalendarioSintoma
	@OneToMany (mappedBy = "calendario", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CalendarioSintoma> varcalendariosintoma;
	
	public int getCodCalendario() {
		return codCalendario;
	}
	public void setCodCalendario(int codCalendario) {
		this.codCalendario = codCalendario;
	}
	public LocalDate getFechaAltaCalendario() {
		return fechaAltaCalendario;
	}
	public void setFechaAltaCalendario(LocalDate fechaAltaCalendario) {
		this.fechaAltaCalendario = fechaAltaCalendario;
	}
	public LocalDate getFechaFinVigenciaC() {
		return fechaFinVigenciaC;
	}
	public void setFechaFinVigenciaC(LocalDate fechaFinVigenciaC) {
		this.fechaFinVigenciaC = fechaFinVigenciaC;
	}
	public String getNombreCalendario() {
		return nombreCalendario;
	}
	public void setNombreCalendario(String nombreCalendario) {
		this.nombreCalendario = nombreCalendario;
	}
	public String getNombrePaciente() {
		return nombrePaciente;
	}
	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}
	public String getRelacionCalendario() {
		return relacionCalendario;
	}
	public void setRelacionCalendario(String relacionCalendario) {
		this.relacionCalendario = relacionCalendario;
	}

	
	// Relacion
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}	
	public List<Recordatorio> getRecordatorio() {
		return recordatorio;
	}
	public void setRecordatorio(List<Recordatorio> recordatorio) {
		this.recordatorio = recordatorio;
	}
	public List<CalendarioMedicion> getVarcalendariomedicion() {
		return varcalendariomedicion;
	}
	public void setVarcalendariomedicion(List<CalendarioMedicion> varcalendariomedicion) {
		this.varcalendariomedicion = varcalendariomedicion;
	}
	public List<CalendarioSintoma> getVarcalendariosintoma() {
		return varcalendariosintoma;
	}
	public void setVarcalendariosintoma(List<CalendarioSintoma> varcalendariosintoma) {
		this.varcalendariosintoma = varcalendariosintoma;
	}
	
}
