package com.medical.springserver.model.recordatorio;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.medical.springserver.model.administracionmed.AdministracionMed;
import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.dosis.Dosis;
import com.medical.springserver.model.frecuencia.Frecuencia;
import com.medical.springserver.model.instruccion.Instruccion;
import com.medical.springserver.model.inventario.Inventario;
import com.medical.springserver.model.medicamento.Medicamento;
import com.medical.springserver.model.presentacionMed.PresentacionMed;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
	private LocalDateTime fechaFinRecordatorio;
	private LocalDate fechaFinVigenciaR;
	private LocalDateTime fechaInicioRecordatorio;
	private int horarioRecordatorio;
	@Lob
    @Column(columnDefinition = "LONGBLOB")
    private String imagen;
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
	@OneToOne
	@JoinColumn (name = "codInstruccion")
	private Instruccion instruccion;
	
	// Relacion con Medicamento
	@ManyToOne
	@JoinColumn (name = "codMedicamento")
	private Medicamento medicamento;
	
	// Relacion con Dosis
	@OneToOne
	@JoinColumn (name = "codDosis")
	private Dosis dosis;
	
	// Relacion con Frecuencia
	@OneToOne
	@JoinColumn (name = "codFrecuencia")
	private Frecuencia frecuencia;
	
	@ManyToOne
	@JoinColumn (name ="codAdministracionMed")
	private AdministracionMed administracionMed;
	
	@ManyToOne
	@JoinColumn (name = "codPresentacionMed")
	private PresentacionMed presentacionMed;
	
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
	public LocalDateTime getFechaFinRecordatorio() {
		return fechaFinRecordatorio;
	}
	public void setFechaFinRecordatorio(LocalDateTime fechaFinRecordatorio) {
		this.fechaFinRecordatorio = fechaFinRecordatorio;
	}
	public LocalDate getFechaFinVigenciaR() {
		return fechaFinVigenciaR;
	}
	public void setFechaFinVigenciaR(LocalDate fechaFinVigenciaR) {
		this.fechaFinVigenciaR = fechaFinVigenciaR;
	}
	public LocalDateTime getFechaInicioRecordatorio() {
		return fechaInicioRecordatorio;
	}
	public void setFechaInicioRecordatorio(LocalDateTime fechaInicioRecordatorio) {
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
				+ instruccion + ", medicamento=" + medicamento +  ", frecuencia=" + frecuencia
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
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public AdministracionMed getAdministracionMed() {
		return administracionMed;
	}
	public void setAdministracionMed(AdministracionMed administracionMed) {
		this.administracionMed = administracionMed;
	}
	public PresentacionMed getPresentacionMed() {
		return presentacionMed;
	}
	public void setPresentacionMed(PresentacionMed presentacionMed) {
		this.presentacionMed = presentacionMed;
	}	
	
}
