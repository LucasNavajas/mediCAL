package com.example.medical.model;

public class Inventario {

    private int codInventario;
    private Integer cantAvisoInventario;
    private Integer cantRealInventario;

    // Relacion con Recordatorio
    private Recordatorio recordatorio;

    public int getCodInventario() { return codInventario;     }

    public Integer getCantAvisoInventario() {
        return cantAvisoInventario;
    }

    public void setCantAvisoInventario(Integer cantAvisoInventario) {
        this.cantAvisoInventario = cantAvisoInventario;
    }

    public Integer getCantRealInventario() {
        return cantRealInventario;
    }

    public void setCantRealInventario(Integer cantRealInventario) {
        this.cantRealInventario = cantRealInventario;
    }

    @Override
    public String toString() {
        return "Inventario [cantAvisoInventario=" + cantAvisoInventario + ", cantRealInventario=" + cantRealInventario
                + ", recordatorio=" + recordatorio + "]";
    }

    // Relaciones

    public Recordatorio getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(Recordatorio recordatorio) {
        this.recordatorio = recordatorio;
    }

}
