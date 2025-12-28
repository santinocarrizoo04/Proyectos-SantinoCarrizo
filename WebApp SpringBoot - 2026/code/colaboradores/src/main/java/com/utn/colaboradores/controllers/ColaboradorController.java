package com.utn.colaboradores.controllers;

import com.utn.colaboradores.domain.Colaborador;
import com.utn.colaboradores.domain.enums.FormasDeColaborar;
import com.utn.colaboradores.dtos.ColaboradorDTO;
import com.utn.colaboradores.dtos.FormulaDTO;
import com.utn.colaboradores.facades.ColaboradorFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {

    private ColaboradorFacade colaboradorFacade; //Autowired por anotation @Service

    public ColaboradorController(ColaboradorFacade colaboradorFacade) {
        this.colaboradorFacade = colaboradorFacade;

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ColaboradorDTO> postColaborador(@RequestBody ColaboradorDTO colaboradorDTO) {

        ColaboradorDTO colaboradorAgregado = this.colaboradorFacade.agregarColaboradorAlSistema(colaboradorDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idTarjeta}")
                .buildAndExpand(colaboradorAgregado.getIdTarjeta())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(colaboradorAgregado);
    }

    @PostMapping("/donacionDinero")
    public ResponseEntity<ColaboradorDTO> postDonacionDinero(@RequestParam Long idTarjeta, @RequestBody Double dineroDonado ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.colaboradorFacade.sumarDonacionDinero(idTarjeta, dineroDonado));
    }

    @PostMapping("/arreglos")
    public ResponseEntity<ColaboradorDTO> postArreglos(@RequestParam Long idTarjeta, @RequestBody Integer cantidadHeladerasArregladas ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.colaboradorFacade.sumarArreglosHeladeras(idTarjeta, cantidadHeladerasArregladas));
    }

    @PostMapping("/deposito")
    public ResponseEntity<ColaboradorDTO> postDeposito(@RequestParam Long idTarjeta, @RequestBody Double dineroDepositado ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.colaboradorFacade.depositarDinero(idTarjeta, dineroDepositado));
    }

    @PostMapping("/retiro")
    public ResponseEntity<ColaboradorDTO> postRetiro(@RequestParam Long idTarjeta, @RequestBody Double dineroRetirado ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.colaboradorFacade.retirarDinero(idTarjeta, dineroRetirado));
    }

    @PostMapping("/donacionViandas")
    public ResponseEntity<ColaboradorDTO> postDonacionViandas(@RequestParam Long idTarjeta, @RequestBody Integer cantidadViandas ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.colaboradorFacade.sumarDonacionViandas(idTarjeta, cantidadViandas));
    }

    @PostMapping("/trasladoViandas")
    public ResponseEntity<ColaboradorDTO> postTrasladoViandas(@RequestParam Long idTarjeta, @RequestBody Integer cantidadTraslados ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.colaboradorFacade.sumarTransportesViandas(idTarjeta, cantidadTraslados));
    }

    @GetMapping
    public ResponseEntity<ColaboradorDTO> getColaboradores(@RequestParam Long idTarjeta) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.colaboradorFacade.obtenerColaboradorPorId(idTarjeta));
    }

    @GetMapping("/puntos")
    public ResponseEntity<ColaboradorDTO> getPuntos(@RequestParam Long idTarjeta) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.colaboradorFacade.calcularPuntos(idTarjeta));
    }

    @PatchMapping("/formasDeColaborar")
    public ResponseEntity<ColaboradorDTO> actualizarFormasDeColaborar(@RequestParam Long idTarjeta,
                                                                      @RequestBody List<FormasDeColaborar> formasDeColaborar){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.colaboradorFacade.actualizarFormasDeColaborar(idTarjeta, formasDeColaborar));
    }

    @PutMapping("/formula")
    public ResponseEntity actualizarFormula(@RequestBody FormulaDTO formula) {
        this.colaboradorFacade.actualizarCoeficientesFormula(formula);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity borrarColaborador(@RequestParam Long idTarjeta) {
        this.colaboradorFacade.borrarColaboradorPorId(idTarjeta);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/resetDB")
    public void resetDB() {
        this.colaboradorFacade.resetDB();
    }

}
