package com.medical.springserver.model.calendariomedicion;
import java.time.LocalDate;

import com.medical.springserver.model.medicion.Medicion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CalendarioMedicion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codCalendarioMedicion;
	private LocalDate fechaCalendarioMedicion;
	private LocalDate fechaFinVigenciaCM;
	
	@ManyToOne
	@JoinColumn(name = "codMedicion")
	private Medicion medicion;
	
	public int getCodCalendarioMedicion() {
		return codCalendarioMedicion;
	}
	public void setCodCalendarioMedicion(int codCalendarioMedicion) {
		this.codCalendarioMedicion = codCalendarioMedicion;
	}
	public LocalDate getFechaCalendarioMedicion() {
		return fechaCalendarioMedicion;
	}
	public void setFechaCalendarioMedicion(LocalDate fechaCalendarioMedicion) {
		this.fechaCalendarioMedicion = fechaCalendarioMedicion;
	}
	public LocalDate getFechaFinVigenciaCM() {
		return fechaFinVigenciaCM;
	}
	public void setFechaFinVigenciaCM(LocalDate fechaFinVigenciaCM) {
		this.fechaFinVigenciaCM = fechaFinVigenciaCM;
	}
	
	@Override
	public String toString() {
		return "CalendarioMedicion [codCalendarioMedicion=" + codCalendarioMedicion + ", fechaCalendarioMedicion="
				+ fechaCalendarioMedicion + ", fechaFinVigenciaCM=" + fechaFinVigenciaCM + "]";
	}
	public Medicion getMedicion() {
		return medicion;
	}
	public void setMedicion(Medicion medicion) {
		this.medicion = medicion;
	}

}
