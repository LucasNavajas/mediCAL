package com.medical.springserver.model.faq;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.lang.Override;

//Comentario de prueba github
@Entity
public class FAQ {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codFAQ;
	private LocalDate fechaActualizacionFAQ;
	@Column(length = 1000)
	private String preguntatFAQ;
	@Column(length = 1000)
	private String respuestaFAQ;
	private LocalDate fechaFinVigenciaFAQ;


	public int getCodFAQ() {
		return codFAQ;
	}


	public void setCodFAQ(int codFAQ) {
		this.codFAQ = codFAQ;
	}


	public LocalDate getFechaActualizacionFAQ() {
		return fechaActualizacionFAQ;
	}


	public void setFechaActualizacionFAQ(LocalDate fechaActualizacionFAQ) {
		this.fechaActualizacionFAQ = fechaActualizacionFAQ;
	}


	public String getPreguntatFAQ() {
		return preguntatFAQ;
	}


	public void setPreguntatFAQ(String preguntatFAQ) {
		this.preguntatFAQ = preguntatFAQ;
	}


	public String getRespuestaFAQ() {
		return respuestaFAQ;
	}


	public void setRespuestaFAQ(String respuestaFAQ) {
		this.respuestaFAQ = respuestaFAQ;
	}


	@Override
	public String toString() {
		return "FAQ [codFAQ=" + codFAQ + ", fechaActualizacionFAQ=" + fechaActualizacionFAQ + ", preguntatFAQ="
				+ preguntatFAQ + ", respuestaFAQ=" + respuestaFAQ + ", fechaFinVigenciaFAQ=" + fechaFinVigenciaFAQ
				+ "]";
	}


	public LocalDate getFechaFinVigenciaFAQ() {
		return fechaFinVigenciaFAQ;
	}


	public void setFechaFinVigenciaFAQ(LocalDate fechaFinVigenciaFAQ) {
		this.fechaFinVigenciaFAQ = fechaFinVigenciaFAQ;
	}
}
	
	