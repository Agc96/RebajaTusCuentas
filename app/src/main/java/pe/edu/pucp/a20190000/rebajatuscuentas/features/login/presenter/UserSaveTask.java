package pe.edu.pucp.a20190000.rebajatuscuentas.features.login.presenter;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import de.rtner.security.auth.spi.SimplePBKDF2;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.api.out.UserOutRO;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.AppDatabase;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.User;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.login.view.ILoginView;

public class UserSaveTask extends AsyncTask<Void, Void, Boolean> {

    private final static String TAG = "RTC_LOGIN_SAVETASK";
    private WeakReference<ILoginView> view;
    private UserOutRO userOutRO;
    private String username;
    private String password;

    protected UserSaveTask(ILoginView view, String username, String password, UserOutRO userOutRO) {
        this.view = new WeakReference<>(view);
        this.username = username;
        this.password = password;
        this.userOutRO = userOutRO;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        // Verificar que la vista todavía está disponible
        ILoginView view = this.view.get();
        if (view == null) return false;
        // Inicializar la base de datos, si es que aún no se hizo
        AppDatabase database = AppDatabase.getInstance(view.getContext());
        if (database == null) {
            Log.d(TAG, "La base de datos no se inicializó, ¿quizás terminó el Activity?");
            return false;
        }

        // Verificar si no se ha guardado con anterioridad
        int userId = userOutRO.getUserId();
        if (database.userDao().findById(userId) == null) {
            try {
                // Generar el hash de la contraseña
                String hash = new SimplePBKDF2().deriveKeyFormatted(password);
                // Guardar los datos del usuario en la base de datos
                User user = new User(userId, userOutRO.getFullName(), userOutRO.getEmail(), username, hash);
                database.userDao().insert(user);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }
}
