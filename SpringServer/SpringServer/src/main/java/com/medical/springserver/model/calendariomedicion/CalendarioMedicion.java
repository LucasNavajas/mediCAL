package com.medical.springserver.model.calendariomedicion;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CalendarioMedicion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codCalendarioMedicion;
	private LocalDate fechaCalendarioMedicion;
	private LocalDate fechaFinVigenciaCM;
	
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

}
