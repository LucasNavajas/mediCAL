package com.example.medical.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegistroRecordatorio {
    private int codRegistroRecordatorio;
    private LocalDate fechaFinVigenciaRR;
    private LocalDateTime fechaTomaEsperada;
    private LocalDateTime fechaTomaReal;
    private int nroRegistro;
    private boolean tomaRegistroRecordatorio;

    // Relacion con Omision
    private Omision omision;

    // Relacion con Recordatorio
    private Recordatorio recordatorio;

    public int getCodRegistroRecordatorio() {
        return codRegistroRecordatorio;
    }
    public void setCodRegistroRecordatorio(int codRegistroRecordatorio) {
        this.codRegistroRecordatorio = codRegistroRecordatorio;
    }
    public LocalDate getFechaFinVigenciaRR() {
        return fechaFinVigenciaRR;
    }
    public void setFechaFinVigenciaRR(LocalDate fechaFinVigenciaRR) {
        this.fechaFinVigenciaRR = fechaFinVigenciaRR;
    }
    public LocalDateTime getFechaTomaEsperada() {
        return fechaTomaEsperada;
    }
    public void setFechaTomaEsperada(LocalDateTime fechaTomaEsperada) {
        this.fechaTomaEsperada = fechaTomaEsperada;
    }
    public LocalDateTime getFechaTomaReal() {
        return fechaTomaReal;
    }
    public void setFechaTomaReal(LocalDateTime fechaTomaReal) {
        this.fechaTomaReal = fechaTomaReal;
    }
    public int getNroRegistro() {
        return nroRegistro;
    }
    public void setNroRegistro(int nroRegistro) {
        this.nroRegistro = nroRegistro;
    }
    public boolean isTomaRegistroRecordatorio() {
        return tomaRegistroRecordatorio;
    }
    public void setTomaRegistroRecordatorio(boolean tomaRegistroRecordatorio) {
        this.tomaRegistroRecordatorio = tomaRegistroRecordatorio;
    }

    @Override
    public String toString() {
        return "RegistroRecordatorio [codRegistroRecordatorio=" + codRegistroRecordatorio + ", fechaFinVigenciaRR="
                + fechaFinVigenciaRR + ", fechaTomaEsperada=" + fechaTomaEsperada + ", fechaTomaReal=" + fechaTomaReal
                + ", nroRegistro=" + nroRegistro + ", tomaRegistroRecordatorio=" + tomaRegistroRecordatorio
                + ", omision=" + omision + ", recordatorio=" + recordatorio + "]";
    }

    // Relaciones

    public Omision getOmision() {
        return omision;
    }
    public void setOmision(Omision omision) {
        this.omision = omision;
    }
    public Recordatorio getRecordatorio() {
        return recordatorio;
    }
    public void setRecordatorio(Recordatorio recordatorio) {
        this.recordatorio = recordatorio;
    }

}
