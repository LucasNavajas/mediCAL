package com.medical.springserver.model.tiporeporte;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//Comentario de prueba github
@Entity
public class TipoReporte {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codTipoReporte;
	private String nombreTipoReporte;

	public int getCodTipoReporte() {
		return codTipoReporte;
	}

	public void setCodTipoReporte(int codTipoReporte) {
		this.codTipoReporte = codTipoReporte;
	}


	public String getNombreTipoReporte() {
		return nombreTipoReporte;
	}


	public void setNombreTipoReporte(String nombreTipoReporte) {
		this.nombreTipoReporte = nombreTipoReporte;
	}


	@Override
	public String toString() {
		return "TipoReporte [codTipoReporte=" + codTipoReporte + ", nombreTipoReporte=" + nombreTipoReporte + "]";
	}
	
	
}
