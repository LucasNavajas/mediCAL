package com.medical.springserver.model.tiporeporte;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medical.springserver.model.reporte.Reporte;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
//Comentario de prueba github
@Entity
public class TipoReporte {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codTipoReporte;
	private String nombreTipoReporte;
	
	@OneToMany (mappedBy = "tipoReporte")
	@JsonIgnore
	private List<Reporte> reportes;

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

	public List<Reporte> getReportes() {
		return reportes;
	}

	public void setReportes(List<Reporte> reportes) {
		this.reportes = reportes;
	}
	
	
}
