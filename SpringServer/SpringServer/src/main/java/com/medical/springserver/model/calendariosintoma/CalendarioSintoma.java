package com.medical.springserver.model.calendariosintoma;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CalendarioSintoma {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codCalendarioSintoma;
	private LocalDate fechaCalendarioSintoma;
	private LocalDate fechaFinVigenciaCS;
		
	public int getCodCalendarioSintoma() {
		return codCalendarioSintoma;
	}

	public void setCodCalendarioSintoma(int codCalendarioSintoma) {
		this.codCalendarioSintoma = codCalendarioSintoma;
	}

	public LocalDate getFechaCalendarioSintoma() {
		return fechaCalendarioSintoma;
	}

	public void setFechaCalendarioSintoma(LocalDate fechaCalendarioSintoma) {
		this.fechaCalendarioSintoma = fechaCalendarioSintoma;
	}

	public LocalDate getFechaFinVigenciaCS() {
		return fechaFinVigenciaCS;
	}

	public void setFechaFinVigenciaCS(LocalDate fechaFinVigenciaCS) {
		this.fechaFinVigenciaCS = fechaFinVigenciaCS;
	}

	@Override
	public String toString() {
		return "CalendarioSintoma [codCalendarioSintoma=" + codCalendarioSintoma + ", fechaCalendarioSintoma=" + fechaCalendarioSintoma 
				+ ", fechaFinVigenciaCS=" + fechaFinVigenciaCS + "]";
	}
}