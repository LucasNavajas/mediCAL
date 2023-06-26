package com.medical.springserver.model.recordatorio;
import java.time.LocalDate;
import java.util.List;

import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.dosis.Dosis;
import com.medical.springserver.model.frecuencia.Frecuencia;
import com.medical.springserver.model.instruccion.Instruccion;
import com.medical.springserver.model.inventario.Inventario;
import com.medical.springserver.model.medicamento.Medicamento;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Recordatorio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codRecordatorio;
	private int duracionRecordatorio;
	private LocalDate fechaAltaRecordatorio;
	private LocalDate fechaFinRecordatorio;
	private LocalDate fechaFinVigenciaR;
	private LocalDate fechaInicioRecordatorio;
	private int horarioRecordatorio;
	
	// Relacion con Calendario
	@ManyToOne
	@JoinColumn (name = "codCalendario")
	private Calendario calendario;
	
	// Relacion con RegistroRecordatorio
	@OneToMany (mappedBy = "recordatorio", cascade = CascadeType.ALL)
	private List<RegistroRecordatorio> registrorecordatorio;
	
	// Relacion con Inventario
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn (name = "codInventario")
	private Inventario inventario;
	
	// Relacion con Instruccion
	@ManyToOne
	@JoinColumn (name = "codInstruccion")
	private Instruccion instruccion;
	
	// Relacion con Medicamento
	@ManyToOne
	@JoinColumn (name = "codMedicamento")
	private Medicamento medicamento;
	
	// Relacion con Dosis
	@ManyToOne
	@JoinColumn (name = "codDosis")
	private Dosis dosis;
	
	// Relacion con Frecuencia
	@ManyToOne
	@JoinColumn (name = "codFrecuencia")
	private Frecuencia frecuencia;
	
	public int getCodRecordatorio() {
		return codRecordatorio;
	}
	public void setCodRecordatorio(int codRecordatorio) {
		this.codRecordatorio = codRecordatorio;
	}
	public int getDuracionRecordatorio() {
		return duracionRecordatorio;
	}
	public void setDuracionRecordatorio(int duracionRecordatorio) {
		this.duracionRecordatorio = duracionRecordatorio;
	}
	public LocalDate getFechaAltaRecordatorio() {
		return fechaAltaRecordatorio;
	}
	public void setFechaAltaRecordatorio(LocalDate fechaAltaRecordatorio) {
		this.fechaAltaRecordatorio = fechaAltaRecordatorio;
	}
	public LocalDate getFechaFinRecordatorio() {
		return fechaFinRecordatorio;
	}
	public void setFechaFinRecordatorio(LocalDate fechaFinRecordatorio) {
		this.fechaFinRecordatorio = fechaFinRecordatorio;
	}
	public LocalDate getFechaFinVigenciaR() {
		return fechaFinVigenciaR;
	}
	public void setFechaFinVigenciaR(LocalDate fechaFinVigenciaR) {
		this.fechaFinVigenciaR = fechaFinVigenciaR;
	}
	public LocalDate getFechaInicioRecordatorio() {
		return fechaInicioRecordatorio;
	}
	public void setFechaInicioRecordatorio(LocalDate fechaInicioRecordatorio) {
		this.fechaInicioRecordatorio = fechaInicioRecordatorio;
	}
	public int getHorarioRecordatorio() {
		return horarioRecordatorio;
	}
	public void setHorarioRecordatorio(int horarioRecordatorio) {
		this.horarioRecordatorio = horarioRecordatorio;
	}
	
	@Override
	public String toString() {
		return "Recordatorio [codRecordatorio=" + codRecordatorio + ", duracionRecordatorio=" + duracionRecordatorio
				+ ", fechaAltaRecordatorio=" + fechaAltaRecordatorio + ", fechaFinRecordatorio=" + fechaFinRecordatorio
				+ ", fechaFinVigenciaR=" + fechaFinVigenciaR + ", fechaInicioRecordatorio=" + fechaInicioRecordatorio
				+ ", horarioRecordatorio=" + horarioRecordatorio + ", calendario=" + calendario
				+ ", registrorecordatorio=" + registrorecordatorio + ", inventario=" + inventario + ", instruccion="
				+ instruccion + ", medicamento=" + medicamento + ", dosis=" + dosis + ", frecuencia=" + frecuencia
				+ "]";
	}
	
	// Relaciones
	
	public List<RegistroRecordatorio> getRegistroRecordatorio() {
		return registrorecordatorio;
	}
	public void setRegistroRecordatorio(List<RegistroRecordatorio> registrorecordatorio) {
		this.registrorecordatorio = registrorecordatorio;
	}
	public Dosis getDosis() {
		return dosis;
	}
	public void setDosis(Dosis dosis) {
		this.dosis = dosis;
	}
	public Frecuencia getFrecuencia() {
		return frecuencia;
	}
	public void setFrecuencia(Frecuencia frecuencia) {
		this.frecuencia = frecuencia;
	}
	public Inventario getInventario() {
		return inventario;
	}
	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}
	public Instruccion getInstruccion() {
		return instruccion;
	}
	public void setInstruccion(Instruccion instruccion) {
		this.instruccion = instruccion;
	}
	public Medicamento getMedicamento() {
		return medicamento;
	}
	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}
	public Calendario getCalendario() {
		return calendario;
	}
	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}	
	
}
