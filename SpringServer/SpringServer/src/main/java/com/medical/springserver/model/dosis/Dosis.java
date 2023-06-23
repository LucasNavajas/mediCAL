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
@Entity
public class Dosis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int codDosis;
	int cantidadDosis;
	@ManyToOne
	@JoinColumn(name = "codConcentraion")
	private Concentracion concentracion;
	
	@OneToMany (mappedBy = "dosis")
	private List<Recordatorio> recordatorios;
	
	 
	public int getCantidadDosis() {
		return cantidadDosis;
	}
	public void setCantidadDosis(int cantidadDosis) {
		this.cantidadDosis = cantidadDosis;
	}
	@Override
	public String toString() {
		return "Dosis [codDosis=" + codDosis + ", cantidadDosis=" + cantidadDosis + ", concentracion=" + concentracion
				+ ", recordatorios=" + recordatorios + "]";
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
	public List<Recordatorio> getRecordatorios() {
		return recordatorios;
	}
	public void setRecordatorios(List<Recordatorio> recordatorios) {
		this.recordatorios = recordatorios;
	}
	
}
