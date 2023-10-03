package com.medical.springserver.model.tiporeporte;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.medical.springserver.model.reporte.Reporte;

@Service
public class TipoReporteDao {
	@Autowired
	private TipoReporteRepository repository;
	
	public TipoReporte save(TipoReporte tipoReporte) {
		return repository.save(tipoReporte);
	}
	
	public List<TipoReporte> getAllTipoReporte() {
	    Streamable<TipoReporte> streamableTipoReporte = Streamable.of(repository.findAll());
	    List<TipoReporte> tipoReporte = new ArrayList<>();
	    streamableTipoReporte.forEach(tipoReporte::add);
	    return tipoReporte;
	}	
	
	public TipoReporte findByNroReporte(int nroReporte) {
		return repository.findByNroReporte(nroReporte);
	}
	
	public void delete(TipoReporte tipoReporte) {
		repository.delete(tipoReporte);
	}
	
}
