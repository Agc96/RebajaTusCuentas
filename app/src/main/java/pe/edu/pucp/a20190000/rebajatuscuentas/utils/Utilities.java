package pe.edu.pucp.a20190000.rebajatuscuentas.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.List;

public final class Utilities {

    public static String formatString(Context context, @StringRes int stringId, Object... args) {
        return String.format(context.getString(stringId), args);
    }

    public static boolean isEmptyString(String string) {
        return (string == null) || (string.isEmpty());
    }
    public static boolean isEmptyArray(Object[] array) {
        return (array == null) || (array.length == 0);
    }
    public static boolean isEmptyArray(int[] array) {
        return (array == null) || (array.length == 0);
    }
    public static <T> boolean isEmptyList(List<T> list) {
        return (list == null) || (list.size() == 0);
    }

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showMessage(Context context, @StringRes int stringId) {
        showMessage(context, context.getString(stringId));
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
