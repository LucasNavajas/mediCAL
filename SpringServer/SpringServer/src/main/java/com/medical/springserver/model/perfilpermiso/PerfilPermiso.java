package com.medical.springserver.model.perfilpermiso;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PerfilPermiso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPerfilPermiso;
	

	public int getIdPerfilPermiso() {
		return idPerfilPermiso;
	}



	public void setIdPerfilPermiso(int idPerfilPermiso) {
		this.idPerfilPermiso = idPerfilPermiso;
	}



	@Override
	public String toString() {
		return "PerfilPermiso [idPerfilPermiso=" + idPerfilPermiso + "]";
	}
	
	
}
