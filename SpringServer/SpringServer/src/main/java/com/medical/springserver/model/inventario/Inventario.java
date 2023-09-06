package com.medical.springserver.model.inventario;

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
public class Inventario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cantAvisoInventario;
	private int cantRealInventario;
	
	// Relacion con Recordatorio
	@OneToOne (mappedBy = "inventario", cascade = CascadeType.ALL)
	@JsonIgnore
	private Recordatorio recordatorio;
		
	public int getCantAvisoInventario() {
		return cantAvisoInventario;
	}

	public void setCantAvisoInventario(int cantAvisoInventario) {
		this.cantAvisoInventario = cantAvisoInventario;
	}

	public int getCantRealInventario() {
		return cantRealInventario;
	}

	public void setCantRealInventario(int cantRealInventario) {
		this.cantRealInventario = cantRealInventario;
	}

	@Override
	public String toString() {
		return "Inventario [cantAvisoInventario=" + cantAvisoInventario + ", cantRealInventario=" + cantRealInventario
				+ ", recordatorio=" + recordatorio + "]";
	}

	// Relaciones
	
	public Recordatorio getRecordatorio() {
		return recordatorio;
	}

	public void setRecordatorio(Recordatorio recordatorio) {
		this.recordatorio = recordatorio;
	}
	
}
