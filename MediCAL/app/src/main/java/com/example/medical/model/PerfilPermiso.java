package com.example.medical.model;

public class PerfilPermiso {

    private int idPerfilPermiso;

    private Permiso permiso;

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
