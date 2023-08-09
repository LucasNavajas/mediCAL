package com.medical.springserver.model.calendario;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface CalendarioRepository extends CrudRepository<Calendario, Integer>{
	// Método para buscar todas las instancias de Calendario con un codusuario específico
    @Query("SELECT c FROM Calendario c WHERE c.usuario.codUsuario = :codusuario")
    List<Calendario> findByCodUsuario(@Param("codusuario") Integer codusuario);

    Calendario findByCodCalendario(Integer codCalendario);
}
