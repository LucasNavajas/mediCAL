package com.example.medical.model;

import java.time.LocalDate;

public class FAQ {
    private int codFAQ;
    private LocalDate fechaActualizacionFAQ;
    private String preguntatFAQ;
    private String respuestaFAQ;

    // ... Getters y setters

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
        return "FAQ [codFAQ=" + codFAQ + ", fechaActualizacionFAQ=" + fechaActualizacionFAQ + ", preguntatFAQ=" + preguntatFAQ + ", respuestaFAQ=" + respuestaFAQ + "]";
    }
}
