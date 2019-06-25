package pe.edu.pucp.a20190000.rebajatuscuentas.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;

public final class Utilities {

    /**
     * Formatea una cadena de texto definida en el archivo XML "res/values/strings.xml".
     * @param context Contexto de la aplicación, generalmente un Activity.
     * @param stringId Identificador de la cadena de texto.
     * @param args Argumentos para la función {@link String#format(String, Object...)}.
     * @return Cadena de caracteres formateada según la cadena de texto definida en el archivo XML.
     */
    public static String formatString(Context context, @StringRes int stringId, Object... args) {
        return String.format(context.getString(stringId), args);
    }

    /**
     * Formatea un valor tipo moneda.
     * @param value Valor del double.
     * @return Cadena de caracteres formateada según el formato de moneda definido.
     */
    public static String formatMoney(double value) {
        return String.format(Locale.getDefault(), "US$ %.2f", value);
    }

    /**
     * Verifica si una cadena de caracteres está vacía.
     * @param string Cadena de caracteres.
     * @return Verdadero si la cadena es nula o su longitud es 0, Falso de otro modo.
     */
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Verifica que un arreglo de objetos está vacío.
     * @param array Arreglo de objetos (pueden ser de cualquier tipo no primitivo).
     * @return Verdadero si el arreglo es nulo o su longitud es 0, Falso de otro modo.
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * Verifica que un arreglo de enteros está vacío.
     * @param array Arreglo de enteros (primitivos "int", no objetos Integer).
     * @return Verdadero si el arreglo es nulo o su longitud es 0, Falso de otro modo.
     */
    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    /**
     * Verifica que una lista esté vacía.
     * @param list Una lista de objetos (pueden ser de cualquier tipo no primitivo).
     * @return Verdadero si la lista es nula o su longitud es 0, Falso de otro modo.
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Muestra un mensaje en forma de notificación Toast.
     * @param context Contexto de la aplicación, generalmente un Activity.
     * @param message Cadena de caracteres a mostrar.
     */
    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Muestra un mensaje en forma de notificación Toast.
     * Este método toma como mensaje el identificador de una cadena de texto definida en el archivo
     * XML "res/values/strings.xml", a diferencia de {@link #showMessage(Context, String)}.
     * @param context Contexto de la aplicación, generalmente un Activity.
     * @param stringId Identificador de la cadena de texto definida en el archivo XML.
     */
    public static void showMessage(Context context, @StringRes int stringId) {
        showMessage(context, context.getString(stringId));
    }

    /**
     * Esconde el teclado virtual de Android si es que está activo.
     * @param activity Activity al cual se le desea esconder su teclado virtual.
     */
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
