package com.medical.springserver.model.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//Comentario de prueba github
@Entity
public class TipoConsejo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int nroTipoConsejo;
	private String nombreTipoConsejo;


	public int getNroTipoConsejo() {
		return nroTipoConsejo;
	}


	public void setNroTipoConsejo(int nroTipoConsejo) {
		this.nroTipoConsejo = nroTipoConsejo;
	}


	public String getNombreTipoConsejo() {
		return nombreTipoConsejo;
	}


	public void setNombreTipoConsejo(String nombreTipoConsejo) {
		this.nombreTipoConsejo = nombreTipoConsejo;
	}


	@Override
	public String toString() {
		return "TipoConsejo [ nroTipoConsejo=" +  nroTipoConsejo + ", nombreTipoConsejo=" + nombreTipoConsejo + "]";
	}
	
	
}
