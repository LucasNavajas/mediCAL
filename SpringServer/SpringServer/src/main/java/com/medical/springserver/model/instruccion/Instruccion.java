package com.medical.springserver.model.instruccion;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Instruccion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codInstruccion;
	private String descInstruccion;
	private String nombreInstruccion;
	
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
				+ ", nombreInstruccion=" + nombreInstruccion + "]";
	}

}
