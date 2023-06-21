package com.medical.springserver.model.perfil;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Perfil {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codPerfil;
	private String descPerfil;
	private LocalDate fechaAltaPerfil;
	private LocalDate fechaFinVigenciaP;
	private String motivoFinVigenciaP;
	private String nombrePerfil;

	

	public int getCodPerfil() {
		return codPerfil;
	}



	public void setCodPerfil(int codPerfil) {
		this.codPerfil = codPerfil;
	}



	public String getDescPerfil() {
		return descPerfil;
	}



	public void setDescPerfil(String descPerfil) {
		this.descPerfil = descPerfil;
	}



	public LocalDate getFechaAltaPerfil() {
		return fechaAltaPerfil;
	}



	public void setFechaAltaPerfil(LocalDate fechaAltaPerfil) {
		this.fechaAltaPerfil = fechaAltaPerfil;
	}



	public LocalDate getFechaFinVigenciaP() {
		return fechaFinVigenciaP;
	}



	public void setFechaFinVigenciaP(LocalDate fechaFinVigenciaP) {
		this.fechaFinVigenciaP = fechaFinVigenciaP;
	}



	public String getMotivoFinVigenciaP() {
		return motivoFinVigenciaP;
	}



	public void setMotivoFinVigenciaP(String motivoFinVigenciaP) {
		this.motivoFinVigenciaP = motivoFinVigenciaP;
	}



	public String getNombrePerfil() {
		return nombrePerfil;
	}



	public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
	}



	@Override
	public String toString() {
		return "Perfil [codPerfil=" + codPerfil + ", descPerfil=" + descPerfil + ", fechaAltaPerfil="
				+ fechaAltaPerfil + ", fechaFinVigenciaP=" + fechaFinVigenciaP + ", motivoFinVigenciaP=" 
				+ motivoFinVigenciaP + ", nombrePerfil=" + nombrePerfil + "]";
	}
	
	
}
