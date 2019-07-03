package pe.edu.pucp.a20190000.rebajatuscuentas.utils;

public final class Constants {
    // Llaves para el envío de pares llave-valor entre componentes de Android (usando Bundles)
    public final static String EXTRA_USER_FULLNAME = "RTC_EXTRA_USER_FULLNAME";
    public final static String EXTRA_USER_EMAIL = "RTC_EXTRA_USER_EMAIL";
    public final static String EXTRA_INMOVABLE_LOCATION_ACTIVE = "RTC_EXTRA_INMOVABLE_LOCATION_ACTIVE";
    public final static String EXTRA_INMOVABLE_LOCATION_DATA = "RTC_EXTRA_INMOVABLE_LOCATION_DATA";
    public final static String EXTRA_INMOVABLE_DATA = "RTC_EXTRA_INMOVABLE_DATA";
    public final static String EXTRA_INMOVABLE_PHOTO_PATH = "RTC_EXTRA_INMOVABLE_PHOTO_PATH";
    public final static String EXTRA_INMOVABLE_PHOTO_DATA = "RTC_EXTRA_INMOVABLE_PHOTO_DATA";

    // Códigos de petición para la interacción entre componentes de Android
    public static final int REQ_CODE_GPS_PERMISSIONS = 1001;
    public static final int REQ_CODE_GPS_ACTIVATE = 1002;
    public static final int REQ_CODE_CAMERA_PERMISSIONS = 1003;
    public static final int REQ_CODE_CAMERA_INTENT = 1004;
    public static final int REQ_CODE_SAVE_PERMISSIONS = 1005;

    // FileProvider para esta aplicación
    public static final String FILE_PROVIDER = "pe.edu.pucp.a20190000.rebajatuscuentas.fileprovider";

    // Formato de archivos para las fotos tomadas con la cámara
    public static final String IMAGE_DIRECTORY = "RebajaTusCuentas";
    public static final String IMAGE_INMOVABLE_FORMAT = "RTC_INMOVABLE_%d.jpg";
}
