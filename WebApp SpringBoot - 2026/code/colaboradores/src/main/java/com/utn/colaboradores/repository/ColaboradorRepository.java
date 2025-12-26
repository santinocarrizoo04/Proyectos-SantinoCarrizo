package com.utn.colaboradores.repository;

import com.utn.colaboradores.domain.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    // Spring parsea automaticamente el nombre findBy, deleteBy y genera automaticamente las Querys correctas
    // Por ahora no necesito nada mas que esto, pero se pueden hacer querys mas complejas a mano
    Optional<Colaborador> findByIdTarjeta(Long idTarjeta);
    Optional<Colaborador> findByNombreYApellido(String nombreyApellido);
    void deleteByIdTarjeta(Long idTarjeta);
    Optional<Colaborador> existsColaboradorByNombreYApellido(String nombreYApellido);

}
