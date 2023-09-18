package com.example.medical.model;

public class Omision {
    private int codOmision;
    private String nombreOmision;


    public int getCodOmision() {
        return codOmision;
    }
    public void setCodOmision(int codOmision) {
        this.codOmision = codOmision;
    }
    public String getNombreOmision() {
        return nombreOmision;
    }
    public void setNombreOmision(String nombreOmision) {
        this.nombreOmision = nombreOmision;
    }

    @Override
    public String toString() {
        return "Omision [codOmision=" + codOmision + ", nombreOmision=" + nombreOmision +"]";
    }


}
