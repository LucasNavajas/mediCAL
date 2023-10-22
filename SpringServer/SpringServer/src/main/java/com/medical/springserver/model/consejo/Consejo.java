package com.medical.springserver.model.consejo;

import java.awt.Image;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medical.springserver.model.tipoconsejo.TipoConsejo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Consejo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int nroConsejo;
	private String nombreConsejo;
	private String linkConsejo;
	private String auspiciante;
	private String descConsejo;
	private LocalDate fechaAltaConsejo;
	// foto antes era tipo Image
	private String fotoConsejo;
	private LocalDate fechaFinVigenciaConsejo;
	private int cantLikes;					// Contador de Likes
	private String listaLikeados;		// Lista de Strings con el codUsuario que ha dado like
	
	@ManyToOne
	@JoinColumn(name = "nroTipoConsejo")
	private TipoConsejo tipoconsejo;
	
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
				+ ", tipoconsejo=" + tipoconsejo + "]";
	}


	public TipoConsejo getTipoConsejo() {
		return tipoconsejo;
	}


	public void setTipoConsejo(TipoConsejo tipoconsejo) {
		this.tipoconsejo = tipoconsejo;
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
