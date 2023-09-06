package com.example.medical.model;

import java.util.List;

public class Concentracion {

    private int codConcentracion;
    private String unidadMedidaC;
    private List<Dosis> dosis;


    public int getCodConcentracion() {
        return codConcentracion;
    }


    public void setCodConcentracion(int codConcentracion) {
        this.codConcentracion = codConcentracion;
    }


    public String getUnidadMedidaC() {
        return unidadMedidaC;
    }


    public void setUnidadMedidaC(String unidadMedidaC) {
        this.unidadMedidaC = unidadMedidaC;
    }




    @Override
    public String toString() {
        return "Concentracion [codConcentracion=" + codConcentracion + ", unidadMedidaC=" +  unidadMedidaC +  "]";
    }


    public List<Dosis> getDosis() {
        return dosis;
    }


    public void setDosis(List<Dosis> dosis) {
        this.dosis = dosis;
    }
}
