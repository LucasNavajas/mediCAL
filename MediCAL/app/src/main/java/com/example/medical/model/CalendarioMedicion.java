package com.example.medical.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CalendarioMedicion {
    private int codCalendarioMedicion;
    private LocalDateTime fechaCalendarioMedicion;
    private LocalDate fechaFinVigenciaCM;
    private float valorCalendarioMedicion;

    public int getCodCalendarioMedicion() {
        return codCalendarioMedicion;
    }

    public void setCodCalendarioMedicion(int codCalendarioMedicion) {
        this.codCalendarioMedicion = codCalendarioMedicion;
    }

    public LocalDateTime getFechaCalendarioMedicion() {
        return fechaCalendarioMedicion;
    }

    public void setFechaCalendarioMedicion(LocalDateTime fechaCalendarioMedicion) {
        this.fechaCalendarioMedicion = fechaCalendarioMedicion;
    }

    public LocalDate getFechaFinVigenciaCM() {
        return fechaFinVigenciaCM;
    }

    public void setFechaFinVigenciaCM(LocalDate fechaFinVigenciaCM) {
        this.fechaFinVigenciaCM = fechaFinVigenciaCM;
    }

    public Float getValorCalendarioMedicion() {
        return valorCalendarioMedicion;
    }

    public void setValorCalendarioMedicion(Float valorCalendarioMedicion) {
        this.valorCalendarioMedicion = valorCalendarioMedicion;
    }


    // relaciones
    private Calendario calendario;
    private Medicion medicion;

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public Medicion getMedicion() {
        return medicion;
    }

    public void setMedicion(Medicion medicion) {
        this.medicion = medicion;
    }

    public String toString() {
        return "CalendarioMedicion [codCalendarioMedicion=" + codCalendarioMedicion + ", fechaCalendarioMedicion="
                + fechaCalendarioMedicion + ", fechaFinVigenciaCM=" + fechaFinVigenciaCM + ", calendario=" + calendario
                + ", medicion=" + medicion + "]";
    }


}
