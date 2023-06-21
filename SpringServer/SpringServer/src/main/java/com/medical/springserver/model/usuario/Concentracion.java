package com.medical.springserver.model.usuario;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//Comentario de prueba github
@Entity
public class Concentracion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codConcentracion;
	private String unidadMedidaC;
	private Float valorConcentracion;


	public int getCodConcentracion() {
		return codConcentracion;
	}


	public void setCodConcentracion(int codConcentracion) {
		this.codConcentracion = codConcentracion;
	}


	public String getUnidadMedidaC() {
		return unidadMedidaC;
	}


	public void setUnidadMedidaC(String unidadMedidaC) {
		this.unidadMedidaC = unidadMedidaC;
	}


	public Float getValorConcentracion() {
		return valorConcentracion;
	}


	public void setValorConcentracion(Float valorConcentracion) {
		this.valorConcentracion = valorConcentracion;
	}


	@Override
	public String toString() {
		return "Concentracion [codConcentracion=" + codConcentracion + ", unidadMedidaC=" +  unidadMedidaC + ",  valorConcentracion=" + valorConcentracion + "]";
	}
}
	
	