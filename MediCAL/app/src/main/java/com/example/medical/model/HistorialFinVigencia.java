package com.example.medical.model;
import java.time.LocalDate;

public class HistorialFinVigencia {

    private int nroHistorialFV;
    private LocalDate fechaDesdeFV;
    private LocalDate fechaHastaFV;
    private String motivoFV;

    private Usuario usuario;


    public LocalDate getFechaDesdeFV() {
        return fechaDesdeFV;
    }
    public void setFechaDesdeFV(LocalDate fechaDesdeFV) {
        this.fechaDesdeFV = fechaDesdeFV;
    }
    public LocalDate getFechaHastaFV() {
        return fechaHastaFV;
    }
    public void setFechaHastaFV(LocalDate fechaHastaFV) {
        this.fechaHastaFV = fechaHastaFV;
    }
    public String getMotivoFV() {
        return motivoFV;
    }
    public void setMotivoFV(String motivoFV) {
        this.motivoFV = motivoFV;
    }
    public int getNroHistorialFV() {
        return nroHistorialFV;
    }
    public void setNroHistorialFV(int nroHistorialFV) {
        this.nroHistorialFV = nroHistorialFV;
    }

    @Override
    public String toString() {
        return "HistorialFinVigencia [nroHistorialFV=" + nroHistorialFV + ", fechaDesdeFV=" + fechaDesdeFV
                + ", fechaHastaFV=" + fechaHastaFV + ", motivoFV=" + motivoFV + ", usuario=" + usuario + "]";
    }

    // Relaciones

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}