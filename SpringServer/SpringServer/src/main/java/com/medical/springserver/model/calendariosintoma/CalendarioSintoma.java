package com.medical.springserver.model.calendariosintoma;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.sintoma.Sintoma;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CalendarioSintoma {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codCalendarioSintoma;
	private LocalDateTime fechaCalendarioSintoma;
	private LocalDate fechaFinVigenciaCS;
	
	// Relacion con Calendario
	@ManyToOne
	@JoinColumn(name = "codCalendario")
	private Calendario calendario;
	
	// Relacion con Sintoma
	@ManyToOne
	@JoinColumn(name = "codSintoma")
	private Sintoma sintoma;
	
	public int getCodCalendarioSintoma() {
		return codCalendarioSintoma;
	}

	public void setCodCalendarioSintoma(int codCalendarioSintoma) {
		this.codCalendarioSintoma = codCalendarioSintoma;
	}

	public LocalDateTime getFechaCalendarioSintoma() {
		return fechaCalendarioSintoma;
	}

	public void setFechaCalendarioSintoma(LocalDateTime fechaCalendarioSintoma) {
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

	public Calendario getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}

	public Sintoma getSintoma() {
		return sintoma;
	}

	public void setSintoma(Sintoma sintoma) {
		this.sintoma = sintoma;
	}
	
	
}