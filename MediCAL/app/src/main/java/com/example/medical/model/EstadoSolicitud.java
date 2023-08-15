package com.example.medical.model;


import java.util.List;

public class EstadoSolicitud {

    private int codEstadoSolicitud;
    private String nombreEstadoSolicitud;

    private List<Solicitud> solicitudes;


    public int getCodEstadoSolicitud() {
        return codEstadoSolicitud;
    }

    public void setCodEstadoSolicitud(int codEstadoSolicitud) {
        this.codEstadoSolicitud = codEstadoSolicitud;
    }


    public String getNombreEstadoSolicitud() {
        return nombreEstadoSolicitud;
    }


    public void setNombreEstadoSolicitud(String nombreEstadoSolicitud) {
        this.nombreEstadoSolicitud = nombreEstadoSolicitud;
    }


    @Override
    public String toString() {
        return "EstadoSolicitud [codEstadoSolicitud=" + codEstadoSolicitud + ", nombreEstadoSolicitud=" + nombreEstadoSolicitud + "]";
    }

    public List<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }


}
