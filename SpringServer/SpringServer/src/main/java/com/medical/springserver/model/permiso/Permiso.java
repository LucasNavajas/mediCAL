package com.medical.springserver.model.permiso;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Permiso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codPermiso;
	private String descPermiso;
	private LocalDate fechaAltaPermiso;
	private LocalDate fechaFinVigenciaP;
	private String nombrePermiso;
	

	public int getCodPermiso() {
		return codPermiso;
	}


	public void setCodPermiso(int codPermiso) {
		this.codPermiso = codPermiso;
	}


	public String getDescPermiso() {
		return descPermiso;
	}


	public void setDescPermiso(String descPermiso) {
		this.descPermiso = descPermiso;
	}


	public LocalDate getFechaAltaPermiso() {
		return fechaAltaPermiso;
	}


	public void setFechaAltaPermiso(LocalDate fechaAltaPermiso) {
		this.fechaAltaPermiso = fechaAltaPermiso;
	}


	public LocalDate getFechaFinVigenciaP() {
		return fechaFinVigenciaP;
	}


	public void setFechaFinVigenciaP(LocalDate fechaFinVigenciaP) {
		this.fechaFinVigenciaP = fechaFinVigenciaP;
	}


	public String getNombrePermiso() {
		return nombrePermiso;
	}


	public void setNombrePermiso(String nombrePermiso) {
		this.nombrePermiso = nombrePermiso;
	}


	@Override
	public String toString() {
		return "Permiso [codPermiso=" + codPermiso + ", descPermiso=" + descPermiso + ", fechaAltaPermiso="
				+ fechaAltaPermiso + ", fechaFinVigenciaP=" + fechaFinVigenciaP + "]";
	}
	
	
}
