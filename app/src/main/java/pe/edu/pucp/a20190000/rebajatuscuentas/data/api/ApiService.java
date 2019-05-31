package pe.edu.pucp.a20190000.rebajatuscuentas.data.api;

import pe.edu.pucp.a20190000.rebajatuscuentas.data.api.in.LoginInRO;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.api.out.UserOutRO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("users/login")
    Call<UserOutRO> login(@Body LoginInRO user);

}
