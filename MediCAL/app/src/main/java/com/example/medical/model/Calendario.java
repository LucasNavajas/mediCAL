package com.example.medical.model;


import android.os.Parcelable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Calendario {

    private int codCalendario;
    private LocalDate fechaAltaCalendario;
    private LocalDate fechaFinVigenciaC;
    private String nombreCalendario;
    private String nombrePaciente;
    private String relacionCalendario;

    // Relacion con Usuario
    private Usuario usuario;

    // Relacion con Recordatorio
   /* private List<Recordatorio> recordatorio;


    private List<CalendarioMedicion> varcalendariomedicion;

    private List<CalendarioSintoma> varcalendariosintoma;
*/
    public int getCodCalendario() {
        return codCalendario;
    }
    public void setCodCalendario(int codCalendario) {
        this.codCalendario = codCalendario;
    }
    public LocalDate getFechaAltaCalendario() {
        return fechaAltaCalendario;
    }
    public void setFechaAltaCalendario(LocalDate fechaAltaCalendario) {
        this.fechaAltaCalendario = fechaAltaCalendario;
    }
    public LocalDate getFechaFinVigenciaC() {
        return fechaFinVigenciaC;
    }
    public void setFechaFinVigenciaC(LocalDate fechaFinVigenciaC) {
        this.fechaFinVigenciaC = fechaFinVigenciaC;
    }
    public String getNombreCalendario() {
        return nombreCalendario;
    }
    public void setNombreCalendario(String nombreCalendario) {
        this.nombreCalendario = nombreCalendario;
    }
    public String getNombrePaciente() {
        return nombrePaciente;
    }
    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }
    public String getRelacionCalendario() {
        return relacionCalendario;
    }
    public void setRelacionCalendario(String relacionCalendario) {
        this.relacionCalendario = relacionCalendario;
    }

    @Override
    public String toString() {
        return "Calendario [codCalendario=" + codCalendario + ", fechaAltaCalendario=" + fechaAltaCalendario
                + ", fechaFinVigenciaC=" + fechaFinVigenciaC + ", nombreCalendario=" + nombreCalendario
                + ", nombrePaciente=" + nombrePaciente + ", relacionCalendario=" + relacionCalendario + ", usuario="
                + usuario + "]";
    }

    // Relacion

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private List<CalendarioSintoma> varcalendariosintoma;
    public List<CalendarioSintoma> getVarcalendariosintoma() {
        List<CalendarioSintoma> varcalendariosintoma = null;
        return null;
    }
    public void setVarcalendariosintoma(List<CalendarioSintoma> varcalendariosintoma) {
        this.varcalendariosintoma = varcalendariosintoma;
    }

   /* public List<Recordatorio> getRecordatorio() {
        return recordatorio;
    }
    public void setRecordatorio(List<Recordatorio> recordatorio) {
        this.recordatorio = recordatorio;
    }
    public List<CalendarioMedicion> getVarcalendariomedicion() {
        return varcalendariomedicion;
    }
    public void setVarcalendariomedicion(List<CalendarioMedicion> varcalendariomedicion) {
        this.varcalendariomedicion = varcalendariomedicion;
    }

*/
}
