package com.utn.colaboradores.repository;

import com.utn.colaboradores.domain.Colaborador;
import com.utn.colaboradores.dtos.ColaboradorDTO;

public class ColaboradorMapper {

    public ColaboradorMapper() {}

    public ColaboradorDTO fromDomainToDTO(Colaborador colaborador) {
        ColaboradorDTO colaboradorDTO =
                new ColaboradorDTO(colaborador.getIdTarjeta(), colaborador.getNombreYApellido(), colaborador.getPuntos(),
                        colaborador.getFormasDeColaborar(), colaborador.getDineroDonado(), colaborador.getHeladerasReparadas());
        return colaboradorDTO;
    }

    public Colaborador fromDTOtoDomain(ColaboradorDTO colaboradorDTO) {
        Colaborador colaborador = new Colaborador(colaboradorDTO.getNombreYApellido(),
                                                    colaboradorDTO.getFormasDeColaborar());
        colaborador.setIdTarjeta(colaboradorDTO.getIdTarjeta());
        colaborador.setDineroDonado(colaboradorDTO.getDineroDonado());
        colaborador.setPuntos(colaboradorDTO.getPuntos());
        colaborador.setHeladerasReparadas(colaboradorDTO.getHeladerasReparadas());
        return colaborador;
    }
}
