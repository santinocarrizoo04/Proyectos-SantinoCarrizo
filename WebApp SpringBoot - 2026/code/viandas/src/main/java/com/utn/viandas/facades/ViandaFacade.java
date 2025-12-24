package com.utn.viandas.facades;

import com.utn.viandas.domain.enums.EstadoVianda;
import com.utn.viandas.dtos.ViandaDTO;

import java.util.List;

public class ViandaFacade {

    public ViandaFacade() {}


    public void agregar(ViandaDTO viandaDTO) {

    }

    public void modificarEstado(String qr, EstadoVianda nuevoEstado) {

    }

    public List<ViandaDTO> viandasDeColaborador(Long idTarjetaColaborador, Integer mes, Integer anio) {
        return null;
    }

    public ViandaDTO buscarPorQR(String qr) {
        return null;
    }

    public void evaluarVencimiento(String qr) {
    }

}
