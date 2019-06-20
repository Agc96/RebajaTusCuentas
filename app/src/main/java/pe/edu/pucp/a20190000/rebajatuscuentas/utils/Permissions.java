package pe.edu.pucp.a20190000.rebajatuscuentas.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;

public class Permissions {

    private final static String TAG = "RTC_PERMISSIONS";

    public static boolean checkFromActivity(Activity activity, int requestCode, String[] permissions) {
        if (Utilities.isEmptyArray(permissions)) {
            Log.d(TAG, "No se solicitó ningún permsio.");
            return false;
        }
        for (String permission : permissions) {
            if (isPermissionMissing(activity, permission)) {
                Log.d(TAG, "Faltan permisos, iniciando petición para ser aceptados...");
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                return false;
            }
        }
        Log.d(TAG, "Todos los permisos fueron aceptados.");
        return true;
    }

    public static boolean checkFromFragment(Fragment fragment, int requestCode, String[] permissions) {
        if (Utilities.isEmptyArray(permissions)) {
            Log.d(TAG, "No se solicitó ningún permsio.");
            return false;
        }
        for (String permission : permissions) {
            if (isPermissionMissing(fragment.getContext(), permission)) {
                Log.d(TAG, "Faltan permisos, iniciando petición para ser aceptados...");
                fragment.requestPermissions(permissions, requestCode);
                return false;
            }
        }
        Log.d(TAG, "Todos los permisos fueron aceptados.");
        return true;
    }

    private static boolean isPermissionMissing(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkFromResults(String[] permissions, int[] grantResults) {
        if (Utilities.isEmptyArray(permissions) || Utilities.isEmptyArray(grantResults)) {
            Log.d(TAG, "El usuario canceló la petición de permisos.");
            return false;
        }
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Hubo por lo menos un permiso que no se aceptó.");
                return false;
            }
        }
        Log.d(TAG, "Todos los permisos fueron aceptados.");
        return true;
    }
}
