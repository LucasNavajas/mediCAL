package com.example.medical.model;

import java.time.LocalDate;
import java.util.List;

public class Medicamento {
    private int codMedicamento;
    private boolean esParticular;
    private LocalDate fechaAltaMedicamento;
    private LocalDate fechaFinVigenciaMed;
    private String marcaMedicamento;
    private String nombreMedicamento;

    // Relacion con Recordatorio
    /*private List<Recordatorio> recordatorio;*/


    public int getCodMedicamento() {
        return codMedicamento;
    }
    public void setCodMedicamento(int codMedicamento) {
        this.codMedicamento = codMedicamento;
    }
    public boolean isEsParticular() {
        return esParticular;
    }
    public void setEsParticular(boolean esParticular) {
        this.esParticular = esParticular;
    }
    public LocalDate getFechaAltaMedicamento() {
        return fechaAltaMedicamento;
    }
    public void setFechaAltaMedicamento(LocalDate fechaAltaMedicamento) {
        this.fechaAltaMedicamento = fechaAltaMedicamento;
    }
    public LocalDate getFechaFinVigenciaMed() {
        return fechaFinVigenciaMed;
    }
    public void setFechaFinVigenciaMed(LocalDate fechaFinVigenciaMed) {
        this.fechaFinVigenciaMed = fechaFinVigenciaMed;
    }
    public String getMarcaMedicamento() {
        return marcaMedicamento;
    }
    public void setMarcaMedicamento(String marcaMedicamento) {
        this.marcaMedicamento = marcaMedicamento;
    }
    public String getNombreMedicamento() {
        return nombreMedicamento;
    }
    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    @Override
    public String toString() {
        return "Medicamento [codMedicamento=" + codMedicamento + ", esParticular=" + esParticular
                + ", fechaAltaMedicamento=" + fechaAltaMedicamento + ", fechaFinVigenciaMed=" + fechaFinVigenciaMed
                + ", marcaMedicamento=" + marcaMedicamento + ", nombreMedicamento=" + nombreMedicamento
                + ", recordatorio=" + "]";
    }
    /*public List<Recordatorio> getRecordatorio() {
        return recordatorio;
    }
    public void setRecordatorio(List<Recordatorio> recordatorio) {
        this.recordatorio = recordatorio;
    }*/
}