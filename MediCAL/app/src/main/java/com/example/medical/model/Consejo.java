package com.example.medical.model;


import java.time.LocalDate;
import java.util.List;

public class Consejo {
    private int nroConsejo;
    private String nombreConsejo;
    private String linkConsejo;
    private String fotoConsejo;
    private String auspiciante;
    private String descConsejo;
    private LocalDate fechaAltaConsejo;
    private LocalDate fechaFinVigenciaConsejo;
    private TipoConsejo tipoConsejo;
    private int cantLikes;
    private String listaLikeados;

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


    public int getCantLikes() {
        return cantLikes;
    }

    public void setCantLikes(int cantLikes) {
        this.cantLikes = cantLikes;
    }


    public String getListaLikeados() {
        return listaLikeados;
    }

    public void setListaLikeados(String listaLikeados) {
        this.listaLikeados = listaLikeados;
    }


    @Override
    public String toString() {
        return "Consejo [nroConsejo=" + nroConsejo + ", nombreConsejo=" + nombreConsejo + ", linkConsejo=" + linkConsejo
                + ", auspiciante=" + auspiciante + ", descConsejo=" + descConsejo + ", fechaAltaConsejo="
                + fechaAltaConsejo + ", fotoConsejo=" + fotoConsejo + ", fechaFinVigenciaConsejo="
                + fechaFinVigenciaConsejo + ", cantLikes=" + cantLikes + ", listaLikeados=" + listaLikeados
                + ", tipoconsejo=" + tipoConsejo + "]";
    }


    public TipoConsejo getTipoConsejo() {
        return tipoConsejo;
    }


    public void setTipoConsejo(TipoConsejo tipoconsejo) {
        this.tipoConsejo = tipoconsejo;
    }


    public String getAuspiciante() {
        return auspiciante;
    }


    public void setAuspiciante(String auspiciante) {
        this.auspiciante = auspiciante;
    }


    public LocalDate getFechaFinVigenciaConsejo() {
        return fechaFinVigenciaConsejo;
    }


    public void setFechaFinVigenciaConsejo(LocalDate fechaFinVigenciaConsejo) {
        this.fechaFinVigenciaConsejo = fechaFinVigenciaConsejo;
    }
}