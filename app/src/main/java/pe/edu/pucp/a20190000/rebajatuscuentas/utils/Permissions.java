package pe.edu.pucp.a20190000.rebajatuscuentas.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class Permissions {

    private final static String TAG = "RTC_PERMISSIONS";

    /**
     * Verifica desde un Activity si los permisos de la aplicación brindados han sido aceptados.
     * Si falta algún permiso por ser aceptado, se le solicitará al usuario permitir manualmente
     * los permisos de la aplicación que sean necesarios.
     * @param activity Activity de la aplicación.
     * @param requestCode Código de petición para la interacción entre componentes de Android.
     *                    Ver {@link Constants}.
     * @param permissions Arreglo de permisos de la aplicación.
     * @return Verdadero si todos los permisos solicitados fueron aceptados, Falso de otro modo.
     */
    public static boolean checkFromActivity(Activity activity, int requestCode, String[] permissions) {
        if (Utilities.isEmpty(permissions)) {
            Log.d(TAG, "No se solicitó ningún permiso.");
            return false;
        }
        if (hasAllPermissions(activity, permissions)) {
            Log.d(TAG, "Todos los permisos fueron aceptados.");
            return true;
        } else {
            Log.d(TAG, "Faltan permisos, iniciando petición para ser aceptados...");
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
            return false;
        }
    }

    /**
     * Verifica desde un Fragment si los permisos de la aplicación brindados han sido aceptados.
     * Si falta algún permiso por ser aceptado, se le solicitará al usuario permitir manualmente
     * los permisos de la aplicación que sean necesarios.
     * @param fragment Fragment de la aplicación.
     * @param requestCode Código de petición para la interacción entre componentes de Android.
     *                    Ver {@link Constants}.
     * @param permissions Arreglo de permisos de la aplicación.
     * @return Verdadero si todos los permisos solicitados fueron aceptados, Falso de otro modo.
     */
    public static boolean checkFromFragment(Fragment fragment, int requestCode, String[] permissions) {
        // Verificar arreglo de permisos
        if (Utilities.isEmpty(permissions)) {
            Log.d(TAG, "No se solicitó ningún permiso.");
            return false;
        }
        // Verificar si falta algún permiso
        if (hasAllPermissions(fragment.getContext(), permissions)) {
            Log.d(TAG, "Todos los permisos fueron aceptados.");
            return true;
        } else {
            Log.d(TAG, "Faltan permisos, iniciando petición para ser aceptados...");
            fragment.requestPermissions(permissions, requestCode);
            return false;
        }
    }

    /**
     * Determina dentro de una lista de permisos si es que alguno aún no ha sido aprobado.
     * @param context Contexto de la aplicación, generalmente un Activity.
     * @param permissions Lista de permisos que se desean solicitar.
     * @return Verdadero si todos los permisos fueron aprobados, Falso de otro modo.
     */
    public static boolean hasAllPermissions(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (!hasPermission(context, permission)) return false;
        }
        return true;
    }

    /**
     * Determina si un permiso de Android ha sido aprobado por el usuario.
     * @param context Contexto de la aplicación, generalmente un Activity.
     * @param permission El permiso que se desea solicitar. Ver {@link android.Manifest.permission}.
     * @return Si ha sido aprobado o no el permiso solicitado.
     */
    public static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkFromResults(String[] permissions, int[] grantResults) {
        if (Utilities.isEmpty(permissions) || Utilities.isEmpty(grantResults)) {
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
