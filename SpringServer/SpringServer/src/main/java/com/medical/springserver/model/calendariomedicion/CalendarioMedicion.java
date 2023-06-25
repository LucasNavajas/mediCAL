package com.medical.springserver.model.calendariomedicion;
import java.time.LocalDate;

import com.medical.springserver.model.calendario.Calendario;
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
	private Float valorCalendarioMedicion;
	
	// Relacion con Calendario
	@ManyToOne
	@JoinColumn (name = "codCalendario")
	private Calendario calendario;
	
	// Relacion con Medicion
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
	public float getValorCalendarioMedicion() {
		return valorCalendarioMedicion;
	}
	public void setValorCalendarioMedicion(float valorCalendarioMedicion) {
		this.valorCalendarioMedicion = valorCalendarioMedicion;
	}
	
	@Override
	public String toString() {
		return "CalendarioMedicion [codCalendarioMedicion=" + codCalendarioMedicion + ", fechaCalendarioMedicion="
				+ fechaCalendarioMedicion + ", fechaFinVigenciaCM=" + fechaFinVigenciaCM + ", calendario=" + calendario
				+ ", medicion=" + medicion + "]";
	}
	
	// Relacion
	
	public Medicion getMedicion() {
		return medicion;
	}
	public void setMedicion(Medicion medicion) {
		this.medicion = medicion;
	}
	public Calendario getCalendario() {
		return calendario;
	}
	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}

}
