package com.medical.springserver.model.medicamento;
import java.time.LocalDate;


import com.medical.springserver.model.administracionmed.AdministracionMed;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Medicamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codMedicamento;
	private boolean esParticular;
	private LocalDate fechaAltaMedicamento;
	private LocalDate fechaFinVigenciaMed;
	private String marcaMedicamento;
	private String nombreMedicamento;
	
	//relacion adm medica
	@ManyToOne
	@JoinColumn(name = "codAdministracionMed")
	private AdministracionMed administracionmed;
	
	public int getCodMedicamento() {
		return codMedicamento;
	}
	public void setCodMedicamento(int codMedicamento) {
		this.codMedicamento = codMedicamento;
	}
	public boolean isEsParticular() {
		return esParticular;
	}
	public void setEsParticular(boolean esParticular) {
		this.esParticular = esParticular;
	}
	public LocalDate getFechaAltaMedicamento() {
		return fechaAltaMedicamento;
	}
	public void setFechaAltaMedicamento(LocalDate fechaAltaMedicamento) {
		this.fechaAltaMedicamento = fechaAltaMedicamento;
	}
	public LocalDate getFechaFinVigenciaMed() {
		return fechaFinVigenciaMed;
	}
	public void setFechaFinVigenciaMed(LocalDate fechaFinVigenciaMed) {
		this.fechaFinVigenciaMed = fechaFinVigenciaMed;
	}
	public String getMarcaMedicamento() {
		return marcaMedicamento;
	}
	public void setMarcaMedicamento(String marcaMedicamento) {
		this.marcaMedicamento = marcaMedicamento;
	}
	public String getNombreMedicamento() {
		return nombreMedicamento;
	}
	public void setNombreMedicamento(String nombreMedicamento) {
		this.nombreMedicamento = nombreMedicamento;
	}
	@Override
	public String toString() {
		return "Medicamento [codMedicamento=" + codMedicamento + ", esParticular=" + esParticular
				+ ", fechaAltaMedicamento=" + fechaAltaMedicamento + ", fechaFinVigenciaMed=" + fechaFinVigenciaMed
				+ ", marcaMedicamento=" + marcaMedicamento + ", nombreMedicamento=" + nombreMedicamento + "]";
	}
	public AdministracionMed getAdministracionmed() {
		return administracionmed;
	}
	public void setAdministracionmed(AdministracionMed administracionmed) {
		this.administracionmed = administracionmed;
	}
	
	

}