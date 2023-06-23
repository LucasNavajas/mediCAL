package com.medical.springserver.model.sintoma;
import java.time.LocalDate;
import java.util.List;

import com.medical.springserver.model.calendariosintoma.CalendarioSintoma;
import com.medical.springserver.model.historialfinvigencia.HistorialFinVigencia;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Sintoma {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codSintoma;
	private LocalDate fechaAltaSintoma;
	private LocalDate fechaFinVigenciaS;
	private String nombreSintoma;
	//relacion con calendariosintoma
	@OneToMany (mappedBy = "sintoma", cascade = CascadeType.ALL)
	private List<CalendarioSintoma> varcalendariosintoma;
	
	public int getCodSintoma() {
		return codSintoma;
	}
	public void setCodSintoma(int codSintoma) {
		this.codSintoma = codSintoma;
	}
	public LocalDate getFechaAltaSintoma() {
		return fechaAltaSintoma;
	}
	public void setFechaAltaSintoma(LocalDate fechaAltaSintoma) {
		this.fechaAltaSintoma = fechaAltaSintoma;
	}
	public LocalDate getFechaFinVigenciaS() {
		return fechaFinVigenciaS;
	}
	public void setFechaFinVigenciaS(LocalDate fechaFinVigenciaS) {
		this.fechaFinVigenciaS = fechaFinVigenciaS;
	}
	public String getNombreSintoma() {
		return nombreSintoma;
	}
	public void setNombreSintoma(String nombreSintoma) {
		this.nombreSintoma = nombreSintoma;
	}
	
	@Override
	public String toString() {
		return "Sintoma [codSintoma=" + codSintoma + ", fechaAltaSintoma=" + fechaAltaSintoma + ", fechaFinVigenciaS="
				+ fechaFinVigenciaS + ", nombreSintoma=" + nombreSintoma + "]";
	}
	public List<CalendarioSintoma> getVarcalendariosintoma() {
		return varcalendariosintoma;
	}
	public void setVarcalendariosintoma(List<CalendarioSintoma> varcalendariosintoma) {
		this.varcalendariosintoma = varcalendariosintoma;
	}	

}
