package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.model.dtos.NotificacionDTO;
import ar.edu.utn.dds.k3003.model.enums.MisFormasDeColaborar;

import java.util.List;

import javax.persistence.*;
@Entity
@Table(name = "colaborador")
public class Colaborador {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column (name = "nombre")
  private String nombre;
  @Column
  private Double puntos = (double) 0;

  @ElementCollection(targetClass = MisFormasDeColaborar.class)
  @Enumerated(EnumType.STRING) // Puedes usar EnumType.ORDINAL si prefieres almacenar índices
  @CollectionTable(name = "colaborador_formas_de_colaborar", joinColumns = @JoinColumn(name = "colaborador_id"))
  @Column(name = "forma_de_colaborar")
  private List<MisFormasDeColaborar> formas;

  @Column
  private Double dineroDonado;

  @Column
  private Double heladerasReparadas;

  @Column
  private Long chatID;

  public Colaborador() {}

  public void notificar(NotificacionDTO notificacionDTO){

    System.out.println("Mensaje Recibido: " + notificacionDTO.getMsg());

  }
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Double getPuntos() {
    return puntos;
  }

  public void setPuntos(Double puntos) {
    this.puntos = puntos;
  }

  public List<MisFormasDeColaborar> getFormas() {
    return formas;
  }

  public void setFormas(List<MisFormasDeColaborar> formas) {
    this.formas = formas;
  }

  public Double getDineroDonado() {
    return dineroDonado;
  }

  public void setDineroDonado(Double dineroDonado) {
    this.dineroDonado = dineroDonado;
  }

  public Double getHeladerasReparadas() {
    return heladerasReparadas;
  }

  public void setHeladerasReparadas(Double heladerasReparadas) {
    this.heladerasReparadas = heladerasReparadas;
  }

  public Long getChatID() {
    return chatID;
  }

  public void setChatID(Long chatID) {
    this.chatID = chatID;
  }
}
