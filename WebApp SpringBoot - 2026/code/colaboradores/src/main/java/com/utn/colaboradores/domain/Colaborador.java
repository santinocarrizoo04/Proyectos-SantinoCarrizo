package com.utn.colaboradores.domain;

import com.utn.colaboradores.domain.enums.FormasDeColaborar;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Colaboradores")
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarjeta;

    @Column(length = 50, nullable = false)
    private String nombreYApellido;

    @Column
    private Long puntos;

    @ElementCollection(targetClass = FormasDeColaborar.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "colaborador_formas_de_colaborar", joinColumns = @JoinColumn(name = "idTarjeta"))
    @Column(name = "forma_de_colaborar")
    private List<FormasDeColaborar> formasDeColaborar;

    @Column
    private Double dineroDonado;

    @Column
    private Integer heladerasReparadas;

    public Colaborador() {}
    public Colaborador(String name, List<FormasDeColaborar> formasDeColaborar) {
        this.nombreYApellido = name;
        this.formasDeColaborar = formasDeColaborar;
        this.dineroDonado = 0.0;
        this.heladerasReparadas = 0;
        this.puntos = 0L;
    }

    public void sumarDonacionDeDinero(Double dineroDonado) {
        this.dineroDonado += dineroDonado;
    }
    public void sumarReparacionesDeHeladeras(Integer cantidad) {
        this.heladerasReparadas += cantidad;
    }
}
