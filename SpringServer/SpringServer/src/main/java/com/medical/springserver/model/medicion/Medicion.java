package com.medical.springserver.model.medicion;
import java.time.LocalDate;
import java.util.List;

import com.medical.springserver.model.calendariomedicion.CalendarioMedicion;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Medicion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codMedicion;
	private LocalDate fechaAltaMedicion;
	private LocalDate fechaFinVigenciaM;
	private String nombreMedicion;
	private String unidadMedidaMedicion;
	
	// Relacion con CalendarioMedicion
	@OneToMany (mappedBy = "medicion", cascade = CascadeType.ALL)
	private List<CalendarioMedicion> calendariomed;
	
	public List<CalendarioMedicion> getCalendariomed() {
		return calendariomed;
	}
	public void setCalendariomed(List<CalendarioMedicion> calendariomed) {
		this.calendariomed = calendariomed;
	}
	public int getCodMedicion() {
		return codMedicion;
	}
	public void setCodMedicion(int codMedicion) {
		this.codMedicion = codMedicion;
	}
	public LocalDate getFechaAltaMedicion() {
		return fechaAltaMedicion;
	}
	public void setFechaAltaMedicion(LocalDate fechaAltaMedicion) {
		this.fechaAltaMedicion = fechaAltaMedicion;
	}
	public LocalDate getFechaFinVigenciaM() {
		return fechaFinVigenciaM;
	}
	public void setFechaFinVigenciaM(LocalDate fechaFinVigenciaM) {
		this.fechaFinVigenciaM = fechaFinVigenciaM;
	}
	public String getNombreMedicion() {
		return nombreMedicion;
	}
	public void setNombreMedicion(String nombreMedicion) {
		this.nombreMedicion = nombreMedicion;
	}
	public String getUnidadMedidaMedicion() {
		return unidadMedidaMedicion;
	}
	public void setUnidadMedidaMedicion(String unidadMedidaMedicion) {
		this.unidadMedidaMedicion = unidadMedidaMedicion;
	}
	
	@Override
	public String toString() {
		return "Medicion [codMedicion=" + codMedicion + ", fechaAltaMedicion=" + fechaAltaMedicion
				+ ", fechaFinVigenciaM=" + fechaFinVigenciaM + ", nombreMedicion=" + nombreMedicion
				+ ", unidadMedidaMedicion=" + unidadMedidaMedicion + "]";
	}
	
}
