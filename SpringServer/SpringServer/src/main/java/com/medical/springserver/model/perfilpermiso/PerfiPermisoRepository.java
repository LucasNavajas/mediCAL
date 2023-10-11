package com.medical.springserver.model.perfilpermiso;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PerfiPermisoRepository extends CrudRepository<PerfilPermiso, Integer>{
	 @Query("SELECT pp FROM PerfilPermiso pp WHERE pp.perfil.codPerfil = :codigoPerfil")
	    List<PerfilPermiso> findByCodigoPerfil(@Param("codigoPerfil") int codigoPerfil);
}
