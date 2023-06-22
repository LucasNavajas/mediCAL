package com.medical.springserver.model.usuario;

import java.awt.Image;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//Comentario de prueba github
@Entity
public class Consejo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int nroConsejo;
	private String nombreConsejo;
	private String linkConsejo;
	private boolean likeConsejo;
	private String descConsejo;
	private LocalDate fechaAltaConsejo;
	//private Image fotoConsejo;
	
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


	public boolean isLikeConsejo() {
		return likeConsejo;
	}


	public void setLikeConsejo(boolean likeConsejo) {
		this.likeConsejo = likeConsejo;
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


	/*public Image getFotoConsejo() {
		return fotoConsejo;
	}


	public void setFotoConsejo(Image fotoConsejo) {
		this.fotoConsejo = fotoConsejo;
	}
*/

	@Override
	public String toString() {
		return "Consejo [ nroConsejo=" +  nroConsejo + ", nombreConsejo=" + nombreConsejo + 
				", linkConsejo=" + linkConsejo + ", descConsejo=" + descConsejo + 
				", fechaAltaConsejo=" + fechaAltaConsejo + ", likeConsejo=" + likeConsejo +"]";
	}
	
	
}
