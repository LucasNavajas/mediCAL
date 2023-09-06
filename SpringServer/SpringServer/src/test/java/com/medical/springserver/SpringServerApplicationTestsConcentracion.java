package com.medical.springserver;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.medical.springserver.model.concentracion.Concentracion;
import com.medical.springserver.model.concentracion.ConcentracionDao;


@SpringBootTest(classes = {com.medical.springserver.SpringServerApplication.class})
class SpringServerApplicationTestsConcentracion{

    @Autowired
    private ConcentracionDao concentracionDao;

    @Test
    void addConcentracionTest() {


        Concentracion concentracion = new Concentracion();
        concentracion.setUnidadMedidaC("g");
        concentracionDao.save(concentracion);
        
        Concentracion concentracion2 = new Concentracion();
        concentracion2.setUnidadMedidaC("mg");
        concentracionDao.save(concentracion2);
        
        Concentracion concentracion3 = new Concentracion();
        concentracion3.setUnidadMedidaC("ml");
        concentracionDao.save(concentracion3);
        
        Concentracion concentracion4 = new Concentracion();
        concentracion4.setUnidadMedidaC("mcg");
        concentracionDao.save(concentracion4);

    }

    //@Test
    void getAllConcentracionAndDelete() {
        List<Concentracion> concentracions = concentracionDao.getAllConcentracion();
        for (Concentracion concentracion : concentracions) {
            concentracionDao.delete(concentracion);
        }

    }

}
