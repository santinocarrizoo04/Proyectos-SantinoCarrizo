package com.utn.colaboradores.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormulaDTO {
    private Double pesoDinero;
    private Double pesoDistribuidas;
    private Double pesoDonadas;
    private Double pesoReparadas;

    public FormulaDTO(Double pesoDinero, Double pesoDistribuidas, Double pesoDonadas, Double pesoReparadas) {
        this.pesoDinero = pesoDinero;
        this.pesoDistribuidas = pesoDistribuidas;
        this.pesoDonadas = pesoDonadas;
        this.pesoReparadas = pesoReparadas;
    }
}
