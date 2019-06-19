package pe.edu.pucp.a20190000.rebajatuscuentas.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;

public class Permissions {

    private final static String TAG = "RTC_PERMISSIONS";

    public static boolean request(Activity activity, int requestCode, String... permissions) {
        // Obtener los permisos que aún no han sido aceptados
        ArrayList<String> missingPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (!isPermissionGranted(activity, permission)) {
                missingPermissions.add(permission);
            }
        }
        int missingCount = missingPermissions.size();
        // Caso 1: Faltan permisos por ser aceptados
        if (missingCount > 0) {
            Log.d(TAG, "Faltan permisos, iniciando petición para ser aceptados...");
            String[] missingArray = missingPermissions.toArray(new String[missingCount]);
            ActivityCompat.requestPermissions(activity, missingArray, requestCode);
            return false;
        }
        // Caso 2: Todos los permisos fueron aceptados
        Log.d(TAG, "Todos los permisos fueron aceptados.");
        return true;
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
