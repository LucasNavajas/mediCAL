package com.example.medical.model;


import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;



public class CodigoVerificacion {

    private String codVerificacion; // Cambiamos el tipo de dato a String

    private LocalDateTime fechaGenerado;

    public CodigoVerificacion() {
        this.codVerificacion = generateRandomCode(); // Generamos el código aleatorio al crear una nueva instancia
        this.fechaGenerado = LocalDateTime.now();
    }

    public String getCodVerificacion() {
        return codVerificacion;
    }

    public void setCodVerificacion(String codVerificacion) {
        this.codVerificacion = codVerificacion;
    }

    public LocalDateTime getFechaGenerado() {
        return fechaGenerado;
    }

    public void setFechaGenerado(LocalDateTime fechaGenerado) {
        this.fechaGenerado = fechaGenerado;
    }

    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder();
        int codeLength = 4;

        for (int i = 0; i < codeLength; i++) {
            int randomNumber = random.nextInt(10); // Generamos un número aleatorio del 0 al 9
            codeBuilder.append(randomNumber);
        }

        return codeBuilder.toString();
    }

    @Override
    public String toString() {
        return "CodigoVerificacion [codVerificacion=" + codVerificacion + ", fechaGenerado=" + fechaGenerado + "]";
    }
}