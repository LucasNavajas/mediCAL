package com.medical.springserver.model.estadosolicitud;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//Comentario de prueba github
@Entity
public class EstadoSolicitud {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codEstadoSolicitud;
	private String nombreEstadoSolicitud;

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
	
	
}