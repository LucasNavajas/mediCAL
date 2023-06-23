package com.medical.springserver;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.inventario.Inventario;
import com.medical.springserver.model.inventario.InventarioDao;

@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsInventario {
	
	@Autowired
	private InventarioDao inventarioDao;
	
	@Test
	void addInventarioTest() {
		
		Inventario inventario = new Inventario();
		inventario.setCantAvisoInventario(5);
		inventario.setCantRealInventario(20);
		
		inventarioDao.save(inventario);
		
	}
	
	//@Test
	void getAllInventariosAndDelete() {
		List<Inventario> inventarios = inventarioDao.getAllInventarios();
		for (Inventario inventario : inventarios) {
			inventarioDao.delete(inventario);
		}
		
	}

}
