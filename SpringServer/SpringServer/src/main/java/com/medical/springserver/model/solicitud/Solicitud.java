package com.medical.springserver.model.solicitud;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medical.springserver.model.estadosolicitud.EstadoSolicitud;
import com.medical.springserver.model.usuario.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Solicitud {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codSolicitud;
	private LocalDate fechaSolicitud;
	
	@ManyToOne(cascade = CascadeType.MERGE) 
	@JoinColumn(name = "codEstadoSolicitud")
	private EstadoSolicitud estadoSolicitud;
	
	//relacion con usuario controlador
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "codUsuarioControlador")
	private Usuario usuarioControlador;
	
    //relacion con usuario controlado
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "codUsuarioControlado")
    private Usuario usuarioControlado;
	
	public Usuario getUsuarioControlador() {
		return usuarioControlador;
	}
	public void setUsuarioControlador(Usuario usuarioControlador) {
		this.usuarioControlador = usuarioControlador;
	}
	public Usuario getUsuarioControlado() {
		return usuarioControlado;
	}
	public void setUsuarioControlado(Usuario usuarioControlado) {
		this.usuarioControlado = usuarioControlado;
	}
	
	
	
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
	