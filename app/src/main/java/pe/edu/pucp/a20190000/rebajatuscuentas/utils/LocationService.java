package pe.edu.pucp.a20190000.rebajatuscuentas.utils;

import android.app.Activity;
import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class LocationService {
    private final static String TAG = "RTC_INM_CREATE_LOC_SRV";
    private final static int LOCATION_INTERVAL = 10000; // milisegundos (= 10 segundos)
    private final static int LOCATION_FAST_INTERVAL = 5000; // milisegundos (= 5 segundos)

    private OnUpdateLocationListener mListener;
    private FusedLocationProviderClient mLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mLastLocation;
    private boolean mActive;

    public LocationService(OnUpdateLocationListener listener, boolean active, Location lastLocation) {
        mListener = listener;
        mActive = active;
        mLastLocation = lastLocation;
        // Obtener clientes para el uso de servicios de ubicación
        mLocationClient = LocationServices.getFusedLocationProviderClient(listener.getActivity());
        mSettingsClient = LocationServices.getSettingsClient(listener.getActivity());
        // Definir los pasos que se realizarán al obtener la ubicación
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // Obtener la ubicación actual
                Log.d(TAG, "Obtenida información del dispositivo GPS.");
                mLastLocation = locationResult.getLastLocation();
                if (mListener != null) {
                    mListener.onUpdateLocation(mActive, mLastLocation);
                } else {
                    Log.d(TAG, "Listener es NULL, se ha terminado el Activity.");
                }
            }
        };
        // Crear la petición para la obtención de la ubicación actual
        mLocationRequest = new LocationRequest()
                .setInterval(LOCATION_INTERVAL)
                .setFastestInterval(LOCATION_FAST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Crear la petición para activar el sensor de ubicación (GPS)
        mSettingsRequest = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest)
                .build();
    }

    /**
     * Solicita al usuario activar el servicio de ubicación (GPS) en su dispositivo, si este acepta,
     * se obtendrán periódicamente los datos de geolocalización.
     */
    public void startLocationUpdates() {
        mActive = true;
        mSettingsClient.checkLocationSettings(mSettingsRequest)
                .addOnSuccessListener(mListener.getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.d(TAG, "El dispositivo GPS está activo, podemos usarlo.");
                        try {
                            mLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                                    null);
                            mListener.onUpdateLocation(mActive, mLastLocation);
                        } catch (SecurityException ex) {
                            // No debería pasar. Recordar que este método debe ser llamado después de
                            // haber aprobado los permisos necesarios para usar el dispositivo GPS.
                            Log.e(TAG, "No se tienen los permisos necesarios para usar el GPS.");
                            mActive = false;
                        }
                    }
                })
                .addOnFailureListener(mListener.getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ResolvableApiException) {
                            // Mostrar un cuadro de diálogo personalizado para que el usuario
                            // confirme si desea activar el dispositivo GPS en su smartphone.
                            try {
                                ResolvableApiException rae = (ResolvableApiException) e;
                                rae.startResolutionForResult(mListener.getActivity(),
                                        Constants.REQ_CODE_GPS_ACTIVATE);
                            } catch (IntentSender.SendIntentException sie) {
                                // Ignoramos este error.
                            }
                        }
                        mActive = false;
                    }
                });
    }

    /**
     * Removemos las peticiones para obtener los datos de geolocalización. Es buena práctica el
     * remover dichas peticiones mientras el Activity está en los estados Paused o Stopped, para
     * evitar gastar la batería del dispositivo.
     */
    public void stopLocationUpdates() {
        if (!mActive) {
            Log.d(TAG, "Se intentó parar la geolocalización pero el servicio no estaba activo.");
            return;
        }
        mLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(mListener.getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mActive = false;
                        if (mListener != null) {
                            mListener.onUpdateLocation(mActive, mLastLocation);
                        } else {
                            Log.d(TAG, "Listener = NULL, se ha terminado el Activity.");
                        }
                    }
                });
    }

    /**
     * Determina si el servicio está activo o no.
     */
    public boolean isActive() {
        return mActive;
    }

    /**
     * Obtiene la última ubicación registrada.
     */
    public Location getLastLocation() {
        return mLastLocation;
    }

    /**
     * Limpia la referencia al Listener (generalmente un Activity).
     */
    public void onDestroy() {
        mListener = null;
    }

    /**
     * Interfaz para la comunicación entre el listener (generalmente un Activity) y el servicio.
     */
    public interface OnUpdateLocationListener {
        Activity getActivity();
        void onUpdateLocation(boolean active, Location location);
    }
}
