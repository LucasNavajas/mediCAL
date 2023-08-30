package com.example.medical.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CalendarioSintoma {
    private LocalDateTime fechaCalendarioSintoma;
    private LocalDate fechaFinVigenciaCS;
    private int codCalendarioSintoma;

    public int getCodCalendarioSintoma() {
        return codCalendarioSintoma;
    }

    public void setCodCalendarioSintoma(int codCalendarioSintoma) {
        this.codCalendarioSintoma = codCalendarioSintoma;
    }

    public LocalDateTime getFechaCalendarioSintoma() {
        return fechaCalendarioSintoma;
    }

    public void setFechaCalendarioSintoma(LocalDateTime fechaCalendarioSintoma) {
        this.fechaCalendarioSintoma = fechaCalendarioSintoma;
    }

    public LocalDate getFechaFinVigenciaCS() {
        return fechaFinVigenciaCS;
    }

    public void setFechaFinVigenciaCS(LocalDate fechaFinVigenciaCS) {
        this.fechaFinVigenciaCS = fechaFinVigenciaCS;
    }

    // Relaciones

    private Calendario calendario;
    private Sintoma sintoma;

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public Sintoma getSintoma() {
        return sintoma;
    }

    public void setSintoma(Sintoma sintoma) {
        this.sintoma = sintoma;
    }

    public String toString() {
        return "CalendarioSintoma [codCalendarioSintoma=" + codCalendarioSintoma + ", fechaCalendarioSintoma=" + fechaCalendarioSintoma
                + ", fechaFinVigenciaCS=" + fechaFinVigenciaCS + "]";
    }


}
