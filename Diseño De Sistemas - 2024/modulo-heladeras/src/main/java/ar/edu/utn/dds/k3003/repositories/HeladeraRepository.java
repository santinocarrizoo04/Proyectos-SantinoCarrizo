package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.model.*;
import ar.edu.utn.dds.k3003.model.Subscriptor.Subscriptor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.*;
import java.util.Collection;

import java.util.concurrent.atomic.AtomicLong;
@Getter
@Setter
public class HeladeraRepository {
    private static AtomicLong seqId = new AtomicLong();

    public Collection<Heladera> heladeras;

    //private EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("tpDDS");
    private EntityManagerFactory entityManagerFactory;
    public EntityManager entityManager;

    public HeladeraRepository() {
        this.heladeras = new ArrayList<>();
        //this.entityManager=entityManagerFactory.createEntityManager();
    }


    public void guardar(Heladera heladera) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(heladera);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    public Heladera findById(long id) {

        Heladera heladeraEncontrada = entityManager.find(Heladera.class, (int) id);
        return heladeraEncontrada;

    }

    public void remove(Heladera heladera) {
        this.heladeras.remove(heladera);
    }

    public List<Heladera> findAllHeladeras() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Heladera> criteriaQuery = criteriaBuilder.createQuery(Heladera.class);
        Root<Heladera> heladeraRoot = criteriaQuery.from(Heladera.class);
        criteriaQuery.select(heladeraRoot);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public void guardarTemperatura(Temperatura temperatura) {
        EntityTransaction t = null;
        try {
            t = entityManager.getTransaction();
            t.begin();
            entityManager.persist(temperatura);
            t.commit();
        } catch (Exception e) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }

    public void actualizar(Heladera heladera) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(heladera);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Temperatura> obtenerTemperaturasDeHeladera(int heladeraId) {
        EntityTransaction transaction = entityManager.getTransaction();
        List<Temperatura> temperaturas = null;
        try {
            transaction.begin();
            TypedQuery<Temperatura> query = entityManager.createQuery(
                    "SELECT t FROM Temperatura t WHERE t.heladeraId = :heladeraId", Temperatura.class);
            query.setParameter("heladeraId", heladeraId);
            temperaturas = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return temperaturas;
    }

    public void guardarVianda(Vianda vianda) {
        EntityTransaction t = entityManager.getTransaction();
        try {
            t.begin();
            entityManager.persist(vianda);
            entityManager.merge(vianda);
            t.commit();
        } catch (Exception e) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }

    public void eliminarVianda(Vianda vianda) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Vianda viandaAEliminar = entityManager.merge(vianda);
            entityManager.remove(viandaAEliminar);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;  // Vuelve a lanzar la excepción para que el llamador pueda manejarla
        }
    }

    public List<Vianda> obtenerViandasDeHeladera(int heladeraId) {
        EntityTransaction transaction = entityManager.getTransaction();
        List<Vianda> viandas = null;
        try {
            transaction.begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Vianda> cq = cb.createQuery(Vianda.class);
            Root<Heladera> heladeraRoot = cq.from(Heladera.class);
            Join<Heladera, Vianda> viandasJoin = heladeraRoot.join("viandas");

            cq.select(viandasJoin).where(cb.equal(heladeraRoot.get("id"), heladeraId));

            viandas = entityManager.createQuery(cq).getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        }
        return viandas;
    }

    public void eliminarHeladeras() {

        entityManager.getTransaction().begin();
        try {
            int deletedCount = entityManager.createQuery("DELETE FROM Heladera ").executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public void eliminarViandas() {
        entityManager.getTransaction().begin();
        try {
            int deletedCount = entityManager.createQuery("DELETE FROM Vianda ").executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public void eliminarTemperaturas() {
        entityManager.getTransaction().begin();
        try {
            int deletedCount = entityManager.createQuery("DELETE FROM Temperatura ").executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public void guardarSensor(SensorMovimiento sensor) {
        EntityTransaction t = entityManager.getTransaction();
        try {
            t.begin();
            entityManager.persist(sensor);
            entityManager.merge(sensor);
            t.commit();
        } catch (Exception e) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }

    public void guardarSensorTemperatura(SensorTemperatura sensor) {
        EntityTransaction t = entityManager.getTransaction();
        try {
            t.begin();
            entityManager.persist(sensor);
            entityManager.merge(sensor);
            t.commit();
        } catch (Exception e) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }

    public void guardarSensorConexion(SensorConexion sensor) {
        EntityTransaction t = entityManager.getTransaction();
        try {
            t.begin();
            entityManager.persist(sensor);
            entityManager.merge(sensor);
            t.commit();
        } catch (Exception e) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }

    public void actualizarSensorConexion(SensorConexion sensor) {
        EntityTransaction t = entityManager.getTransaction();
        try {
            t.begin();
            entityManager.merge(sensor);
            t.commit();
        } catch (Exception e) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }

    public void actualizarSensorTemperatura(SensorTemperatura sensor) {
        EntityTransaction t = entityManager.getTransaction();
        try {
            t.begin();
            entityManager.merge(sensor);
            t.commit();
        } catch (Exception e) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }

    public void actualizarSensor(SensorMovimiento sensorMovimiento) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(sensorMovimiento);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void persistirSubscriptor(Subscriptor sub) {
        EntityTransaction t = entityManager.getTransaction();
        try {
            t.begin();
            entityManager.persist(sub);
            entityManager.merge(sub);
            t.commit();
        } catch (Exception e) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
            e.printStackTrace();
        }

    }

    public void actualizarSubscriptor(Subscriptor sub) {
        EntityTransaction t = entityManager.getTransaction();
        try {
            t.begin();
            entityManager.merge(sub);
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            throw e;
        }
    }

    public List<Subscriptor> findAllSubscriptores() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Subscriptor> criteriaQuery = criteriaBuilder.createQuery(Subscriptor.class);
        Root<Subscriptor> subscriptorRoot = criteriaQuery.from(Subscriptor.class);
        criteriaQuery.select(subscriptorRoot);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public void persistirRetiro(Retiro r) {
        EntityTransaction t = entityManager.getTransaction();
        try {
            t.begin();
            entityManager.persist(r);
            entityManager.merge(r);
            t.commit();
        } catch (Exception e) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
            e.printStackTrace();
        }

    }
}



