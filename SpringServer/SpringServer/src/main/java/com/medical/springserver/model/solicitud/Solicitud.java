package com.medical.springserver.model.solicitud;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Solicitud {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codSolicitud;
	private LocalDate fechaSolicitud;
	public int getCodSolicitud() {
		return codSolicitud;
	}
	public void setCodSolicitud(int codSolicitud) {
		this.codSolicitud = codSolicitud;
	}
	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	@Override
	public String toString() {
		return "Solicitud [codSolicitud=" + codSolicitud + ", fechaSolicitud=" + fechaSolicitud + "]";
	}
}
	