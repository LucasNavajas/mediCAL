package com.example.medical.model;


import java.time.LocalDate;

public class Consejo {
    private int nroConsejo;
    private String nombreConsejo;
    private String linkConsejo;
    private String fotoConsejo;
    private String auspiciante;
    private String descConsejo;
    private LocalDate fechaAltaConsejo;

    private TipoConsejo tipoConsejo;

    public int getNroConsejo() {
        return nroConsejo;
    }

    public void setNroConsejo(int nroConsejo) {
        this.nroConsejo = nroConsejo;
    }

    public String getNombreConsejo() {
        return nombreConsejo;
    }

    public void setNombreConsejo(String nombreConsejo) {
        this.nombreConsejo = nombreConsejo;
    }

    public String getLinkConsejo() {
        return linkConsejo;
    }

    public void setLinkConsejo(String linkConsejo) {
        this.linkConsejo = linkConsejo;
    }

    public String getAuspiciante() {
        return auspiciante;
    }

    public void setAuspiciante(String auspiciante) {
        this.auspiciante = auspiciante;
    }

    public String getDescConsejo() {
        return descConsejo;
    }

    public void setDescConsejo(String descConsejo) {
        this.descConsejo = descConsejo;
    }

    public LocalDate getFechaAltaConsejo() {
        return fechaAltaConsejo;
    }

    public void setFechaAltaConsejo(LocalDate fechaAltaConsejo) {
        this.fechaAltaConsejo = fechaAltaConsejo;
    }

    public String getFotoConsejo() {
        return fotoConsejo;
    }

    public void setFotoConsejo(String fotoConsejo) {
        this.fotoConsejo = fotoConsejo;
    }

    public TipoConsejo getTipoConsejo() {
        return tipoConsejo;
    }

    public void setTipoConsejo(TipoConsejo tipoconsejo) {
        this.tipoConsejo = tipoconsejo;
    }
}