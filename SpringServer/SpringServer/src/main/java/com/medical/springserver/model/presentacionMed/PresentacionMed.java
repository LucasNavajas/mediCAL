package com.medical.springserver.model.presentacionMed;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medical.springserver.model.administracionmed.AdministracionMed;
import com.medical.springserver.model.recordatorio.Recordatorio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class PresentacionMed {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codPresentacionMed;
	private String descPresentacionMed;
	private LocalDate fechaAltaPresentacionMed;
	private LocalDate fechaFinVigenciaPM;
	private String nombrePresentacionMed;
	
	//relacion adm med
	@ManyToOne
	@JoinColumn(name = "codAdministracionMed")
	private AdministracionMed administracionMed;
	
	@OneToMany(mappedBy = "presentacionMed", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Recordatorio> recordatorios;
	
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
	public AdministracionMed getVaradministracionmed() {
		return administracionMed;
	}
	public void setVaradministracionmed(AdministracionMed varadministracionmed) {
		this.administracionMed = varadministracionmed;
	}

}
