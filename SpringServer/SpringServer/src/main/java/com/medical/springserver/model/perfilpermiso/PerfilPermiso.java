package com.medical.springserver.model.perfilpermiso;

import java.util.List;

import com.medical.springserver.model.perfil.Perfil;
import com.medical.springserver.model.permiso.Permiso;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class PerfilPermiso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPerfilPermiso;
	
	// Relacion con Permiso
	@ManyToOne
	@JoinColumn(name = "codPermiso")
	private Permiso permiso;
	
	//Relacion con Perfil
	@ManyToOne
	@JoinColumn(name = "codPerfil")
	private Perfil perfil;
	

	public Perfil getPerfil() {
		return perfil;
	}



	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}



	public Permiso getPermiso() {
		return permiso;
	}



	public void setPermiso(Permiso permiso) {
		this.permiso = permiso;
	}



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
