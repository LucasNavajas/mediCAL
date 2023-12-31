package com.example.medical.model;

import java.time.LocalDate;

public class AdministracionMed {

    private int codAdministracionMed;
    private String descAdministracionMed;
    private LocalDate fechaAltaAdministracionMed;
    private LocalDate fechaFinVigenciaAM;
    private String nombreAdministracionMed;

    public int getCodAdministracionMed() {
        return codAdministracionMed;
    }

    public void setCodAdministracionMed(int codAdministracionMed) {
        this.codAdministracionMed = codAdministracionMed;
    }

    public String getDescAdministracionMed() {
        return descAdministracionMed;
    }

    public void setDescAdministracionMed(String descAdministracionMed) {
        this.descAdministracionMed = descAdministracionMed;
    }

    public LocalDate getFechaAltaAdministracionMed() {
        return fechaAltaAdministracionMed;
    }

    public void setFechaAltaAdministracionMed(LocalDate fechaAltaAdministracionMed) {
        this.fechaAltaAdministracionMed = fechaAltaAdministracionMed;
    }

    public LocalDate getFechaFinVigenciaAM() {
        return fechaFinVigenciaAM;
    }

    public void setFechaFinVigenciaAM(LocalDate fechaFinVigenciaAM) {
        this.fechaFinVigenciaAM = fechaFinVigenciaAM;
    }

    public String getNombreAdministracionMed() {
        return nombreAdministracionMed;
    }

    public void setNombreAdministracionMed(String nombreAdministracionMed) {
        this.nombreAdministracionMed = nombreAdministracionMed;
    }

    @Override
    public String toString() {
        return "AdministracionMed [codAdministracionMed=" + codAdministracionMed + ", descAdministracionMed="
                + descAdministracionMed + ", fechaAltaAdministracionMed=" + fechaAltaAdministracionMed
                + ", fechaFinVigenciaAM=" + fechaFinVigenciaAM + ", nombreAdministracionMed=" + nombreAdministracionMed
                + "]";
    }
}
