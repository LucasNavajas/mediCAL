package com.medical.springserver.model.registroRecordatorio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegistroRecordatorioRepository extends CrudRepository<RegistroRecordatorio, Integer>{

}
