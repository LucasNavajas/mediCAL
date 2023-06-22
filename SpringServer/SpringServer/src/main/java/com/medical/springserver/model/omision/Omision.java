package com.medical.springserver.model.omision;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Omision {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codOmision;
	private String nombreOmision;
	
	public int getCodOmision() {
		return codOmision;
	}
	public void setCodOmision(int codOmision) {
		this.codOmision = codOmision;
	}
	public String getNombreOmision() {
		return nombreOmision;
	}
	public void setNombreOmision(String nombreOmision) {
		this.nombreOmision = nombreOmision;
	}
	@Override
	public String toString() {
		return "Omision [codOmision=" + codOmision + ", nombreOmision=" + nombreOmision + "]";
	}
}
