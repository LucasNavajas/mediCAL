package com.medical.springserver.model.dosis;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Dosis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int codDosis;
	int cantidadDosis;
	public int getCantidadDosis() {
		return cantidadDosis;
	}
	public void setCantidadDosis(int cantidadDosis) {
		this.cantidadDosis = cantidadDosis;
	}
	@Override
	public String toString() {
		return "Dosis [codDosis=" + codDosis + ", cantidadDosis=" + cantidadDosis + "]";
	}
	public int getCodDosis() {
		return codDosis;
	}
	public void setCodDosis(int codDosis) {
		this.codDosis = codDosis;
	}
	
}
