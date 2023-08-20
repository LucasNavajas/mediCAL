package com.example.medical.model;

import java.time.LocalDate;

public class Medicion {
    private int codMedicion;
    private LocalDate fechaFinVigenciaM;
    private String nombreMedicion;
    private String unidadMedidaMedicion;
    private LocalDate fechaAltaMedicion;

    public int getCodMedicion() {
        return codMedicion;
    }

    public void setCodMedicion(int codMedicion) {
        this.codMedicion = codMedicion;
    }

    public LocalDate getFechaAltaMedicion() {
        return fechaAltaMedicion;
    }

    public void setFechaAltaMedicion(LocalDate fechaAltaMedicion) {
        this.fechaAltaMedicion = fechaAltaMedicion;
    }

    public LocalDate getFechaFinVigenciaM() {
        return fechaFinVigenciaM;
    }

    public void setFechaFinVigenciaM(LocalDate fechaFinVigenciaM) {
        this.fechaFinVigenciaM = fechaFinVigenciaM;
    }

    public String getNombreMedicion() {
        return nombreMedicion;
    }

    public void setNombreMedicion(String nombreMedicion) {
        this.nombreMedicion = nombreMedicion;
    }

    public String getUnidadMedidaMedicion() {
        return unidadMedidaMedicion;
    }

    public void setUnidadMedidaMedicion(String unidadMedidaMedicion) {
        this.unidadMedidaMedicion = unidadMedidaMedicion;
    }
    @Override
    public String toString() {
        return "Medicion [codMedicion=" + codMedicion + ", fechaAltaMedicion=" + fechaAltaMedicion
                + ", fechaFinVigenciaM=" + fechaFinVigenciaM + ", nombreMedicion=" + nombreMedicion
                + ", unidadMedidaMedicion=" + unidadMedidaMedicion + "]";
    }

    //faltan relaciones

}
