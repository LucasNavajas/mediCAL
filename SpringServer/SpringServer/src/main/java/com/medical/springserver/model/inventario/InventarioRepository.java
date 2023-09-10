package com.medical.springserver.model.inventario;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends CrudRepository<Inventario, Integer>{
	
	// Método para buscar todas las instancias de Inventario con un codrecordatorio específico
	@Query("SELECT i FROM Inventario i WHERE EXISTS (SELECT r FROM Recordatorio r WHERE r.codRecordatorio = :codRecordatorio AND r.inventario = i)")
	Inventario findByCodRecordatorio(@Param("codRecordatorio") Integer codRecordatorio);
    
    //@Query("SELECT i FROM Inventario i WHERE EXISTS (SELECT r FROM Recordatorio r WHERE r.codRecordatorio = :codRecordatorio AND r.inventario = i)")
    //List<Inventario> findByCodRecordatorio(@Param("codRecordatorio") Integer codRecordatorio);
	
}

