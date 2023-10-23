package com.example.medical.retrofit;

import com.example.medical.model.PerfilPermiso;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PerfilPermisoApi {
    @GET("perfilpermiso/{codPerfil}")
    Call<List<PerfilPermiso>> getByCodPerfil(@Path("codPerfil") int codPerfil);
}
