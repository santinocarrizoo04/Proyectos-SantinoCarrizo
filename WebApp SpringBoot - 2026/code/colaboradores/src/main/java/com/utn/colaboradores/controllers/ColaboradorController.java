package com.utn.colaboradores.controllers;

import com.utn.colaboradores.domain.enums.FormasDeColaborar;
import com.utn.colaboradores.dtos.ColaboradorDTO;
import com.utn.colaboradores.dtos.FormulaDTO;
import com.utn.colaboradores.facades.ColaboradorFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ColaboradorController {

    private ColaboradorFacade colaboradorFacade; //Autowired por anotation @Service

    public ColaboradorController(ColaboradorFacade colaboradorFacade) {
        this.colaboradorFacade = colaboradorFacade;

    }

    @PostMapping("/colaboradores")
    public ColaboradorDTO postColaborador(@RequestBody ColaboradorDTO colaboradorDTO) {
        return this.colaboradorFacade.agregarColaboradorAlSistema(colaboradorDTO);
    }

    @PostMapping("/colaboradores/donacionDinero")
    public ColaboradorDTO postDonacionDinero(@RequestParam Long idTarjeta, @RequestBody Double dineroDonado ) {
        return this.colaboradorFacade.sumarDonacionDinero(idTarjeta, dineroDonado);
    }

    @PostMapping("/colaboradores/arreglos")
    public ColaboradorDTO postArreglos(@RequestParam Long idTarjeta, @RequestBody Integer cantidadHeladerasArregladas ) {
        return this.colaboradorFacade.sumarArreglosHeladeras(idTarjeta, cantidadHeladerasArregladas);
    }

    @PostMapping("/colaboradores/deposito")
    public ColaboradorDTO postDeposito(@RequestParam Long idTarjeta, @RequestBody Double dineroDepositado ) {
        return this.colaboradorFacade.depositarDinero(idTarjeta, dineroDepositado);
    }

    @PostMapping("/colaboradores/retiro")
    public ColaboradorDTO postRetiro(@RequestParam Long idTarjeta, @RequestBody Double dineroRetirado ) {
        return this.colaboradorFacade.retirarDinero(idTarjeta, dineroRetirado);
    }

    @PostMapping("/colaboradores/donacionViandas")
    public ColaboradorDTO postDonacionViandas(@RequestParam Long idTarjeta, @RequestBody Integer cantidadViandas ) {
        return this.colaboradorFacade.sumarDonacionViandas(idTarjeta, cantidadViandas);
    }

    @PostMapping("/colaboradores/trasladoViandas")
    public ColaboradorDTO postTrasladoViandas(@RequestParam Long idTarjeta, @RequestBody Integer cantidadTraslados ) {
        return this.colaboradorFacade.sumarDonacionViandas(idTarjeta, cantidadTraslados);
    }

    @GetMapping("/colaboradores")
    public ColaboradorDTO getColaboradores(@RequestParam Long idTarjeta) {
        return this.colaboradorFacade.obtenerColaboradorPorId(idTarjeta);
    }

    @GetMapping("/colaboradores/puntos")
    public ColaboradorDTO getPuntos(@RequestParam Long idTarjeta) {
        return this.colaboradorFacade.calcularPuntos(idTarjeta);
    }

    @PatchMapping("/colaboradores/formasDeColaborar")
    public ColaboradorDTO actualizarFormasDeColaborar(@RequestParam Long idTarjeta,
                                                      @RequestBody List<FormasDeColaborar> formasDeColaborar) {
        return this.colaboradorFacade.actualizarFormasDeColaborar(idTarjeta, formasDeColaborar);
    }

    @PutMapping("/colaboradores/formula")
    public void actualizarFormula(@RequestBody FormulaDTO formula) {
        this.colaboradorFacade.actualizarCoeficientesFormula(formula);
    }

    @DeleteMapping("/colaboradores")
    public void borrarColaborador(@RequestParam Long idTarjeta) {
        this.colaboradorFacade.borrarColaboradorPorId(idTarjeta);
    }

    @DeleteMapping("/colaboradores/resetDB")
    public void resetDB() {
        this.colaboradorFacade.resetDB();
    }

}
