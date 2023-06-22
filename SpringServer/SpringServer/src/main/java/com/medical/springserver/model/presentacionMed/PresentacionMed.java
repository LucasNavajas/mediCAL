package com.medical.springserver.model.presentacionMed;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PresentacionMed {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codPresentacionMed;
	private String descPresentacionMed;
	private LocalDate fechaAltaPresentacionMed;
	private LocalDate fechaFinVigenciaPM;
	private String nombrePresentacionMed;
	public int getCodPresentacionMed() {
		return codPresentacionMed;
	}
	public void setCodPresentacionMed(int codPresentacionMed) {
		this.codPresentacionMed = codPresentacionMed;
	}
	public String getDescPresentacionMed() {
		return descPresentacionMed;
	}
	public void setDescPresentacionMed(String descPresentacionMed) {
		this.descPresentacionMed = descPresentacionMed;
	}
	public LocalDate getFechaAltaPresentacionMed() {
		return fechaAltaPresentacionMed;
	}
	public void setFechaAltaPresentacionMed(LocalDate fechaAltaPresentacionMed) {
		this.fechaAltaPresentacionMed = fechaAltaPresentacionMed;
	}
	public LocalDate getFechaFinVigenciaPM() {
		return fechaFinVigenciaPM;
	}
	public void setFechaFinVigenciaPM(LocalDate fechaFinVigenciaPM) {
		this.fechaFinVigenciaPM = fechaFinVigenciaPM;
	}
	public String getNombrePresentacionMed() {
		return nombrePresentacionMed;
	}
	public void setNombrePresentacionMed(String nombrePresentacionMed) {
		this.nombrePresentacionMed = nombrePresentacionMed;
	}
	@Override
	public String toString() {
		return "PresentacionMed [codPresentacionMed=" + codPresentacionMed + ", descPresentacionMed="
				+ descPresentacionMed + ", fechaAltaPresentacionMed=" + fechaAltaPresentacionMed
				+ ", fechaFinVigenciaPM=" + fechaFinVigenciaPM + ", nombrePresentacionMed=" + nombrePresentacionMed
				+ "]";
	}
}
