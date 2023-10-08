package com.example.medical.model;

import java.time.LocalDate;

public class Reporte {

    private int nroReporte;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private LocalDate fechaGenerada;
    private String nombreMed;

    private TipoReporte tipoReporte;

    private Usuario usuario;

    public int getNroReporte() {
        return nroReporte;
    }

    // ??
    public void setNroReporte(int nroReporte) {
        this.nroReporte = nroReporte;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public LocalDate getFechaGenerada() {
        return fechaGenerada;
    }

    public void setFechaGenerada(LocalDate fechaGenerada) {
        this.fechaGenerada = fechaGenerada;
    }

    public String getNombreMed() { return nombreMed; }

    public void setNombreMed(String nombreMed) { this.nombreMed = nombreMed; }

    @Override
    public String toString() {
        return "Reporte [nroReporte=" + nroReporte + ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta
                + ", fechaGenerada=" + fechaGenerada + ", tipoReporte=" + tipoReporte + ", usuario=" + usuario + "]";
    }


    public TipoReporte getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(TipoReporte tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
