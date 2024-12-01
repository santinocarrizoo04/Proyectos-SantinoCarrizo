package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.facades.dtos.EstadoTrasladoEnum;
import ar.edu.utn.dds.k3003.model.Ruta;
import ar.edu.utn.dds.k3003.model.Traslado;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

public class TrasladoRepository {
    private final EntityManager entityManager;

    public TrasladoRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Traslado save(Traslado traslado) {

        if (Objects.isNull(traslado.getId())) {
            entityManager.getTransaction().begin();
            entityManager.persist(traslado);
            entityManager.getTransaction().commit();
        }

        return traslado;
    }

    public Traslado findById(Long id) throws NoSuchElementException {

        Traslado traslado = entityManager.find(Traslado.class, id);

        if (Objects.isNull(traslado)) {
            throw new NoSuchElementException("No hay una ruta de id: " + id);
        }

        return traslado;
    }

    public List<Traslado> findByCollaboratorId(Long colaboradorId, Integer mes, Integer anio) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Traslado> criteriaQuery = criteriaBuilder.createQuery(Traslado.class);
        Root<Traslado> root = criteriaQuery.from(Traslado.class);

        Predicate colaborador_id = criteriaBuilder.equal(root.get("collaboratorId"), colaboradorId);
        Predicate fecha_mes = criteriaBuilder.equal(criteriaBuilder.function("MONTH", Integer.class, root.get("fechaTraslado")), mes);
        Predicate fecha_anio = criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, root.get("fechaTraslado")), anio);

        criteriaQuery.select(root).where(criteriaBuilder.and(colaborador_id, fecha_mes, fecha_anio));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Traslado> findByRuta(Ruta ruta) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Traslado> criteriaQuery = criteriaBuilder.createQuery(Traslado.class);
        Root<Traslado> root = criteriaQuery.from(Traslado.class);

        Predicate ruta_query = criteriaBuilder.equal(root.get("ruta"), ruta);

        criteriaQuery.select(root).where(criteriaBuilder.and(ruta_query));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public void modificarEstado(Long idTraslado, EstadoTrasladoEnum estado) {

        entityManager.getTransaction().begin();
        Traslado traslado = this.findById(idTraslado);
        traslado.setEstado(estado);
        entityManager.merge(traslado);
        entityManager.getTransaction().commit();
    }

    public void borrarTodo() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Traslado").executeUpdate();
        entityManager.getTransaction().commit();
    }
}
