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

    public List<Consejo> getVarConsejo() {
        return varconsejo;
    }

    public void setVarConsejo(List<Consejo> varconsejo) {
        this.varconsejo = varconsejo;
    }


    @Override
    public String toString() {
        return "TipoConsejo [ nroTipoConsejo=" +  nroTipoConsejo + ", nombreTipoConsejo=" + nombreTipoConsejo + "]";
    }
}

