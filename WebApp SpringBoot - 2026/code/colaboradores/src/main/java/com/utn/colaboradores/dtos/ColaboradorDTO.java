package com.utn.colaboradores.dtos;

import com.utn.colaboradores.domain.enums.FormasDeColaborar;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ColaboradorDTO {

    private Long idTarjeta;
    private String nombreYApellido;
    private Long puntos;
    private List<FormasDeColaborar> formasDeColaborar;
    private Double dineroDonado;
    private Integer heladerasReparadas;

    public ColaboradorDTO(Long idTarjeta, String nombreYApellido, Long puntos, List<FormasDeColaborar> formasDeColaborar,
                          Double dineroDonado, Integer heladerasReparadas) {
        this.idTarjeta = idTarjeta;
        this.nombreYApellido = nombreYApellido;
        this.puntos = puntos;
        this.formasDeColaborar = formasDeColaborar;
        this.dineroDonado = dineroDonado;
        this.heladerasReparadas = heladerasReparadas;
    }
}
