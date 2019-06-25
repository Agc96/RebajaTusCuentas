package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class InmovableCreatePhotoFragment extends Fragment {
    private final static String TAG = "RTC_INM_NEW_PHOTO_FRG";
    private IInmovableCreateView mView;
    private ImageView mPhotoView;
    private Bitmap mPhoto;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

    public void initializeComponents(Bundle savedInstanceState) {
        // Configurar el botón de añadir una foto
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        // Configurar el botón de quitar una foto
        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePhoto();
            }
        });
        // Configurar la visibilidad del botón de remover
    }

    private void takePhoto() {
        // Verificar que el dispositivo móvil cuenta con cámara
        PackageManager manager = mView.getContext().getPackageManager();
        if (!manager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Utilities.showMessage(mView.getContext(), R.string.camera_msg_unavailable);
            return;
        }
        // Solicitar a la cámara del celular que tome una foto
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(manager) != null) {
            startActivityForResult(takePictureIntent, Constants.REQ_CODE_CAMERA_RESULT);
        }
    }

    private void removePhoto() {
        // Borra la imagen del ImageView y del Fragment
        mPhotoView.setImageBitmap(null);
        mPhoto = null;
        // Desactiva el botón de remover foto
        mRemoveButton.setEnabled(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQ_CODE_CAMERA_RESULT) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    processImage(data);
                    break;
                case Activity.RESULT_CANCELED:
                    Log.d(TAG, "El usuario canceló la toma de fotos.");
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void processImage(Intent data) {
        // Obtener la imagen y colocarla en el ImageView
        Bundle extras = data.getExtras();
        if (extras != null) {
            mPhoto = Image.rotateIfNeeded((Bitmap) extras.get("data"), data.getData());
            mPhotoView.setImageBitmap(mPhoto);
        }
        // TODO: TEST
        File filesDir = mView.getContext().getFilesDir();
        Log.d(TAG, filesDir.toString());
        File externalFilesDir = mView.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.d(TAG, externalFilesDir == null ? "null" : externalFilesDir.toString());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mView = null;
    }
}
