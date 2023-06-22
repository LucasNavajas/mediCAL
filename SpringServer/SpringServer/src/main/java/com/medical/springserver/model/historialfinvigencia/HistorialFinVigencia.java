package com.medical.springserver.model.historialfinvigencia;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class HistorialFinVigencia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int nroHistorialFV;
	private LocalDate fechaDesdeFV;
	private LocalDate fechaHastaFV;
	private String motivoFV;
	public LocalDate getFechaDesdeFV() {
		return fechaDesdeFV;
	}
	public void setFechaDesdeFV(LocalDate fechaDesdeFV) {
		this.fechaDesdeFV = fechaDesdeFV;
	}
	public LocalDate getFechaHastaFV() {
		return fechaHastaFV;
	}
	public void setFechaHastaFV(LocalDate fechaHastaFV) {
		this.fechaHastaFV = fechaHastaFV;
	}
	public String getMotivoFV() {
		return motivoFV;
	}
	public void setMotivoFV(String motivoFV) {
		this.motivoFV = motivoFV;
	}
	public int getNroHistorialFV() {
		return nroHistorialFV;
	}
	public void setNroHistorialFV(int nroHistorialFV) {
		this.nroHistorialFV = nroHistorialFV;
	}
	@Override
	public String toString() {
		return "HistorialFinVigencia [fechaDesdeFV=" + fechaDesdeFV + ", fechaHastaFV=" + fechaHastaFV + ", motivoFV="
				+ motivoFV + ", nroHistorialFV=" + nroHistorialFV + "]";
	}
	
	
}
