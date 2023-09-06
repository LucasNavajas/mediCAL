package com.medical.springserver.model.instruccion;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medical.springserver.model.recordatorio.Recordatorio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Instruccion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codInstruccion;
	private String descInstruccion;
	private String nombreInstruccion;
	
	// Relacion con Recordatorio
	@OneToOne (mappedBy = "instruccion", cascade = CascadeType.ALL)
	@JsonIgnore
	private Recordatorio recordatorio;
	
	public int getCodInstruccion() {
		return codInstruccion;
	}
	public void setCodInstruccion(int codInstruccion) {
		this.codInstruccion = codInstruccion;
	}
	public String getDescInstruccion() {
		return descInstruccion;
	}
	public void setDescInstruccion(String descInstruccion) {
		this.descInstruccion = descInstruccion;
	}
	public String getNombreInstruccion() {
		return nombreInstruccion;
	}
	public void setNombreInstruccion(String nombreInstruccion) {
		this.nombreInstruccion = nombreInstruccion;
	}
	
	@Override
	public String toString() {
		return "Instruccion [codInstruccion=" + codInstruccion + ", descInstruccion=" + descInstruccion
				+ ", nombreInstruccion=" + nombreInstruccion + ", recordatorio=" + recordatorio + "]";
	}
	
	// Relacion
	
	public Recordatorio getRecordatorio() {
		return recordatorio;
	}
	public void setRecordatorio(Recordatorio recordatorio) {
		this.recordatorio = recordatorio;
	}

}
