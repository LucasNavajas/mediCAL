package com.example.medical.model;

import java.util.List;

public class TipoReporte {

    private int codTipoReporte;
    private String nombreTipoReporte;

    private List<Reporte> reportes;

    public int getCodTipoReporte() {
        return codTipoReporte;
    }

    public void setCodTipoReporte(int codTipoReporte) {
        this.codTipoReporte = codTipoReporte;
    }

    public String getNombreTipoReporte() {
        return nombreTipoReporte;
    }

    public void setNombreTipoReporte(String nombreTipoReporte) {
        this.nombreTipoReporte = nombreTipoReporte;
    }

    @Override
    public String toString() {
        return "TipoReporte [codTipoReporte=" + codTipoReporte + ", nombreTipoReporte=" + nombreTipoReporte + "]";
    }

    public List<Reporte> getReportes() {
        return reportes;
    }

    public void setReportes(List<Reporte> reportes) {
        this.reportes = reportes;
    }

}
