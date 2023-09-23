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
	private int codInventario;
	private Integer cantAvisoInventario;
	private Integer cantRealInventario;
	
	// Relacion con Recordatorio
		
	
	public int getCodInventario() {
		return codInventario;
	}
	
	public Integer getCantAvisoInventario() {
		return cantAvisoInventario;
	}

	public void setCantAvisoInventario(Integer cantAvisoInventario) {
		this.cantAvisoInventario = cantAvisoInventario;
	}

	public Integer getCantRealInventario() {
		return cantRealInventario;
	}

	public void setCantRealInventario(Integer cantRealInventario) {
		this.cantRealInventario = cantRealInventario;
	}

	@Override
	public String toString() {
		return "Inventario [cantAvisoInventario=" + cantAvisoInventario + ", cantRealInventario=" + cantRealInventario
				+  "]";
	}


	
}
