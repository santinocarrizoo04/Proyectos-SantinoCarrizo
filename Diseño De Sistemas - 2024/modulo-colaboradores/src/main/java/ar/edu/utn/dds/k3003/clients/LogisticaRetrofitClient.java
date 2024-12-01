package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface LogisticaRetrofitClient {
    @GET("traslados/search/findByColaboradorId")
    Call<List<TrasladoDTO>> findByTraslado(@Query("id") Long id, @Query("anio") Integer anio, @Query("mes") Integer mes);

}
