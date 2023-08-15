package com.example.medical.model;
import java.time.LocalDate;


public class Solicitud {
    private int codSolicitud;
    private LocalDate fechaSolicitud;

    private EstadoSolicitud estadoSolicitud;

    //relacion con usuario controlador
    private Usuario usuarioControlador;

    //relacion con usuario controlado
    private Usuario usuarioControlado;

    public Usuario getUsuarioControlador() {
        return usuarioControlador;
    }
    public void setUsuarioControlador(Usuario usuarioControlador) {
        this.usuarioControlador = usuarioControlador;
    }
    public Usuario getUsuarioControlado() {
        return usuarioControlado;
    }
    public void setUsuarioControlado(Usuario usuarioControlado) {
        this.usuarioControlado = usuarioControlado;
    }



    public int getCodSolicitud() {
        return codSolicitud;
    }
    public void setCodSolicitud(int codSolicitud) {
        this.codSolicitud = codSolicitud;
    }
    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }
    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    @Override
    public String toString() {
        return "Solicitud [codSolicitud=" + codSolicitud + ", fechaSolicitud=" + fechaSolicitud + ", estadoSolicitud="
                + estadoSolicitud + "]";
    }
    public EstadoSolicitud getEstadoSolicitud() {
        return estadoSolicitud;
    }
    public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }
}

