package com.medical.springserver.model.registroRecordatorio;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RegistroRecordatorio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codRegistroRecordatorio;
	private LocalDate fechaFinVigenciaRR;
	private LocalDateTime fechaTomaEsperada;
	private LocalDateTime fechaTomaReal;
	private int nroRegistro; //Para que servia este numero?
	private boolean tomaRegistroRecordatorio;
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
				+ ", nroRegistro=" + nroRegistro + ", tomaRegistroRecordatorio=" + tomaRegistroRecordatorio + "]";
	}
	
}
