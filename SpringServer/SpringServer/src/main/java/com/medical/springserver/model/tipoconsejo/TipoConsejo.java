package com.medical.springserver.model.tipoconsejo;

import java.util.List;

import com.medical.springserver.model.consejo.Consejo;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
//Comentario de prueba github
@Entity
public class TipoConsejo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int nroTipoConsejo;
	private String nombreTipoConsejo;

	@OneToMany (mappedBy = "tipoconsejo", cascade = CascadeType.ALL)
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



	@Override
	public String toString() {
		return "TipoConsejo [ nroTipoConsejo=" +  nroTipoConsejo + ", nombreTipoConsejo=" + nombreTipoConsejo + "]";
	}
	
	
}
