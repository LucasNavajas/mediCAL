package com.example.medical.model;

import java.util.List;

public class Dosis {
    int codDosis;
    float cantidadDosis;
    float valorConcentracion;
    private Concentracion concentracion;

    private Recordatorio recordatorio;


    public float getCantidadDosis() {
        return cantidadDosis;
    }
    public void setCantidadDosis(float cantidadDosis) {
        this.cantidadDosis = cantidadDosis;
    }
    @Override
    public String toString() {
        return "Dosis [codDosis=" + codDosis + ", cantidadDosis=" + cantidadDosis + ", concentracion=" + concentracion
                + ", recordatorio=" + recordatorio + "]";
    }
    public int getCodDosis() {
        return codDosis;
    }
    public void setCodDosis(int codDosis) {
        this.codDosis = codDosis;
    }
    public Concentracion getConcentracion() {
        return concentracion;
    }
    public void setConcentracion(Concentracion concentracion) {
        this.concentracion = concentracion;
    }
    public Recordatorio getRecordatorios() {
        return recordatorio;
    }
    public void setRecordatorios(Recordatorio recordatorios) {
        this.recordatorio = recordatorios;
    }

}
