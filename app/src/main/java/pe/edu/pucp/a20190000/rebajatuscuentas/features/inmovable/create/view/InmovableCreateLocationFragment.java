package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Permissions;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class InmovableCreateLocationFragment extends Fragment {

    private static final String TAG = "RTC_INM_CREATE_LOC_FRG";
    private static final int REQ_CODE_GPS_PERMISSIONS = 1000;
    private static final int LOCATION_INTERVAL = 10000;
    private static final int LOCATION_FAST_INTERVAL = 5000;

    private IInmovableCreateView mView;
    private FusedLocationProviderClient mFusedLocationClient;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IInmovableCreateView) {
            mView = (IInmovableCreateView) context;
        }
    }

    private void requestGeolocation() {
        Context context = mView.getContext();
        // Verificar si contamos con GPS
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (lm == null) {
            Utilities.showMessage(context, R.string.gps_msg_unavailable);
            return;
        }
        // Solicitar permisos a la aplicación para iniciar el servicio de geolocalización
        String[] permissions = new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        if (Permissions.checkFromFragment(this, REQ_CODE_GPS_PERMISSIONS, permissions)) {
            startGeolocationService();
        }
    }

    public void startGeolocationService() {
        mView.testResourceIds();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Verificar si se aceptaron los permisos para el uso de GPS
        if (requestCode == REQ_CODE_GPS_PERMISSIONS) {
            if (Permissions.checkFromResults(permissions, grantResults)) {
                startGeolocationService();
            } else {
                Utilities.showMessage(getContext(), R.string.gps_msg_permissions);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
