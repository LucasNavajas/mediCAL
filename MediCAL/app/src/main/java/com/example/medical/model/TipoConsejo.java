package com.example.medical.model;


import java.util.List;

public class TipoConsejo {
    private int nroTipoConsejo;
    private String nombreTipoConsejo;

    private List<Consejo> varconsejo;

    public int getNroTipoConsejo() {
        return nroTipoConsejo;
    }

    public void setNroTipoConsejo(int nroTipoConsejo) {
        this.nroTipoConsejo = nroTipoConsejo;
    }

    public String getNombreTipoConsejo() {
        return nombreTipoConsejo;
    }

    public void setNombreTipoConsejo(String nombreTipoConsejo) {
        this.nombreTipoConsejo = nombreTipoConsejo;
    }

    public List<Consejo> getVarconsejo() {
        return varconsejo;
    }

    public void setVarconsejo(List<Consejo> varconsejo) {
        this.varconsejo = varconsejo;
    }
}