package com.example.medical.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Recordatorio {
    private int codRecordatorio;
    private int duracionRecordatorio;
    private LocalDate fechaAltaRecordatorio;
    private LocalDate fechaFinRecordatorio;
    private LocalDate fechaFinVigenciaR;
    private LocalDateTime fechaInicioRecordatorio;
    private int horarioRecordatorio;
    private String imagen;
    // Relacion con Calendario
    private Calendario calendario;

    private AdministracionMed administracionMed;

    private PresentacionMed presentacionMed;

    // Relacion con RegistroRecordatorio
   // private List<RegistroRecordatorio> registrorecordatorio;

    // Relacion con Inventario
    //private Inventario inventario;

    // Relacion con Instruccion
    //private Instruccion instruccion;

    // Relacion con Medicamento
    private Medicamento medicamento;

    // Relacion con Dosis
    //private Dosis dosis;

    // Relacion con Frecuencia
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
    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Recordatorio [codRecordatorio=" + codRecordatorio + ", duracionRecordatorio=" + duracionRecordatorio
                + ", fechaAltaRecordatorio=" + fechaAltaRecordatorio + ", fechaFinRecordatorio=" + fechaFinRecordatorio
                + ", fechaFinVigenciaR=" + fechaFinVigenciaR + ", fechaInicioRecordatorio=" + fechaInicioRecordatorio
                + ", horarioRecordatorio=" + horarioRecordatorio + ", calendario=" + calendario
                + ", registrorecordatorio=" + /*registrorecordatorio + ", inventario=" + inventario + ", instruccion="
                + instruccion + ", medicamento=" + medicamento + ", dosis=" + dosis + ", frecuencia=" + frecuencia
                + */"]";
    }

    // Relaciones

    /*public List<RegistroRecordatorio> getRegistroRecordatorio() {
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
    }*/
    public Frecuencia getFrecuencia() {
        return frecuencia;
    }
    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }
    /*public Inventario getInventario() {
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
    }*/
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
