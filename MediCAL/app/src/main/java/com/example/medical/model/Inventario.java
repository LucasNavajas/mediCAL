package com.example.medical.model;

import java.util.List;

public class Inventario {

    private int cantAvisoInventario;
    private int cantRealInventario;

    // Relacion con Recordatorio
    private List<Recordatorio> recordatorio;

    public int getCantAvisoInventario() {
        return cantAvisoInventario;
    }

    public void setCantAvisoInventario(int cantAvisoInventario) {
        this.cantAvisoInventario = cantAvisoInventario;
    }

    public int getCantRealInventario() {
        return cantRealInventario;
    }

    public void setCantRealInventario(int cantRealInventario) {
        this.cantRealInventario = cantRealInventario;
    }

    @Override
    public String toString() {
        return "Inventario [cantAvisoInventario=" + cantAvisoInventario + ", cantRealInventario=" + cantRealInventario
                + ", recordatorio=" + recordatorio + "]";
    }

    // Relaciones

    public List<Recordatorio> getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(List<Recordatorio> recordatorio) {
        this.recordatorio = recordatorio;
    }

}
