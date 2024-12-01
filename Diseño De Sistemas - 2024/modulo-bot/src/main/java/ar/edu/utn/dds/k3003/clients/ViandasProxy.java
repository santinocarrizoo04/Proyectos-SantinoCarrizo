package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;
import ar.edu.utn.dds.k3003.facades.dtos.RutaDTO;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import lombok.SneakyThrows;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ViandasProxy implements FachadaViandas {

    private final String endpoint;
    private final ViandasRetrofitClient service;

    public ViandasProxy(ObjectMapper objectMapper) {
        var env = System.getenv();
        this.endpoint = env.getOrDefault("URL_VIANDAS", "http://localhost:8081/");
        var retrofit = new Retrofit.Builder().baseUrl(this.endpoint).addConverterFactory(JacksonConverterFactory.create(objectMapper)).build();
        this.service = retrofit.create(ViandasRetrofitClient.class);
    }

    @Override
    public ViandaDTO agregar(ViandaDTO viandaDTO) {
        agregar(viandaDTO.getCodigoQR(), viandaDTO.getFechaElaboracion(), viandaDTO.getEstado(), viandaDTO.getColaboradorId(), viandaDTO.getHeladeraId());
        return viandaDTO;
    }
    public void agregar(String codigoQR, LocalDateTime fechaElaboracion, EstadoViandaEnum estado, Long colaboradorId, Integer heladeraId) {
        try {
            Response<Void> response = service.agregar(new ViandaDTO(codigoQR, fechaElaboracion, estado, colaboradorId, heladeraId)).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("No se pudo crear la vianda: " + response.errorBody().string());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al crear la vianda: ", e);
        }
    }

    @Override
    public ViandaDTO modificarEstado(String s, EstadoViandaEnum estadoViandaEnum) throws NoSuchElementException {
        Response <ViandaDTO> execute=null;
        try{
            execute=service.modificarEstado(s,estadoViandaEnum).execute();
            if (execute.isSuccessful()) {
                return execute.body();
            }
            if (execute.code() == HttpStatus.NOT_FOUND.getCode()) {
                throw new NoSuchElementException("No se encontró el QR: " + s);
            }
            throw new RuntimeException("Error al obtener : " + execute.code());
        }catch (IOException e){
            throw new RuntimeException("Error de QR", e);
        }
    }

    @Override
    public List<ViandaDTO> viandasDeColaborador(Long aLong, Integer integer, Integer integer1)
            throws NoSuchElementException {
        return null;
    }

    @SneakyThrows
    @Override
    public ViandaDTO buscarXQR(String qr) throws NoSuchElementException {
        Response<ViandaDTO> execute = service.get(qr).execute();
        if (execute.isSuccessful()) {
            return execute.body();
        }
        if (execute.code() == HttpStatus.NOT_FOUND.getCode()) {
            throw new NoSuchElementException("No se encontro la vianda " + qr);
        }
        throw new RuntimeException("Error conectandose con el componente viandas");

    }

    @Override
    public void setHeladerasProxy(FachadaHeladeras fachadaHeladeras) {}

    @Override
    public boolean evaluarVencimiento(String s) throws NoSuchElementException {
        return false;
    }

    @Override
    public ViandaDTO modificarHeladera(String s, int i) {
        return null;
    }
}