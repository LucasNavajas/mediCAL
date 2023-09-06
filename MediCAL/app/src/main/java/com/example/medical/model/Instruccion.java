package com.example.medical.model;

public class Instruccion {

    private int codInstruccion;
    private String descInstruccion;
    private String nombreInstruccion;

    private Recordatorio recordatorio;

    public int getCodInstruccion() {
        return codInstruccion;
    }
    public void setCodInstruccion(int codInstruccion) {
        this.codInstruccion = codInstruccion;
    }
    public String getDescInstruccion() {
        return descInstruccion;
    }
    public void setDescInstruccion(String descInstruccion) {
        this.descInstruccion = descInstruccion;
    }
    public String getNombreInstruccion() {
        return nombreInstruccion;
    }
    public void setNombreInstruccion(String nombreInstruccion) {
        this.nombreInstruccion = nombreInstruccion;
    }

    @Override
    public String toString() {
        return "Instruccion [codInstruccion=" + codInstruccion + ", descInstruccion=" + descInstruccion
                + ", nombreInstruccion=" + nombreInstruccion + ", recordatorio=" + recordatorio + "]";
    }

    // Relacion

    public Recordatorio getRecordatorio() {
        return recordatorio;
    }
    public void setRecordatorio(Recordatorio recordatorio) {
        this.recordatorio = recordatorio;
    }

}
