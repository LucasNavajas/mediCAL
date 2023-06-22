package com.medical.springserver.model.codigoverificacion;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CodigoVerificacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codVerificacion;
	private LocalDate fechaGenerado;
	public int getCodVerificacion() {
		return codVerificacion;
	}
	public void setCodVerificacion(int codVerificacion) {
		this.codVerificacion = codVerificacion;
	}
	public LocalDate getFechaGenerado() {
		return fechaGenerado;
	}
	public void setFechaGenerado(LocalDate fechaGenerado) {
		this.fechaGenerado = fechaGenerado;
	}
	@Override
	public String toString() {
		return "CodigoVerificacion [codVerificacion=" + codVerificacion + ", fechaGenerado=" + fechaGenerado + "]";
	}
	
}
	