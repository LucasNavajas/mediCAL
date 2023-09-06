package com.medical.springserver.model.dosis;

import java.util.List;

import com.medical.springserver.model.concentracion.Concentracion;
import com.medical.springserver.model.recordatorio.Recordatorio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
@Entity
public class Dosis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int codDosis;
	float cantidadDosis;
	float valorConcentracion;
	@ManyToOne
	@JoinColumn(name = "codConcentraion")
	private Concentracion concentracion;
	
	@OneToOne (mappedBy = "dosis")
	private Recordatorio recordatorio;
	
	 
	public float getCantidadDosis() {
		return cantidadDosis;
	}
	public void setCantidadDosis(float cantidadDosis) {
		this.cantidadDosis = cantidadDosis;
	}
	@Override
	public String toString() {
		return "Dosis [codDosis=" + codDosis + ", cantidadDosis=" + cantidadDosis + ", concentracion=" + concentracion
				+ ", recordatorio=" + recordatorio + ", valorConcentracion"+ valorConcentracion+ "]";
	}
	public int getCodDosis() {
		return codDosis;
	}
	public void setCodDosis(int codDosis) {
		this.codDosis = codDosis;
	}
	public Concentracion getConcentracion() {
		return concentracion;
	}
	public void setConcentracion(Concentracion concentracion) {
		this.concentracion = concentracion;
	}
	public Recordatorio getRecordatorios() {
		return recordatorio;
	}
	public void setRecordatorios(Recordatorio recordatorio) {
		this.recordatorio = recordatorio;
	}
	public float getValorConcentracion() {
		return valorConcentracion;
	}

	public void setValorConcentracion(float valorConcentracion) {
		this.valorConcentracion = valorConcentracion;
	}

	
}
