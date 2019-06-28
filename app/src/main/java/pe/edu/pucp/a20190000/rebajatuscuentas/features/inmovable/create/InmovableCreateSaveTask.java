package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.os.AsyncTask;
import android.util.Log;

import androidx.core.util.Pair;

import java.lang.ref.WeakReference;
import java.util.Locale;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.AppDatabase;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.Inmovable;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Constants;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Image;

public class InmovableCreateSaveTask extends AsyncTask<Void, Void, Pair<Boolean, Integer>> {
    private final static String TAG = "INM_CREATE_SAVE_TASK";
    private WeakReference<IInmovableCreateView> mView;
    private Inmovable mInmovable;
    private String mPhotoPath;
    private boolean mSavePhoto;

    public InmovableCreateSaveTask(IInmovableCreateView view, Inmovable inmovable, String photoPath,
                                   boolean savePhoto) {
        this.mView = new WeakReference<>(view);
        this.mInmovable = inmovable;
        this.mPhotoPath = photoPath;
        this.mSavePhoto = savePhoto;
    }

    @Override
    protected Pair<Boolean, Integer> doInBackground(Void... voids) {
        // Verificar que la vista todavía está disponible
        IInmovableCreateView view = this.mView.get();
        if (view == null) return null;
        // Inicializar la base de datos, si es que aún no se hizo
        AppDatabase database = AppDatabase.getInstance(view.getContext());
        if (database == null) {
            Log.d(TAG, "La base de datos no se inicializó, ¿quizás terminó el Activity?");
            return new Pair<>(false, R.string.inm_create_msg_failure);
        }
        // Guardar los datos del inmueble y verificar que se guardaron exitosamente.
        long rowId = database.inmovableDao().insert(mInmovable);
        if (rowId <= 0) {
            return new Pair<>(false, R.string.inm_create_msg_database);
        }
        // Ver si vamos a guardar la imagen del inmueble
        if (mPhotoPath != null) {
            if (mSavePhoto) {
                // Mover la foto del inmueble al almacenamiento externo
                String filename = String.format(Locale.getDefault(), Constants.IMAGE_INMOVABLE_FORMAT, rowId);
                if (!Image.moveToExternalStorage(mPhotoPath, filename)) {
                    return new Pair<>(true, R.string.inm_create_msg_no_photo);
                }
            } else {
                // Borrar la foto temporal tomada con la cámara
                Image.delete(mPhotoPath);
            }
        }
        // Devolver un mensaje de confirmación
        return new Pair<>(true, R.string.inm_create_msg_saved);
    }

    @Override
    protected void onPostExecute(Pair<Boolean, Integer> result) {
        // Verificar que la vista todavía está disponible
        IInmovableCreateView view = this.mView.get();
        if (view != null) {
            // Obtener el resultado del AsyncTask
            boolean saved = false;
            if (result != null && result.first != null) {
                saved = result.first;
            }
            int messageId = R.string.inm_create_msg_failure;
            if (result != null && result.second != null) {
                messageId = result.second;
            }
            // Realizar acciones dependiendo del resultado
            view.showInmovableSaveResult(saved, messageId);
        }
    }
}
