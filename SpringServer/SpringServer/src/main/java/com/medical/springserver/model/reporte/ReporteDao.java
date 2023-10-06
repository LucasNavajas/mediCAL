package com.medical.springserver.model.reporte;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.medical.springserver.model.inventario.Inventario;

@Service
public class ReporteDao {
	@Autowired
	private ReporteRepository repository;
	
	public Reporte save(Reporte reporte) {
		return repository.save(reporte);
	}
	
	public List<Reporte> getAllReporte() {
	    Streamable<Reporte> streamableReporte = Streamable.of(repository.findAll());
	    List<Reporte> reporte = new ArrayList<>();
	    streamableReporte.forEach(reporte::add);
	    return reporte;
	}
	
	public Reporte findByNroReporte(int nroReporte) {
		return repository.findByNroReporte(nroReporte);
	}
	
	public List<Reporte> findByCodUsuario(int codUsuario) {
		return repository.findByCodUsuario(codUsuario);
	}
	
	public void delete(Reporte reporte) {
		repository.delete(reporte);
	}
	
	public Integer deleteByNroReporte(int nroReporte) {
		return repository.deleteByNroReporte(nroReporte);
	}
	
	
}
