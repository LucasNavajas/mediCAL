package com.medical.springserver.controller;
import com.medical.springserver.model.calendario.Calendario;
import com.medical.springserver.model.inventario.Inventario;
import com.medical.springserver.model.inventario.InventarioDao;
import com.medical.springserver.model.usuario.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class InventarioController {
	
	@Autowired
	InventarioDao inventarioDao;
	
	@GetMapping("/inventario/get-all")
	public List<Inventario> getAllInventarios(){
		return inventarioDao.getAllInventarios();
	}
	
	@PostMapping("/inventario/save")
	public Inventario save(@RequestBody Inventario inventario) {
		return inventarioDao.save(inventario);
	}
	
	/*@GetMapping("/inventario/recordatorio/{codRecordatorio}")
	public List<Inventario> getByCodRecordatorio(@PathVariable int codRecordatorio){
		return inventarioDao.findByCodRecordatorio(codRecordatorio);
	}*/
	
	@GetMapping("/inventario/recordatorio/{codRecordatorio}")
	public Inventario getByCodRecordatorio(@PathVariable int codRecordatorio) {
	    return inventarioDao.findByCodRecordatorio(codRecordatorio);
	}	
	
	@PostMapping("inventario/actualizarInventario/{codInventario}")
	public ResponseEntity<String> actualizarInventario(@PathVariable int codInventario, @RequestBody int nuevaCantidadReal){
		try {
            Inventario inventario = inventarioDao.actualizarInventario(codInventario, nuevaCantidadReal);
            return new ResponseEntity<>("Inventario actualizado correctamente.", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("El inventario no existe.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el inventario.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@PostMapping("inventario/desactivarInventario/{codInventario}")
	public ResponseEntity<String> desactivarInventario(@PathVariable int codInventario){
		try {
            Inventario inventario = inventarioDao.desactivarInventario(codInventario);
            return new ResponseEntity<>("Inventario desactivado correctamente.", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("El inventario no existe.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al desactivar el inventario.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

}
