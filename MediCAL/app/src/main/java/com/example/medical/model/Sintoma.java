package com.example.medical.model;

import java.time.LocalDate;


public class Sintoma {

    private int codSintoma;
    private LocalDate fechaAltaSintoma;
    private LocalDate fechaFinVigenciaS;
    private String nombreSintoma;

    public int getCodSintoma() {
        return codSintoma;
    }
    public void setCodSintoma(int codSintoma) {
        this.codSintoma = codSintoma;
    }
    public LocalDate getFechaAltaSintoma() {
        return fechaAltaSintoma;
    }
    public void setFechaAltaSintoma(LocalDate fechaAltaSintoma) {
        this.fechaAltaSintoma = fechaAltaSintoma;
    }
    public LocalDate getFechaFinVigenciaS() {
        return fechaFinVigenciaS;
    }
    public void setFechaFinVigenciaS(LocalDate fechaFinVigenciaS) {
        this.fechaFinVigenciaS = fechaFinVigenciaS;
    }

    public boolean tieneFechaAlta() {
        return fechaAltaSintoma != null; // Devuelve true si la fecha de alta no es nula
    }
    public String getNombreSintoma() {
        return nombreSintoma;
    }
    public void setNombreSintoma(String nombreSintoma) {
        this.nombreSintoma = nombreSintoma;
    }

    @Override
    public String toString() {
        return "Sintoma [codSintoma=" + codSintoma + ", fechaAltaSintoma=" + fechaAltaSintoma + ", fechaFinVigenciaS="
                + fechaFinVigenciaS + ", nombreSintoma=" + nombreSintoma + "]";
    }

    //faltan relaciones
}

