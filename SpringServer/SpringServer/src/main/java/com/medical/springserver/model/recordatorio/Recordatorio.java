package com.medical.springserver.model.recordatorio;
import java.time.LocalDate;

import com.medical.springserver.model.dosis.Dosis;
import com.medical.springserver.model.frecuencia.Frecuencia;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Recordatorio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codRecordatorio;
	private int duracionRecordatorio;
	private LocalDate fechaAltaRecordatorio;
	private LocalDate fechaFinRecordatorio;
	private LocalDate fechaFinVigenciaR;
	private LocalDate fechaInicioRecordatorio;
	private int horarioRecordatorio;
	@ManyToOne
	@JoinColumn (name = "codDosis")
	private Dosis dosis;
	
	@ManyToOne
	@JoinColumn (name = "codFrecuencia")
	private Frecuencia frecuencia;
	
	public int getCodRecordatorio() {
		return codRecordatorio;
	}
	public void setCodRecordatorio(int codRecordatorio) {
		this.codRecordatorio = codRecordatorio;
	}
	public int getDuracionRecordatorio() {
		return duracionRecordatorio;
	}
	public void setDuracionRecordatorio(int duracionRecordatorio) {
		this.duracionRecordatorio = duracionRecordatorio;
	}
	public LocalDate getFechaAltaRecordatorio() {
		return fechaAltaRecordatorio;
	}
	public void setFechaAltaRecordatorio(LocalDate fechaAltaRecordatorio) {
		this.fechaAltaRecordatorio = fechaAltaRecordatorio;
	}
	public LocalDate getFechaFinRecordatorio() {
		return fechaFinRecordatorio;
	}
	public void setFechaFinRecordatorio(LocalDate fechaFinRecordatorio) {
		this.fechaFinRecordatorio = fechaFinRecordatorio;
	}
	public LocalDate getFechaFinVigenciaR() {
		return fechaFinVigenciaR;
	}
	public void setFechaFinVigenciaR(LocalDate fechaFinVigenciaR) {
		this.fechaFinVigenciaR = fechaFinVigenciaR;
	}
	public LocalDate getFechaInicioRecordatorio() {
		return fechaInicioRecordatorio;
	}
	public void setFechaInicioRecordatorio(LocalDate fechaInicioRecordatorio) {
		this.fechaInicioRecordatorio = fechaInicioRecordatorio;
	}
	public int getHorarioRecordatorio() {
		return horarioRecordatorio;
	}
	public void setHorarioRecordatorio(int horarioRecordatorio) {
		this.horarioRecordatorio = horarioRecordatorio;
	}
	@Override
	public String toString() {
		return "Recordatorio [codRecordatorio=" + codRecordatorio + ", duracionRecordatorio=" + duracionRecordatorio
				+ ", fechaAltaRecordatorio=" + fechaAltaRecordatorio + ", fechaFinRecordatorio=" + fechaFinRecordatorio
				+ ", fechaFinVigenciaR=" + fechaFinVigenciaR + ", fechaInicioRecordatorio=" + fechaInicioRecordatorio
				+ ", horarioRecordatorio=" + horarioRecordatorio + ", dosis=" + dosis + ", frecuencia=" + frecuencia
				+ "]";
	}
	public Dosis getDosis() {
		return dosis;
	}
	public void setDosis(Dosis dosis) {
		this.dosis = dosis;
	}
	public Frecuencia getFrecuencia() {
		return frecuencia;
	}
	public void setFrecuencia(Frecuencia frecuencia) {
		this.frecuencia = frecuencia;
	}
}
