package com.utn.colaboradores.facades;

import com.utn.colaboradores.domain.Colaborador;
import com.utn.colaboradores.domain.enums.FormasDeColaborar;
import com.utn.colaboradores.dtos.ColaboradorDTO;
import com.utn.colaboradores.exceptions.ColaboradorExistenteException;
import com.utn.colaboradores.exceptions.ColaboradorNoEncontradoException;
import com.utn.colaboradores.repository.ColaboradorMapper;
import com.utn.colaboradores.repository.ColaboradorRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Service
@Transactional
public class ColaboradorFacade {

    private Map<String, Double> coeficientes;
    private ColaboradorRepository colaboradorRepository;
    private ColaboradorMapper colaboradorMapper;

    public ColaboradorFacade() {}
    public ColaboradorFacade(ColaboradorRepository colaboradorRepository) {
        Map<String, Double> coeficientes = new HashMap<>();
        coeficientes.put("dineroDonado", 0.5);
        coeficientes.put("viandasDistribuidas", 1.0);
        coeficientes.put("viandasDonadas", 1.5);
        coeficientes.put("heladerasReparadas", 2.0);
        this.coeficientes = coeficientes;
        this.colaboradorRepository = colaboradorRepository;
        this.colaboradorMapper = new ColaboradorMapper();
    }

    public void actualizarCoeficientesFormula(Double pesoDinero, Double pesoDistribuidas,
                                              Double pesoDonadas, Double pesoReparadas){
        coeficientes.put("dineroDonado", pesoDinero);
        coeficientes.put("viandasDistribuidas", pesoDistribuidas);
        coeficientes.put("viandasDonadas", pesoDonadas);
        coeficientes.put("heladerasReparadas", pesoReparadas);
    }

    public Colaborador agregarColaboradorAlSistema(String nombreYApellido, List<FormasDeColaborar> formasDeColaborar) {

        if(colaboradorRepository.existsColaboradorByNombreYApellido(nombreYApellido).isPresent()){
            throw new ColaboradorExistenteException("Ya existe un colaborador con ese idtarjeta");
        }

        Colaborador colaborador = new Colaborador(nombreYApellido, formasDeColaborar);
        return this.colaboradorRepository.save(colaborador);
    }

    public Colaborador obtenerColaboradorPorId(Long idTarjetaColaborador){
        Optional<Colaborador> colaborador = this.colaboradorRepository.findByIdTarjeta(idTarjetaColaborador);
        if(colaborador.isPresent()){
            return colaborador.get();
        }
        else{
            throw new ColaboradorNoEncontradoException("No existe colaborador con ese ID");
        }
    }

    public void actualizarFormasDeColaborar(Long idTarjetaColaborador, List<FormasDeColaborar> nuevasFormasDeColaborar){
        try{
            Colaborador colaboradorBuscado = this.obtenerColaboradorPorId(idTarjetaColaborador);
            colaboradorBuscado.setFormasDeColaborar(nuevasFormasDeColaborar);
        }catch (ColaboradorNoEncontradoException e){
            throw new ColaboradorNoEncontradoException("No existe un colaborador con ese ID");
        }
    }

    public Long calcularPuntos(Long idTarjetaColaborador) {

        try{
            Colaborador colaboradorBuscado = this.obtenerColaboradorPorId(idTarjetaColaborador);

            // Voy a buscar a viandas las donadas y a Logisgica las distribuidas TODO
            Long viandasDistribuidas = 1L ;
            Long viandasDonadas = 1L;

            Double pesoDinero = this.coeficientes.get("dineroDonado");
            Double pesoDistribuidas = this.coeficientes.get("viandasDistribuidas");
            Double pesoDonadas = this.coeficientes.get("viandasDonadas");
            Double pesoReparadas = this.coeficientes.get("heladerasReparadas");

            Long puntosCalculados = Math.round(colaboradorBuscado.getDineroDonado()*pesoDinero + viandasDistribuidas*pesoDistribuidas
                    + viandasDonadas*pesoDonadas + colaboradorBuscado.getHeladerasReparadas()*pesoReparadas);

            colaboradorBuscado.setPuntos(puntosCalculados);
            return puntosCalculados;

        }catch(ColaboradorNoEncontradoException e){
            throw new ColaboradorNoEncontradoException("No existe un colaborador con ese ID");
        }
    }

}
