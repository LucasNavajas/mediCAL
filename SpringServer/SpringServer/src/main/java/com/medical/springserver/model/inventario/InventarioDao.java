package com.medical.springserver.model.inventario;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
	
	/*public List<Inventario> findByCodRecordatorio(int codRecordatorio){
		return repository.findByCodRecordatorio(codRecordatorio);
	}*/
	
	public Inventario findByCodRecordatorio(int codRecordatorio) {
		return repository.findByCodRecordatorio(codRecordatorio);
	}	
	
	public void delete(Inventario inventario) {
		repository.delete(inventario);
	}
	
	public Inventario actualizarInventario(int codInventario, int nuevaCantidadReal) {
		Optional<Inventario> optionalInventario = repository.findById(codInventario);
		Inventario inventario;
		if (optionalInventario.isPresent()) {
            inventario = optionalInventario.get();
            inventario.setCantRealInventario(nuevaCantidadReal);;
        } else {
            throw new NoSuchElementException("El inventario no existe.");
        }
		return repository.save(inventario);
	}

}
