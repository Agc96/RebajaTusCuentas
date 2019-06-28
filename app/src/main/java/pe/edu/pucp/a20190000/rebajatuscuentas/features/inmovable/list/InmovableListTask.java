package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.list;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.AppDatabase;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.InmovableMainData;

public class InmovableListTask extends AsyncTask<Void, Void, List<InmovableMainData>> {
    private final static String TAG = "RTC_INM_LIST_TASK";
    private WeakReference<IInmovableListView> view;

    public InmovableListTask(IInmovableListView view) {
        this.view = new WeakReference<>(view);
    }

    @Override
    protected List<InmovableMainData> doInBackground(Void... voids) {
        // Verificar que la vista todavía está disponible
        IInmovableListView view = this.view.get();
        if (view == null) return null;
        // Inicializar la base de datos, si es que aún no se hizo
        AppDatabase database = AppDatabase.getInstance(view.getContext());
        if (database == null) {
            Log.d(TAG, "La base de datos no se inicializó, ¿quizás terminó el Activity?");
            return null;
        }
        return database.inmovableDao().listAllMainData();
    }

    @Override
    protected void onPostExecute(List<InmovableMainData> inmovables) {
        // Verificar que la vista todavía está disponible
        IInmovableListView view = this.view.get();
        if (view != null) {
            Log.d(TAG, "Mostrando lista de inmuebles desde base de datos...");
            view.initializeList(inmovables);
        }
    }
}
