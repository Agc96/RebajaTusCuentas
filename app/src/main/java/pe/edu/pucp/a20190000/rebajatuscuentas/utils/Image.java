package pe.edu.pucp.a20190000.rebajatuscuentas.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.media.ExifInterface;
import android.util.Log;

import java.io.IOException;

public class Image {
    private static final String TAG = "RTC_UTIL_IMAGE";

    public static Bitmap rotateIfNeeded(Bitmap image, Uri uri) {
        if (uri == null || uri.getPath() == null) {
            Log.d(TAG, "La ruta de la imagen está vacía.");
            return image;
        }
        try {
            ExifInterface ei = new ExifInterface(uri.getPath());
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
            return image;
        }
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
