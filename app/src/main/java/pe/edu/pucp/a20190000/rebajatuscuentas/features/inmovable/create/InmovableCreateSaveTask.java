package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.AppDatabase;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.Inmovable;

public class InmovableCreateSaveTask extends AsyncTask<Void, Void, Boolean> {
    private final static String TAG = "INM_CREATE_SAVE_TASK";
    private WeakReference<IInmovableCreateView> mView;
    private Inmovable mInmovable;

    public InmovableCreateSaveTask(IInmovableCreateView view, Inmovable inmovable) {
        this.mView = new WeakReference<>(view);
        this.mInmovable = inmovable;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        // Verificar que la vista todavía está disponible
        IInmovableCreateView view = this.mView.get();
        if (view == null) return false;
        // Inicializar la base de datos, si es que aún no se hizo
        AppDatabase database = AppDatabase.getInstance(view.getContext());
        if (database == null) {
            Log.d(TAG, "La base de datos no se inicializó, ¿quizás terminó el Activity?");
            return false;
        }
        // Guardar los datos del inmueble y verificar que se guardaron exitosamente.
        long rowId = database.inmovableDao().insert(mInmovable);
        return rowId > 0;
    }

    @Override
    protected void onPostExecute(Boolean saved) {
        // Verificar que la vista todavía está disponible
        IInmovableCreateView view = this.mView.get();
        if (view != null) {
            // Realizar acciones dependiendo del resultado
            view.showInmovableSaveResult(saved);
        }
    }
}
