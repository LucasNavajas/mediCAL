package com.medical.springserver.model.instruccion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InstruccionRepository extends CrudRepository<Instruccion, Integer> {

}
