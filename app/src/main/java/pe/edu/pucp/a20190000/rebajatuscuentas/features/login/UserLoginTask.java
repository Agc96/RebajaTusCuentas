package pe.edu.pucp.a20190000.rebajatuscuentas.features.login;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import de.rtner.security.auth.spi.SimplePBKDF2;
import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.AppDatabase;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.User;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class UserLoginTask extends AsyncTask<Void, Void, User> {

    private final static String TAG = "MFL_LOGIN_LOGINTASK";
    private WeakReference<ILoginView> view;
    private String username;
    private String password;

    protected UserLoginTask(ILoginView view, String username, String password) {
        this.view = new WeakReference<>(view);
        this.username = username;
        this.password = password;
    }

    @Override
    protected User doInBackground(Void... voids) {
        // Verificar que la vista todavía está disponible
        ILoginView view = this.view.get();
        if (view == null) return null;

        // Iniciar la base de datos, si es que aún no se hizo
        AppDatabase database = AppDatabase.getInstance(view.getContext());
        if (database == null) {
            Log.d(TAG, "La base de datos no se inicializó, ¿quizás terminó el Activity?");
            return null;
        }

        // Buscar el usuario con el nombre de usuario proporcionado
        User user = database.userDao().findByUsername(username);
        if (user == null) return null;
        // Evaluar la contraseña
        boolean isValid = new SimplePBKDF2().verifyKeyFormatted(user.getHash(), password);
        return isValid ? user : null;
    }

    @Override
    protected void onPostExecute(User user) {
        // Verificar que la vista todavía está disponible
        ILoginView view = this.view.get();
        if (view == null) return;

        // Verificar si el usuario fue encontrado
        if (user != null) {
            view.goToHomePage(user.getFullName(), user.getEmail());
        } else {
            String message = Utilities.formatString(view.getContext(), R.string.login_dlg_error_msg_not_found);
            view.showErrorDialog(message);
        }
    }

}
