package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.clients.ColaboradoresProxy;
import ar.edu.utn.dds.k3003.clients.HeladerasProxy;
import ar.edu.utn.dds.k3003.facades.FachadaColaboradores;
import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.*;
import ar.edu.utn.dds.k3003.model.Incidente;
import ar.edu.utn.dds.k3003.model.dtos.IncidenteDTO;
import ar.edu.utn.dds.k3003.model.enums.EstadoIncidenteEnum;
import ar.edu.utn.dds.k3003.repositories.*;
import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import ar.edu.utn.dds.k3003.clients.ViandasRetrofitClient;
import lombok.Setter;
import org.mockito.internal.matchers.Null;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import io.javalin.micrometer.MicrometerPlugin;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmHeapPressureMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.prometheusmetrics.PrometheusConfig;

public class Fachada {

    public IncidenteRepository incidenteRepository;
    public IncidenteMapper incidenteMapper;

    private FachadaViandas fachadaViandas;

    private HeladerasProxy fachadaHeladeras;
    private ColaboradoresProxy fachadaColaboradores;

    private Counter incidentesCreadosCounter;
    private Counter incidentesResueltosCounter;
    private Counter incidentesConsultadosCounter;

    public Fachada() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("postgres");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        this.incidenteRepository = new IncidenteRepository(entityManager);
        this.incidenteMapper = new IncidenteMapper();
    }

    public IncidenteDTO crearIncidente(IncidenteDTO incidenteDTO) throws IllegalArgumentException {

        if(incidenteDTO.getHeladeraId() == null){
            throw new IllegalArgumentException("El id de la heladera no puede ser nulo");
        }

        if(incidenteDTO.getTipoIncidente() == null){
            throw new IllegalArgumentException("El tipo de incidente no puede ser nulo");
        }

        // Creo un nuevo incidente con los datos recibidos
        Incidente incidente = new Incidente(
                EstadoIncidenteEnum.ACTIVO,
                incidenteDTO.getTipoIncidente(),
                incidenteDTO.getHeladeraId(),
                incidenteDTO.isExcedeTemperatura(),
                incidenteDTO.getExcesoTemperatura(),
                incidenteDTO.getTiempoSinRespuesta()
        );

         this.fachadaHeladeras.modificarEstadoHeladera(incidenteDTO);

         this.fachadaColaboradores.reportarFalla(incidenteDTO);

        // Guardo el incidente en la base de datos
        Incidente incidenteGuardado = this.incidenteRepository.save(incidente);

        // Incrementar el contador de incidentes creados
        if (incidentesCreadosCounter != null) {
            incidentesCreadosCounter.increment();
        }

        // Devuelvo un DTO con la información del incidente guardado
        return incidenteMapper.map(incidenteGuardado);
    }

    public IncidenteDTO buscarIncidente(Long idIncidente) throws NoSuchElementException {
        Incidente incidente = this.incidenteRepository.findById(idIncidente);
        IncidenteDTO incidenteDTO= new IncidenteDTO(incidente.getTipoIncidente(), incidente.getHeladeraId(), incidente.getEstadoIncidente(), incidente.isExcedeTemperatura(), incidente.getExcesoTemperatura(), incidente.getTiempoSinRespuesta());
        incidenteDTO.setId(incidente.getId());

        // Incrementar el contador de incidentes consultados
        if (incidentesConsultadosCounter != null) {
            incidentesConsultadosCounter.increment();
        }

        return incidenteDTO;
    }

    public void setHeladerasProxy(HeladerasProxy fachadaHeladeras) {
        this.fachadaHeladeras = fachadaHeladeras;
    }

    public void setViandasProxy(FachadaViandas fachadaViandas) {
        this.fachadaViandas = fachadaViandas;
    }

    public void setColaboradoresProxy(ColaboradoresProxy fachadaColaboradores) {
        this.fachadaColaboradores = fachadaColaboradores;
    }

    public void setRegistry(PrometheusMeterRegistry registry) {

        this.incidentesCreadosCounter = Counter.builder("app.incidentes.creados")
                .description("Numero de incidentes creados")
                .register(registry);
        this.incidentesResueltosCounter = Counter.builder("app.incidentes.resueltos")
                .description("Numero de incidentes resueltos")
                .register(registry);
        this.incidentesConsultadosCounter = Counter.builder("app.incidentes.consultados")
                .description("Numero de incidentes consultados")
                .register(registry);
    }

    public IncidenteDTO actualizarIncidente(Long idIncidente, EstadoIncidenteEnum estado) throws NoSuchElementException {
        Incidente incidente = this.incidenteRepository.findById(idIncidente);

        this.incidenteRepository.modificarEstado(idIncidente,estado);

        // Si el estado es resuelto, incrementar el contador de incidentes resueltos
        if (estado == EstadoIncidenteEnum.RESUELTO && incidentesResueltosCounter != null) {
            incidentesResueltosCounter.increment();
        }

        return incidenteMapper.map(incidente);
    }

    public List<IncidenteDTO> listarIncidentesPorHeladera(Long heladeraId) {
        List<Incidente> incidentes = this.incidenteRepository.findByHeladeraId(heladeraId);
        return incidentes.stream()
                .map(incidenteMapper::map)
                .collect(Collectors.toList());
    }

}
