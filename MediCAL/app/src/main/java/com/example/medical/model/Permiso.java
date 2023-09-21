package com.example.medical.model;

import java.time.LocalDate;
import java.util.List;

public class Permiso {

    private int codPermiso;
    private String descPermiso;
    private LocalDate fechaAltaPermiso;
    private LocalDate fechaFinVigenciaP;
    private String nombrePermiso;

    private List<PerfilPermiso> perfilPermiso;


    public List<PerfilPermiso> getPerfilPermiso() {
        return perfilPermiso;
    }


    public void setPerfilPermiso(List<PerfilPermiso> perfilPermiso) {
        this.perfilPermiso = perfilPermiso;
    }


    public int getCodPermiso() {
        return codPermiso;
    }


    public void setCodPermiso(int codPermiso) {
        this.codPermiso = codPermiso;
    }


    public String getDescPermiso() {
        return descPermiso;
    }


    public void setDescPermiso(String descPermiso) {
        this.descPermiso = descPermiso;
    }


    public LocalDate getFechaAltaPermiso() {
        return fechaAltaPermiso;
    }


    public void setFechaAltaPermiso(LocalDate fechaAltaPermiso) {
        this.fechaAltaPermiso = fechaAltaPermiso;
    }


    public LocalDate getFechaFinVigenciaP() {
        return fechaFinVigenciaP;
    }


    public void setFechaFinVigenciaP(LocalDate fechaFinVigenciaP) {
        this.fechaFinVigenciaP = fechaFinVigenciaP;
    }


    public String getNombrePermiso() {
        return nombrePermiso;
    }


    public void setNombrePermiso(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
    }


    @Override
    public String toString() {
        return "Permiso [codPermiso=" + codPermiso + ", descPermiso=" + descPermiso + ", fechaAltaPermiso="
                + fechaAltaPermiso + ", fechaFinVigenciaP=" + fechaFinVigenciaP + "]";
    }

}
