package com.utn.heladeras.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class Heladera {

    private Integer id;
    private String nombre;
    private Integer cantidadDeViandas;
    // Collection<Vianda> viandas;
    Collection<Temperatura> temperaturas;
    SensorMovimiento sensor;
    SensorTemperatura sensorTemperatura;
    SensorConexion sensorConexion;
    private Boolean estaAbierta=Boolean.TRUE;
    private Boolean estaActiva=Boolean.TRUE;
    Collection<Retiro> retiros;

    public Heladera(Integer id, String nombre, Integer cantidadDeViandas) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadDeViandas = cantidadDeViandas;
        this.temperaturas = new ArrayList<>();
        this.sensor = new SensorMovimiento();
        this.sensorTemperatura = new SensorTemperatura();
        this.sensorConexion = new SensorConexion();
        this.estaAbierta = Boolean.TRUE;
        this.estaActiva = Boolean.TRUE;
        this.retiros = new ArrayList<>();
    }



}
