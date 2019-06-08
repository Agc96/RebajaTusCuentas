package pe.edu.pucp.a20190000.rebajatuscuentas.features.login.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.net.UnknownHostException;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.api.ApiAdapter;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.api.in.LoginInRO;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.api.out.UserOutRO;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.login.view.ILoginView;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements ILoginPresenter {

    private final static String TAG = "RTC_LOGIN_PRESENTER";
    private ILoginView view;

    public LoginPresenter(ILoginView view) {
        this.view = view;
    }

    public void loginRest(final String username, final String password) {
        LoginInRO loginInRO = new LoginInRO(ApiAdapter.APPLICATION_NAME, username, password);
        Call<UserOutRO> call = ApiAdapter.getInstance().login(loginInRO);
        call.enqueue(new Callback<UserOutRO>() {
            @Override
            public void onResponse(@NonNull Call<UserOutRO> call, @NonNull Response<UserOutRO> response) {
                processUserResponse(response, username, password);
            }
            @Override
            public void onFailure(@NonNull Call<UserOutRO> call, @NonNull Throwable t) {
                if (t instanceof UnknownHostException) {
                    // No se encontró la URL, preguntar si se desea iniciar sesión sin conexión
                    view.askForLoginOffline();
                } else {
                    // Mostrar mensaje de error en el logcat y en un cuadro de diálogo
                    t.printStackTrace();
                    view.showErrorDialog(t.getMessage());
                }
            }
        });
    }

    public boolean verifyLoginData(String username, String password) {
        if (username == null || username.isEmpty()) {
            Utilities.showMessage(view.getContext(), R.string.login_msg_username_empty);
            return false;
        }
        if (password == null || password.isEmpty()) {
            Utilities.showMessage(view.getContext(), R.string.login_msg_password_empty);
            return false;
        }
        return true;
    }

    private void processUserResponse(Response<UserOutRO> response, String username, String password) {
        // Verificar respuesta del servidor REST
        Pair<UserOutRO, String> result = validateResponse(response);
        if (result.first == null) {
            // Mostrar mensaje de error
            view.showErrorDialog(result.second);
        } else {
            // Obtener el objeto JSON
            UserOutRO userOutRO = result.first;
            // Guardar los datos del usuario en la base de datos
            new UserSaveTask(view, username, password, userOutRO).execute();
            // Ir a la pantalla de bienvenida
            view.goToHomePage(userOutRO.getFullName(), userOutRO.getEmail());
        }
    }

    private Pair<UserOutRO, String> validateResponse(Response<UserOutRO> response) {
        Context context = view.getContext();
        // Verificar que la respuesta es satisfactoria
        if (!response.isSuccessful()) {
            String message = Utilities.formatString(context, R.string.api_dlg_error_msg_http,
                    response.code());
            return new Pair<>(null, message);
        }
        // Verificar el contenido de la respuesta en JSON
        UserOutRO userOutRO = response.body();
        if (userOutRO == null) {
            String message = Utilities.formatString(context, R.string.api_dlg_error_msg_empty);
            return new Pair<>(null, message);
        }
        // Verificar que la respuesta no indique un error
        int errorCode = userOutRO.getErrorCode();
        String message = userOutRO.getMessage();
        if (errorCode == 0) {
            return new Pair<>(userOutRO, message); // Respuesta sin errores
        }
        // Verificar que el mensaje de error no está vacío
        if (message == null || message.isEmpty()) {
            message = Utilities.formatString(context, R.string.api_dlg_error_msg_rest, errorCode);
        }
        return new Pair<>(null, message);
    }

    @Override
    public void loginOffline(String username, String password) {
        if (verifyLoginData(username, password)) {
            new UserLoginTask(view, username, password).execute();
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
