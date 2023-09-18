package com.medical.springserver.model.registroRecordatorio;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.medical.springserver.model.omision.Omision;
import com.medical.springserver.model.recordatorio.Recordatorio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class RegistroRecordatorio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codRegistroRecordatorio;
	private LocalDate fechaFinVigenciaRR;
	private LocalDateTime fechaTomaEsperada;
	private LocalDateTime fechaTomaReal;
	private int nroRegistro; 
	private boolean tomaRegistroRecordatorio;
	
	// Relacion con Omision
	@OneToOne
	@JoinColumn(name = "codOmision")
	private Omision omision;
	
	// Relacion con Recordatorio
	@ManyToOne
	@JoinColumn(name = "codRecordatorio")
	private Recordatorio recordatorio;
	
	public int getCodRegistroRecordatorio() {
		return codRegistroRecordatorio;
	}
	public void setCodRegistroRecordatorio(int codRegistroRecordatorio) {
		this.codRegistroRecordatorio = codRegistroRecordatorio;
	}
	public LocalDate getFechaFinVigenciaRR() {
		return fechaFinVigenciaRR;
	}
	public void setFechaFinVigenciaRR(LocalDate fechaFinVigenciaRR) {
		this.fechaFinVigenciaRR = fechaFinVigenciaRR;
	}
	public LocalDateTime getFechaTomaEsperada() {
		return fechaTomaEsperada;
	}
	public void setFechaTomaEsperada(LocalDateTime fechaTomaEsperada) {
		this.fechaTomaEsperada = fechaTomaEsperada;
	}
	public LocalDateTime getFechaTomaReal() {
		return fechaTomaReal;
	}
	public void setFechaTomaReal(LocalDateTime fechaTomaReal) {
		this.fechaTomaReal = fechaTomaReal;
	}
	public int getNroRegistro() {
		return nroRegistro;
	}
	public void setNroRegistro(int nroRegistro) {
		this.nroRegistro = nroRegistro;
	}
	public boolean isTomaRegistroRecordatorio() {
		return tomaRegistroRecordatorio;
	}
	public void setTomaRegistroRecordatorio(boolean tomaRegistroRecordatorio) {
		this.tomaRegistroRecordatorio = tomaRegistroRecordatorio;
	}
	
	@Override
	public String toString() {
		return "RegistroRecordatorio [codRegistroRecordatorio=" + codRegistroRecordatorio + ", fechaFinVigenciaRR="
				+ fechaFinVigenciaRR + ", fechaTomaEsperada=" + fechaTomaEsperada + ", fechaTomaReal=" + fechaTomaReal
				+ ", nroRegistro=" + nroRegistro + ", tomaRegistroRecordatorio=" + tomaRegistroRecordatorio
				+ ", omision=" + omision + ", recordatorio=" + recordatorio + "]";
	}
	
	// Relaciones
	
	public Omision getOmision() {
		return omision;
	}
	public void setOmision(Omision omision) {
		this.omision = omision;
	}
	public Recordatorio getRecordatorio() {
		return recordatorio;
	}
	public void setRecordatorio(Recordatorio recordatorio) {
		this.recordatorio = recordatorio;
	}
	
}
