package pe.edu.pucp.a20190000.rebajatuscuentas.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import androidx.exifinterface.media.ExifInterface;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Image {
    private static final String TAG = "RTC_UTIL_IMAGE";

    /**
     * Crea una imagen temporal en el directorio reservado para uso de la aplicación.
     * @param context Contexto de la aplicación, generalmente un Activity
     * @return Un archivo temporal creado para guardar una foto, o NULL si es que no se pudo crear.
     */
    public static File createImage(Context context) {
        // Formulamos el formato del archivo (sin la extensión)
        SimpleDateFormat now = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String filename = "RTC_PHOTO_" + now.format(new Date());
        // Obtenemos un directorio reservado para uso de la aplicación
        File directory = context.getFilesDir();
        // Creamos una imagen temporal en el directorio especificado.
        try {
            return File.createTempFile(filename, ".jpg", directory);
        } catch (IOException ex) {
            Log.e(TAG, "Error al crear la imagen", ex);
            return null;
        }
    }

    public static Bitmap rotateIfNeeded(Bitmap image, Uri uri, Context context) {
        try (InputStream in = context.getContentResolver().openInputStream(uri)) {
            if (in == null) {
                Log.e(TAG, String.format("No se pudo abrir el archivo %s", uri));
                return image;
            }
            ExifInterface ei = new ExifInterface(in);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotate(image, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotate(image, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotate(image, 270);
                default:
                    return image;
            }
        } catch (IOException ex) {
            Log.e(TAG, "Error al arreglar la imagen", ex);
        }
        return image;
    }

    private static Bitmap rotate(Bitmap image, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImage = Bitmap.createBitmap(image, 0, 0, image.getWidth(),
                image.getHeight(), matrix, true);
        image.recycle();
        return rotatedImage;
    }
}
