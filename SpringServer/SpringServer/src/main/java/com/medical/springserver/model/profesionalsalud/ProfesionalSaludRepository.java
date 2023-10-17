package com.medical.springserver.model.profesionalsalud;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface ProfesionalSaludRepository extends CrudRepository<ProfesionalSalud, Integer>{
	@Query("SELECT p.dni FROM ProfesionalSalud p")
    List<String> findAllDistinctDniUsuario();
	
	@Query("SELECT p.usuarioUnico FROM ProfesionalSalud p")
    List<String> findAllDistinctUsuarioUnico();
	
	List<ProfesionalSalud> findByUsuarioUnico(String usuarioUnico);
	
	@Query("SELECT p FROM ProfesionalSalud p WHERE p.nombreInstitucion = :nombreInstitucion"
			+ " AND p.perfil.codPerfil = 4"
			+ " AND p.fechaAltaUsuario BETWEEN :startDate AND :endDate ")
    List<ProfesionalSalud> findByFechaAltaUsuarioBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("nombreInstitucion") String nombreInstitucion);
}