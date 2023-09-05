package com.medical.springserver.model.inventario;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.medical.springserver.model.calendario.Calendario;

@Service

public class InventarioDao {
	@Autowired
	private InventarioRepository repository;
	
	public Inventario save(Inventario inventario) {
		return repository.save(inventario);
	}
	
	public List<Inventario> getAllInventarios(){
		Streamable<Inventario> streamableInventarios = Streamable.of(repository.findAll());
		List<Inventario> inventarios = new ArrayList<>();
		streamableInventarios.forEach(inventarios::add);
		return inventarios;
	}
	
	public List<Inventario> findByCodRecordatorio(int codRecordatorio){
		return repository.findByCodRecordatorio(codRecordatorio);
	}
	
	public void delete(Inventario inventario) {
		repository.delete(inventario);
	}

}
