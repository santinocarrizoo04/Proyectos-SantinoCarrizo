package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.dtos.EstadoTrasladoEnum;
import ar.edu.utn.dds.k3003.facades.dtos.RutaDTO;
import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import ar.edu.utn.dds.k3003.facades.exceptions.TrasladoNoAsignableException;
import ar.edu.utn.dds.k3003.model.dtos.IncidenteDTO;
import ar.edu.utn.dds.k3003.model.enums.EstadoIncidenteEnum;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Objects;

public class IncidenteController {

    private final Fachada fachada;

    public IncidenteController(Fachada fachada) {
        this.fachada = fachada;
    }

    public void agregar(Context context) {
        var incidenteDTO = context.bodyAsClass(IncidenteDTO.class);
        var incidenteDTORta = this.fachada.crearIncidente(incidenteDTO);
        context.json(incidenteDTORta);
        context.status(HttpStatus.CREATED);
    }

    public void obtener(Context context) throws NoSuchElementException {
        var id = context.pathParamAsClass("id", Long.class).get();
        try {
            var incidenteDTO = this.fachada.buscarIncidente(id);
            context.json(incidenteDTO);
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException(ex.getLocalizedMessage());
        }
    }

    public void eliminar(Context ctx) {
        try {
            fachada.incidenteRepository.borrarTodo();
            ctx.json(Collections.singletonMap("message", "Se eliminaron todos los incidentes"));
            ctx.status(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public void actualizar(Context context) {
        var id = context.pathParamAsClass("id", Long.class).get();
        var incidenteDTO = context.bodyAsClass(IncidenteDTO.class);
        try {
            var incidenteDTORta = this.fachada.actualizarIncidente(id, incidenteDTO.getEstadoIncidente());
            context.json(incidenteDTORta);
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException(ex.getLocalizedMessage());
        }
    }

    public void listarPorHeladera(Context context) {
        var heladeraId = context.pathParamAsClass("heladeraId", Long.class).get();
        try {
            var incidentes = this.fachada.listarIncidentesPorHeladera(heladeraId);
            context.json(incidentes);
            context.status(HttpStatus.OK);
        } catch (Exception ex) {
            throw new RuntimeException("Error al listar incidentes: " + ex.getMessage());
        }
    }

}