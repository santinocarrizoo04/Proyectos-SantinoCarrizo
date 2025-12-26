package com.utn.colaboradores.dtos;

import com.utn.colaboradores.domain.enums.FormasDeColaborar;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public class ColaboradorDTO {

    private Long idTarjeta;
    private String nombreYApellido;
    private Long puntos;
    private List<FormasDeColaborar> formasDeColaborar;
    private Double dineroDonado;
    private Integer heladerasReparadas;
    private Double cuentaCorriente;

    public ColaboradorDTO(String nombreYApellido, List<FormasDeColaborar> formasDeColaborar) {
        this.nombreYApellido = nombreYApellido;
        this.formasDeColaborar = formasDeColaborar;
        this.dineroDonado = 0.0;
        this.heladerasReparadas = 0;
        this.puntos = 0L;
        this.cuentaCorriente = 0.0;
    }
}
