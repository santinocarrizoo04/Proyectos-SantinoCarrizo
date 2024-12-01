package ar.edu.utn.dds.k3003.repositorios;

import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.model.Colaborador;
import ar.edu.utn.dds.k3003.model.enums.MisFormasDeColaborar;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ColaboradorRepository {

  private static AtomicLong seqId = new AtomicLong();
  private Collection<Colaborador> colaboradores;
  public ColaboradorRepository() {
    this.colaboradores = new ArrayList<>();
  }

  public Colaborador saveJPA(Colaborador colaborador, EntityManager em){
    colaborador.setPuntos((double)0);
    em.persist(colaborador);
    return colaborador;
  }
  public Colaborador save(Colaborador colaborador){
    if (Objects.isNull(colaborador.getId())) {
      colaborador.setId(seqId.getAndIncrement());
      this.colaboradores.add(colaborador);
    }
    return colaborador;
  }
  public Colaborador findByIdJPA(Long id, EntityManager em) {
    return em.find(Colaborador.class, id);
  }
  public Colaborador findById(Long id) {
    Optional<Colaborador> first =
        this.colaboradores.stream().filter(x -> x.getId().equals(id)).findFirst();
    return first.orElseThrow(
        () -> new NoSuchElementException(String.format("No hay un colaborador de id: %s", id)));
  }

  public void modificarFormasDeJPA(Long id, List<MisFormasDeColaborar> formas, EntityManager em){
    this.findByIdJPA(id, em).setFormas(formas);
  }
  public void modificarFormasDe(Long id, List<MisFormasDeColaborar> formas) {
    this.findById(id).setFormas(formas);
  }

  public void remove(Colaborador colaborador){
    this.colaboradores = this.colaboradores.stream().filter(x -> !x.getId().equals(colaborador.getId())).toList();
  }

}
