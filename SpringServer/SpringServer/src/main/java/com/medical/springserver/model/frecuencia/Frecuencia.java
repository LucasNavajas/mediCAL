package com.medical.springserver.model.frecuencia;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Frecuencia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codFrecuencia;
	private int cantidadFrecuencia;
	private int diasDescansoF;
	private int diasTomaF;
	private String nombreFrecuencia;
	public int getCantidadFrecuencia() {
		return cantidadFrecuencia;
	}
	public void setCantidadFrecuencia(int cantidadFrecuencia) {
		this.cantidadFrecuencia = cantidadFrecuencia;
	}
	public int getDiasDescansoF() {
		return diasDescansoF;
	}
	public void setDiasDescansoF(int diasDescansoF) {
		this.diasDescansoF = diasDescansoF;
	}
	public int getDiasTomaF() {
		return diasTomaF;
	}
	public void setDiasTomaF(int diasTomaF) {
		this.diasTomaF = diasTomaF;
	}
	public String getNombreFrecuencia() {
		return nombreFrecuencia;
	}
	public void setNombreFrecuencia(String nombreFrecuencia) {
		this.nombreFrecuencia = nombreFrecuencia;
	}
	@Override
	public String toString() {
		return "Frecuencia [codFrecuencia=" + codFrecuencia + ", cantidadFrecuencia=" + cantidadFrecuencia
				+ ", diasDescansoF=" + diasDescansoF + ", diasTomaF=" + diasTomaF + ", nombreFrecuencia="
				+ nombreFrecuencia + "]";
	}
	public int getCodFrecuencia() {
		return codFrecuencia;
	}
	public void setCodFrecuencia(int codFrecuencia) {
		this.codFrecuencia = codFrecuencia;
	}
	
}
