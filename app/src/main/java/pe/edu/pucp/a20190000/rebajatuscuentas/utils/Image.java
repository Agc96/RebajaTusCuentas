package pe.edu.pucp.a20190000.rebajatuscuentas.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import androidx.exifinterface.media.ExifInterface;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Image {
    private static final String TAG = "RTC_UTIL_IMAGE";

    /**
     * Crea un archivo temporal en un directorio reservado para uso únicamente de la aplicación.
     * Esto nos permite guardar imágenes temporales sin que estos sobresalgan en la Galería de
     * usuario u otras aplicaciones borren dichos archivos (al menos en teoría).
     * @param context Contexto de la aplicación, generalmente un Activity
     * @return Un archivo temporal creado para guardar una foto, o NULL si es que no se pudo crear.
     */
    public static File create(Context context) {
        // Formulamos el formato del archivo (sin la extensión)
        SimpleDateFormat now = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String filename = "RTC_PHOTO_" + now.format(new Date());
        // Obtenemos un directorio reservado para uso de la aplicación
        File directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // Creamos una imagen temporal en el directorio especificado.
        try {
            File imageFile = File.createTempFile(filename, ".jpg", directory);
            imageFile.deleteOnExit();
            return imageFile;
        } catch (IOException ex) {
            Log.e(TAG, "Error al crear la imagen", ex);
            return null;
        }
    }

    /**
     * Rota la imagen obtenida con la captura de la cámara, si es necesario. Por ejemplo, varios
     * dispositivos móviles toman fotos de forma horizontal por defecto, y si se toman las fotos
     * de forma vertical, la imagen resultante puede estar rotada 90 grados.
     * @param path La ruta absoluta de la imagen obtenida con la cámara.
     * @return La imagen obtenida con la captura de la cámara, rotada a su posición correcta.
     */
    public static Bitmap rotateIfNeeded(String path) {
        Bitmap image = BitmapFactory.decodeFile(path);
        try {
            ExifInterface ei = new ExifInterface(path);
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

    /**
     * Rota una imagen unos ciertos grados sexagesimales.
     * @param image La imagen de origen
     * @param degree La cantidad de grados que se desea rotar.
     * @return Una nueva imagen, rotada la cantidad de grados especificada.
     */
    private static Bitmap rotate(Bitmap image, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImage = Bitmap.createBitmap(image, 0, 0, image.getWidth(),
                image.getHeight(), matrix, true);
        image.recycle();
        return rotatedImage;
    }

    /**
     * Mueve una imagen temporal a una carpeta permanente ubicada en el almacenamiento externo del
     * Smartphone.
     * @param sourcePath La ruta absoluta de la imagen de origen obtenida con la cámara.
     * @param destFilename El nombre del archivo que tendrá la imagen de destino.
     * @return Verdadero si se pudo transferir el archivo, Falso de otro modo.
     */
    public static boolean moveToExternalStorage(String sourcePath, String destFilename) {
        // Obtener la imagen de origen
        File sourceImage = new File(sourcePath);
        // Obtener directorio del almacenamiento externo del Smartphone
        File destDir = getExternalStroageFolder();
        if (destDir == null) return false;
        // Crear la imagen de destino
        File destImage = new File(destDir, destFilename);
        return sourceImage.renameTo(destImage);
    }

    private static File getExternalStroageFolder() {
        File parentDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (parentDir == null) {
            Log.d(TAG, "No se encontró la carpeta para el almacenamiento externo...");
            return null;
        }
        // Obtener (y crear) directorio para las imágenes de Rebaja tus cuentas
        File rtcDir = new File(parentDir, Constants.IMAGE_DIRECTORY);
        if (!rtcDir.exists() && !rtcDir.mkdir()) {
            Log.d(TAG, "No se pudo crear la carpeta para las imágenes de Rebaja tus Cuentas.");
            return null;
        }
        return rtcDir;
    }

    public static Bitmap loadFromExternalStorage(String filename) {
        // Obtener directorio del almacenamiento externo del Smartphone
        File destDir = getExternalStroageFolder();
        if (destDir == null) return null;
        File image = new File(destDir, filename);
        if (image.exists() && image.isFile()) {
            return rotateIfNeeded(image.getAbsolutePath());
        }
        return null;
    }

    public static void delete(String path) {
        boolean result = new File(path).delete();
        if (!result) {
            Log.w(TAG, "No se pudo borrar la imagen ubicada en " + path);
        }
    }
}
