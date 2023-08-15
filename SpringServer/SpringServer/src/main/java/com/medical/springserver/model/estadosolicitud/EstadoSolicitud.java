package com.medical.springserver.model.estadosolicitud;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medical.springserver.model.solicitud.Solicitud;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
//Comentario de prueba github
@Entity
public class EstadoSolicitud {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codEstadoSolicitud;
	private String nombreEstadoSolicitud;

	@OneToMany (mappedBy = "estadoSolicitud", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Solicitud> solicitudes;
	
	
	public int getCodEstadoSolicitud() {
		return codEstadoSolicitud;
	}

	public void setCodEstadoSolicitud(int codEstadoSolicitud) {
		this.codEstadoSolicitud = codEstadoSolicitud;
	}


	public String getNombreEstadoSolicitud() {
		return nombreEstadoSolicitud;
	}


	public void setNombreEstadoSolicitud(String nombreEstadoSolicitud) {
		this.nombreEstadoSolicitud = nombreEstadoSolicitud;
	}


	@Override
	public String toString() {
		return "EstadoSolicitud [codEstadoSolicitud=" + codEstadoSolicitud + ", nombreEstadoSolicitud=" + nombreEstadoSolicitud + "]";
	}

	public List<Solicitud> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}
	
	
}