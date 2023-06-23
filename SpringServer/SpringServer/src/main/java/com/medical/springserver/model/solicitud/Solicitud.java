package com.medical.springserver.model.solicitud;
import java.time.LocalDate;

import com.medical.springserver.model.estadosolicitud.EstadoSolicitud;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Solicitud {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codSolicitud;
	private LocalDate fechaSolicitud;
	
	@ManyToOne 
	@JoinColumn(name = "codEstadoSolicitud")
	private EstadoSolicitud estadoSolicitud;
	
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
		return "Solicitud [codSolicitud=" + codSolicitud + ", fechaSolicitud=" + fechaSolicitud + ", estadoSolicitud="
				+ estadoSolicitud + "]";
	}
	public EstadoSolicitud getEstadoSolicitud() {
		return estadoSolicitud;
	}
	public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}
}
	