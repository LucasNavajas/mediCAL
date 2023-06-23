package com.medical.springserver.model.concentracion;



import java.util.List;

import com.medical.springserver.model.dosis.Dosis;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
//Comentario de prueba github
@Entity
public class Concentracion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codConcentracion;
	private String unidadMedidaC;
	private Float valorConcentracion;
	
	@OneToMany (mappedBy = "concentracion", cascade = CascadeType.ALL)
	private List<Dosis> dosis;


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


	public List<Dosis> getDosis() {
		return dosis;
	}


	public void setDosis(List<Dosis> dosis) {
		this.dosis = dosis;
	}
}
	
	