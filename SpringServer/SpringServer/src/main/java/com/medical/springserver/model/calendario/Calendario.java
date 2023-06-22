package com.medical.springserver.model.calendario;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
	
	@Override
	public String toString() {
		return "Calendario [codCalendario=" + codCalendario + ", fechaAltaCalendario=" + fechaAltaCalendario
				+ ", fechaFinVigenciaC=" + fechaFinVigenciaC + ", nombreCalendario=" + nombreCalendario
				+ ", nombrePaciente=" + nombreCalendario + ", relacionCalendario=" + relacionCalendario
				+ "]";
	}
	
}
