package com.utn.colaboradores.domain;

import com.utn.colaboradores.domain.enums.FormasDeColaborar;
import com.utn.colaboradores.exceptions.DineroInsuficienteException;
import com.utn.colaboradores.exceptions.FormaDeColaborarInvalidaException;
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

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "colaborador_formas_de_colaborar", joinColumns = @JoinColumn(name = "idTarjeta"))
    @Column(name = "forma_de_colaborar")
    private List<FormasDeColaborar> formasDeColaborar;

    @Column
    private Double dineroDonado;

    @Column
    private Integer heladerasReparadas; // Deberia saberlo Heladeras

    @Column
    private Double cuentaCorriente;

    public Colaborador() {}
    public Colaborador(String name, List<FormasDeColaborar> formasDeColaborar) {
        this.nombreYApellido = name;
        this.formasDeColaborar = formasDeColaborar;
        this.dineroDonado = 0.0;
        this.heladerasReparadas = 0;
        this.puntos = 0L;
        this.cuentaCorriente = 0.0;
    }

    public void sumarDonacionDeDinero(Double dineroDonado) {
        if(this.formasDeColaborar.contains(FormasDeColaborar.DONADORDEDINERO) && this.cuentaCorriente >= dineroDonado){
            this.retirarDinero(dineroDonado);
            this.dineroDonado += dineroDonado;
        }else if(this.cuentaCorriente >= dineroDonado){
            throw new FormaDeColaborarInvalidaException("El colaborador no esta habilitado a donar dinero");
        }else{
            throw new DineroInsuficienteException("Dinero insuficiente en cuenta corriente para hacer la donacion");
        }
    }

    public void sumarReparacionesDeHeladeras(Integer cantidad) {
        if(this.formasDeColaborar.contains(FormasDeColaborar.TECNICO)){
            this.heladerasReparadas += cantidad;
        }else{
            throw new FormaDeColaborarInvalidaException("El colaborador no esta habilitado a reparar heladeras");
        }
    }

    public void depositarDinero(Double dinero){
        this.cuentaCorriente += dinero;
    }

    public void retirarDinero(Double dinero) {
        if(this.cuentaCorriente >= dinero){
            this.cuentaCorriente -= dinero;
        }else{
            throw new DineroInsuficienteException("Dinero insuficiente en cuenta corriente para realizar el retiro");
        }
    }
}
