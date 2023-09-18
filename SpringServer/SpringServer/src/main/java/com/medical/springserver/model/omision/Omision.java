package com.medical.springserver.model.omision;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medical.springserver.model.registroRecordatorio.RegistroRecordatorio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Omision {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codOmision;
	private String nombreOmision;
	
	// Relacion con RegistroRecordatorio
	@OneToOne (mappedBy = "omision", cascade = CascadeType.ALL)
	@JsonIgnore
	private RegistroRecordatorio registroRecordatorio;
	
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
		return "Omision [codOmision=" + codOmision + ", nombreOmision=" + nombreOmision +  "]";
	}
		
	// Relaciones
	
	/*public List<RegistroRecordatorio> getRegistrorecordatorio() {
		return registrorecordatorio;
	}
	public void setRegistrorecordatorio(List<RegistroRecordatorio> registrorecordatorio) {
		this.registrorecordatorio = registrorecordatorio;
	}
	*/
}
