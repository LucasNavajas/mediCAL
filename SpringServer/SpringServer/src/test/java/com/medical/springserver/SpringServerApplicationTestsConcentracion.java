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
        concentracion.setValorConcentracion(0.6f); // Agrega la letra 'f' para indicar que es un float

        concentracionDao.save(concentracion);

    }

    //@Test
    void getAllConcentracionAndDelete() {
        List<Concentracion> concentracions = concentracionDao.getAllConcentracion();
        for (Concentracion concentracion : concentracions) {
            concentracionDao.delete(concentracion);
        }

    }

}
