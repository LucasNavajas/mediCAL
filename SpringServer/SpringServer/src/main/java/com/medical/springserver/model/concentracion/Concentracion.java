package com.medical.springserver.model.concentracion;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	
	@OneToMany (mappedBy = "concentracion", cascade = CascadeType.ALL)
	@JsonIgnore
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




	@Override
	public String toString() {
		return "Concentracion [codConcentracion=" + codConcentracion + ", unidadMedidaC=" +  unidadMedidaC + "]";
	}


}
	
	