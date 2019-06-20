# GPS
https://developer.android.com/training/location/change-location-settings.html
https://developer.android.com/training/location/retrieve-current
https://developer.android.com/training/location/receive-location-updates
https://developer.android.com/training/location/display-address
https://developer.android.com/reference/android/location/Location
https://developer.android.com/reference/android/location/Geocoder.html

https://stackoverflow.com/questions/1513485/how-do-i-get-the-current-gps-location-programmatically-in-android

## LA VIDA
https://github.com/googlesamples/android-play-location/tree/master/LocationUpdates

# Fragments
https://developer.android.com/training/basics/fragments/communicating.html

# TabLayout
https://developer.android.com/guide/navigation/navigation-swipe-view

# Snippets
```Java
package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.presenter;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class InmovableCreateLocationService extends IntentService {

    private final static String TAG = "RTC_INM_COMIDA";
    public static final int LOCATION_SUCCESS_RESULT = 0;
    public static final int LOCATION_FAILURE_RESULT = 1;
    public static final String LOCATION_DATA_EXTRA = "";

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) return;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String errorMessage = "";

        // Get the location passed to this service through an extra.
        Location location = intent.getParcelableExtra(LOCATION_DATA_EXTRA);

        // ...

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException ex) {
            Log.e(TAG, "Servicio no disponible, puede ser un error en la conexión a Internet", ex);
        } catch (IllegalArgumentException ex) {
            Log.e(TAG, String.format("Datos de geolocalización no válidos. Lat: %.2f, Long: %.2f.",
                    location.getLatitude(), location.getLongitude()), ex);
        }

        // Handle case where no address was found.
        if (Utilities.isEmptyList(addresses)) {
            Log.e(TAG, "No se encontraron direcciones cercanas.");
            deliverResultToReceiver(LOCATION_FAILURE_RESULT, errorMessage);
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.i(TAG, getString(R.string.address_found));
            deliverResultToReceiver(LOCATION_SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"),
                            addressFragments));
        }
    }
}
```

``` Java
Log.d(TAG, "Activando GPS...");
LocationRequest locationRequest = new LocationRequest()
        .setInterval(LOCATION_INTERVAL)
        .setFastestInterval(LOCATION_FAST_INTERVAL)
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest);

final Activity activity = getActivity();
if (activity != null) {
    SettingsClient client = LocationServices.getSettingsClient(activity);
    Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
    task.addOnSuccessListener(activity, new OnSuccessListener<LocationSettingsResponse>() {
        @Override
        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
        }
    });
    task.addOnFailureListener(activity, new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                }
            }
        }
    });
}
```
