package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Permissions;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class InmovableCreateLocationFragment extends Fragment {

    private static final String TAG = "RTC_INM_CREATE_LOC_FRG";
    private static final int REQ_CODE_PERMISSIONS = 1000;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragment
        View view = inflater.inflate(R.layout.fragment_inmovable_create_location, container, false);
        // Configurar el botón
        Button geolocationButton = view.findViewById(R.id.inm_create_loc_btn_geolocation);
        geolocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestGeolocation();
            }
        });
        return view;
    }

    private void requestGeolocation() {
        Activity activity = getActivity();
        if (activity == null) return;
        // Verificar si contamos con GPS
        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (lm == null) {
            Utilities.showMessage(activity, R.string.gps_msg_unavailable);
            return;
        }
        // Solicitar permisos a la aplicación para iniciar el servicio de geolocalización
        if (Permissions.request(activity, REQ_CODE_PERMISSIONS, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            startGeolocationService();
        }
    }

    public void startGeolocationService() {
        Log.d(TAG, "Activando GPS...");
        // TODO
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult se va al fragment.");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
