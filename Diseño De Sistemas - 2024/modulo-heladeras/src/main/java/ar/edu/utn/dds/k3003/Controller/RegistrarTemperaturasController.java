package ar.edu.utn.dds.k3003.Controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.model.Temperatura;
import ar.edu.utn.dds.k3003.repositories.TemperaturaMapper;
import ar.edu.utn.dds.k3003.worker.MQUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

public class RegistrarTemperaturasController implements Handler {
    private Fachada fachada;
    private MQUtils mqutils;
    private TemperaturaMapper mapperTemperatura = new TemperaturaMapper();
    public RegistrarTemperaturasController(Fachada fachada, MQUtils mqutils) {
        this.fachada = fachada;
        this.mqutils= mqutils;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        try{
            Temperatura temperatura = context.bodyAsClass(Temperatura.class);
            mqutils.publish(context.body());
            //fachada.temperatura(mapperTemperatura.map(temperatura));
            context.status(HttpStatus.OK);
            context.result("Temperatura registrada correctamente");
        } catch (Exception e) {
            context.status(HttpStatus.BAD_REQUEST);
            context.result("Error de solicitud");
        }

    }
}
