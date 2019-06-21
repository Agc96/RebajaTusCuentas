package pe.edu.pucp.a20190000.rebajatuscuentas.data.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiAdapter {
    private static ApiService INSTANCE;

    public static ApiService getInstance() {
        if (INSTANCE == null) {
            // Configurar el parsing de objetos JSON de Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
            // Crear la instancia de OkHttp, una librería que Retrofit usa para las peticiones HTTP
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
                    .build();
            // Crear la instancia de Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.API_BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                    .client(httpClient)
                    .build();
            // Crear la interfaz que manejará el servicio REST
            INSTANCE = retrofit.create(ApiService.class);
        }
        return INSTANCE;
    }
}
