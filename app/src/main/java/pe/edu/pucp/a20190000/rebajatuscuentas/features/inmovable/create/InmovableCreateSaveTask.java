package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.StringRes;

import java.lang.ref.WeakReference;
import java.util.Locale;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.AppDatabase;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.Inmovable;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Constants;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Image;

public class InmovableCreateSaveTask extends AsyncTask<Void, Void, Integer> {
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
    protected Integer doInBackground(Void... voids) {
        // Verificar que la vista todavía está disponible
        IInmovableCreateView view = this.mView.get();
        if (view == null) return null;
        // Inicializar la base de datos, si es que aún no se hizo
        AppDatabase database = AppDatabase.getInstance(view.getContext());
        if (database == null) {
            Log.d(TAG, "La base de datos no se inicializó, ¿quizás terminó el Activity?");
            return R.string.inm_create_msg_failure;
        }
        // Guardar los datos del inmueble y verificar que se guardaron exitosamente.
        long id = database.inmovableDao().insert(mInmovable);
        if (id <= 0) {
            return R.string.inm_create_msg_failure;
        }
        // Ver si vamos a guardar la imagen del inmueble, si no es así ya terminamos
        if (mPhotoPath == null) {
            return R.string.inm_create_msg_saved;
        }
        if (mSavePhoto) {
            // Mover la foto del inmueble al almacenamiento externo
            String filename = String.format(Locale.US, Constants.IMAGE_INMOVABLE_FORMAT, id);
            if (Image.moveToExternalStorage(mPhotoPath, filename)) {
                return R.string.inm_create_msg_saved_photo;
            } else {
                return R.string.inm_create_msg_saved_no_photo;
            }
        } else {
            // Borrar la foto temporal tomada con la cámara
            Image.delete(mPhotoPath);
            return R.string.inm_create_msg_saved;
        }
    }

    @Override
    protected void onPostExecute(@StringRes Integer messageId) {
        // Verificar que la vista todavía está disponible
        IInmovableCreateView view = this.mView.get();
        if (view != null) {
            // Realizar acciones dependiendo del resultado
            view.showInmovableSaveResult(messageId);
        }
    }
}
