package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Constants;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Image;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Permissions;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class InmovableCreatePhotoFragment extends Fragment {
    private final static String TAG = "RTC_INM_NEW_PHOTO_FRG";
    private IInmovableCreateView mView;
    private ImageView mPhotoView;
    private Bitmap mPhoto;
    private String mPhotoPath;
    private Button mAddButton;
    private Button mRemoveButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IInmovableCreateView) {
            mView = (IInmovableCreateView) context;
        } else {
            throw new RuntimeException("El Activity debe implementar la interfaz IInmovableCreateView.");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout de este Fragment
        View view = inflater.inflate(R.layout.fragment_inmovable_create_photo, container, false);
        // Obtener los componentes
        mAddButton = view.findViewById(R.id.inm_create_photo_btn_add);
        mRemoveButton = view.findViewById(R.id.inm_create_photo_btn_remove);
        mPhotoView = view.findViewById(R.id.inm_create_photo_img_main);
        // Inicializar los componentes
        initializeComponents(savedInstanceState);
        return view;
    }

    private void initializeComponents(Bundle savedInstanceState) {
        // Configurar el botón de añadir una foto
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForTakePhoto();
            }
        });
        // Configurar el botón de quitar una foto
        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePhoto();
            }
        });
    }

    private void askForTakePhoto() {
        // Verificar que el dispositivo móvil cuenta con cámara
        PackageManager manager = mView.getContext().getPackageManager();
        if (!manager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Utilities.showMessage(mView.getContext(), R.string.feature_camera_msg_unavailable);
            return;
        }
        // Verificar que se tengan los permisos necesarios para tomar la foto
        if (Permissions.checkFromFragment(this, Constants.REQ_CODE_CAMERA_PERMISSIONS,
                Manifest.permission.CAMERA)) {
            takePhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Verificar si se aceptaron los permisos para el uso de la cámara y el almacenamiento externo
        if (requestCode == Constants.REQ_CODE_CAMERA_PERMISSIONS) {
            if (Permissions.checkFromResults(permissions, grantResults)) {
                takePhoto();
            } else {
                Utilities.showMessage(mView.getContext(), R.string.feature_camera_msg_denied);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void takePhoto() {
        if (mView == null || mView.getContext() == null) {
            Log.d(TAG, "El Activity ha terminado, ya no es necesario tomar la foto.");
            return;
        }
        // Solicitar a la cámara del celular que tome una foto
        PackageManager manager = mView.getContext().getPackageManager();
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(manager) != null) {
            // Si es que ya se había tomado una foto temporal, se elimina dicha foto
            if (mPhotoPath != null) {
                Image.delete(mPhotoPath);
            }
            // Crear un archivo privado para guardar la foto
            File photoFile = Image.create(mView.getContext());
            if (photoFile != null) {
                // Guardar datos de la foto para cuando se regrese del Intent
                mPhotoPath = photoFile.getAbsolutePath();
                Uri uri = FileProvider.getUriForFile(mView.getContext(), Constants.FILE_PROVIDER, photoFile);
                // Iniciar el Intent para el Activity que maneja la cámara
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(takePhotoIntent, Constants.REQ_CODE_CAMERA_INTENT);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQ_CODE_CAMERA_INTENT) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    processImage();
                    break;
                case Activity.RESULT_CANCELED:
                    Log.d(TAG, "El usuario canceló la toma de fotos.");
                    break;
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void processImage() {
        // Obtener la imagen y colocarla en el ImageView
        mPhoto = Image.rotateIfNeeded(mPhotoPath);
        mPhotoView.setImageBitmap(mPhoto);
        // Activar el botón de borrar la foto
        mRemoveButton.setEnabled(true);
    }

    private void removePhoto() {
        // Borra la imagen del ImageView y del Fragment
        mPhotoView.setImageBitmap(null);
        mPhoto = null;
        // Borra la imagen del directorio de la aplicación
        if (mPhotoPath != null) {
            Image.delete(mPhotoPath);
        }
        // Desactiva el botón de remover foto
        mRemoveButton.setEnabled(false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Persistimos la foto si es que esta no está vacía
        if (mPhoto != null) {
            outState.putString(Constants.EXTRA_INMOVABLE_PHOTO_PATH, mPhotoPath);
            outState.putParcelable(Constants.EXTRA_INMOVABLE_PHOTO_DATA, mPhoto);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            // Verificar si se han persistido los datos de la foto
            if (savedInstanceState.keySet().contains(Constants.EXTRA_INMOVABLE_PHOTO_DATA) &&
                    savedInstanceState.keySet().contains(Constants.EXTRA_INMOVABLE_PHOTO_PATH)) {
                // Recuperamos la foto y colocamos los datos correspondientes
                mPhoto = savedInstanceState.getParcelable(Constants.EXTRA_INMOVABLE_PHOTO_DATA);
                mPhotoPath = savedInstanceState.getString(Constants.EXTRA_INMOVABLE_PHOTO_PATH);
                mPhotoView.setImageBitmap(mPhoto);
                mRemoveButton.setEnabled(true);
            }
        }
    }

    public void setInmovablePhotoData() {
        mView.getPresenter().setInmovablePhoto(mPhotoPath);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mView = null;
    }
}
