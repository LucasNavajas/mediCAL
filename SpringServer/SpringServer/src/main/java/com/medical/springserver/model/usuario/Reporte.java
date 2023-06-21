package com.medical.springserver.model.usuario;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//Comentario de prueba github
@Entity
public class Reporte {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int nroReporte;
	private LocalDate fechaDesde;
	private LocalDate fechaHasta;
	private LocalDate fechaGenerada;

	public int getNroReporte() {
		return nroReporte;
	}


	public void setNroReporte(int nroReporte) {
		this.nroReporte = nroReporte;
	}

	public LocalDate getFechaDesde() {
		return fechaDesde;
	}


	public void setFechaDesde(LocalDate fechaDesde) {
		this.fechaDesde = fechaDesde;
	}


	public LocalDate getFechaHasta() {
		return fechaHasta;
	}


	public void setFechaHasta(LocalDate fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public LocalDate getFechaGenerada() {
		return fechaGenerada;
	}


	public void setFechaGenerada(LocalDate fechaGenerada) {
		this.fechaGenerada = fechaGenerada;
	}


	@Override
	public String toString() {
		return "Reporte [nroReporte=" + nroReporte + ", fechaDesde=" + fechaDesde + ",  fechaHasta=" + fechaHasta +  ",  fechaGenerada=" + fechaGenerada + "]";
	}
	
	
}