package com.utn.colaboradores.facades;

import com.utn.colaboradores.domain.Colaborador;
import com.utn.colaboradores.domain.enums.FormasDeColaborar;
import com.utn.colaboradores.dtos.ColaboradorDTO;
import com.utn.colaboradores.dtos.FormulaDTO;
import com.utn.colaboradores.exceptions.ColaboradorExistenteException;
import com.utn.colaboradores.exceptions.ColaboradorNoEncontradoException;
import com.utn.colaboradores.exceptions.DineroInsuficienteException;
import com.utn.colaboradores.exceptions.FormaDeColaborarInvalidaException;
import com.utn.colaboradores.repository.ColaboradorMapper;
import com.utn.colaboradores.repository.ColaboradorRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Service
@Transactional
public class ColaboradorFacade {

    private Map<String, Double> coeficientes;
    private ColaboradorRepository colaboradorRepository;
    private ColaboradorMapper colaboradorMapper;

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

    public void actualizarCoeficientesFormula(FormulaDTO formula) {
        coeficientes.put("dineroDonado", formula.getPesoDinero());
        coeficientes.put("viandasDistribuidas", formula.getPesoDistribuidas());
        coeficientes.put("viandasDonadas", formula.getPesoDonadas());
        coeficientes.put("heladerasReparadas", formula.getPesoReparadas());
    }

    public ColaboradorDTO agregarColaboradorAlSistema(ColaboradorDTO colaboradorDTO) {

        if(colaboradorRepository.findByNombreYApellido(colaboradorDTO.getNombreYApellido()).isPresent()){
            throw new ColaboradorExistenteException("Ya existe un colaborador con ese nombreYApellido");
        }

        Colaborador colaborador = new Colaborador(colaboradorDTO.getNombreYApellido(), colaboradorDTO.getFormasDeColaborar());
        return this.colaboradorMapper.fromDomainToDTO(this.colaboradorRepository.save(colaborador));
    }

    public ColaboradorDTO obtenerColaboradorPorId(Long idTarjetaColaborador){
        Optional<Colaborador> colaborador = this.colaboradorRepository.findByIdTarjeta(idTarjetaColaborador);
        if(colaborador.isPresent()){
            return this.colaboradorMapper.fromDomainToDTO(colaborador.get());
        }
        else{
            throw new ColaboradorNoEncontradoException("No existe colaborador con ese ID");
        }
    }

    public ColaboradorDTO actualizarFormasDeColaborar(Long idTarjetaColaborador, List<FormasDeColaborar> nuevasFormasDeColaborar){
        try{
            Colaborador colaboradorBuscado = this.colaboradorRepository.findByIdTarjeta(idTarjetaColaborador).get();
            colaboradorBuscado.setFormasDeColaborar(nuevasFormasDeColaborar);
            return this.colaboradorMapper.fromDomainToDTO(colaboradorBuscado);
        }catch (ColaboradorNoEncontradoException e){
            throw new ColaboradorNoEncontradoException("No existe un colaborador con ese ID");
        }
    }

    public ColaboradorDTO sumarDonacionDinero(Long idTarjetaColaborador, Double dineroDonado){
        try{
            Colaborador colaboradorBuscado = this.colaboradorRepository.findByIdTarjeta(idTarjetaColaborador).get();
            if(colaboradorBuscado.getCuentaCorriente() >= dineroDonado &&
                    colaboradorBuscado.getFormasDeColaborar().contains(FormasDeColaborar.DONADORDEDINERO)){
                colaboradorBuscado.retirarDinero(dineroDonado);
                colaboradorBuscado.sumarDonacionDeDinero(dineroDonado);
                return this.colaboradorMapper.fromDomainToDTO(colaboradorBuscado);
            }else if(colaboradorBuscado.getFormasDeColaborar().contains(FormasDeColaborar.DONADORDEDINERO)){
                throw new DineroInsuficienteException("Dinero insuficiente en cuenta corriente");
            }else{
                throw new FormaDeColaborarInvalidaException("El colaborador no esta habilitado a donar dinero");
            }
        }catch (ColaboradorNoEncontradoException e){
            throw new ColaboradorNoEncontradoException("No existe un colaborador con ese ID");
        }
    }

    public ColaboradorDTO sumarArreglosHeladeras(Long idTarjetaColaborador, Integer cantidadHeladeras){
        try{
            Colaborador colaboradorBuscado = this.colaboradorRepository.findByIdTarjeta(idTarjetaColaborador).get();
            if(colaboradorBuscado.getFormasDeColaborar().contains(FormasDeColaborar.TECNICO)){
            colaboradorBuscado.sumarReparacionesDeHeladeras(cantidadHeladeras);
            return this.colaboradorMapper.fromDomainToDTO(colaboradorBuscado);
            }else{
                throw new FormaDeColaborarInvalidaException("El colaborador no esta habilitado a arreglar heladeras");
            }
        }catch (ColaboradorNoEncontradoException e){
            throw new ColaboradorNoEncontradoException("No existe un colaborador con ese ID");
        }
    }

    public ColaboradorDTO depositarDinero(Long idTarjetaColaborador, Double dineroDepositado){
        try{
            Colaborador colaboradorBuscado = this.colaboradorRepository.findByIdTarjeta(idTarjetaColaborador).get();
            colaboradorBuscado.depositarDinero(dineroDepositado);
            return this.colaboradorMapper.fromDomainToDTO(colaboradorBuscado);
        }catch (ColaboradorNoEncontradoException e){
            throw new ColaboradorNoEncontradoException("No existe un colaborador con ese ID");
        }
    }

    public ColaboradorDTO retirarDinero(Long idTarjetaColaborador, Double dineroRetirado){
        try{
            Colaborador colaboradorBuscado = this.colaboradorRepository.findByIdTarjeta(idTarjetaColaborador).get();
            if(colaboradorBuscado.getCuentaCorriente() >= dineroRetirado){
                colaboradorBuscado.retirarDinero(dineroRetirado);
                return this.colaboradorMapper.fromDomainToDTO(colaboradorBuscado);
            }else{
                throw new DineroInsuficienteException("Dinero insuficiente en cuenta corriente");
            }
        }catch (ColaboradorNoEncontradoException e){
            throw new ColaboradorNoEncontradoException("No existe un colaborador con ese ID");
        }
    }

    public ColaboradorDTO sumarDonacionViandas(Long idTarjetaColaborador, Integer cantidadViandas){
        try{
            Colaborador colaboradorBuscado = this.colaboradorRepository.findByIdTarjeta(idTarjetaColaborador).get();
            if(colaboradorBuscado.getFormasDeColaborar().contains(FormasDeColaborar.DONADORDEVIANDAS)){
                // Comunicacion con VIANDAS TODO
                return this.colaboradorMapper.fromDomainToDTO(colaboradorBuscado);
            }else{
                throw new FormaDeColaborarInvalidaException("El colaborador no esta habilitado a donar viandas");
            }
        }catch (ColaboradorNoEncontradoException e){
            throw new ColaboradorNoEncontradoException("No existe un colaborador con ese ID");
        }
    }

    public ColaboradorDTO sumarTransportesViandas(Long idTarjetaColaborador, Integer cantidadTraslados){
        try{
            Colaborador colaboradorBuscado = this.colaboradorRepository.findByIdTarjeta(idTarjetaColaborador).get();
            if(colaboradorBuscado.getFormasDeColaborar().contains(FormasDeColaborar.TRANSPORTADOR)){
                // Comunicacion con LOGISTICA TODO
                return this.colaboradorMapper.fromDomainToDTO(colaboradorBuscado);
            }else{
                throw new FormaDeColaborarInvalidaException("El colaborador no esta habilitado a transportar viandas");
            }
        }catch (ColaboradorNoEncontradoException e){
            throw new ColaboradorNoEncontradoException("No existe un colaborador con ese ID");
        }
    }

    public ColaboradorDTO calcularPuntos(Long idTarjetaColaborador) {

        try{
            Colaborador colaboradorBuscado = this.colaboradorRepository.findByIdTarjeta(idTarjetaColaborador).get();

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
            return this.colaboradorMapper.fromDomainToDTO(colaboradorBuscado);

        }catch(ColaboradorNoEncontradoException e){
            throw new ColaboradorNoEncontradoException("No existe un colaborador con ese ID");
        }
    }

    public HttpStatus borrarColaboradorPorId(Long idTarjetaColaborador) {
        this.colaboradorRepository.deleteById(idTarjetaColaborador);
        return HttpStatus.OK;
    }

    public void resetDB(){
        this.colaboradorRepository.deleteAll();
    }

}
