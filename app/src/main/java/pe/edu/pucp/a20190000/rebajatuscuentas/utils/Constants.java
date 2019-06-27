package pe.edu.pucp.a20190000.rebajatuscuentas.utils;

public final class Constants {
    // Llaves para el envío de pares llave-valor entre componentes de Android (usando Bundles)
    public final static String EXTRA_USER_FULLNAME = "RTC_EXTRA_USER_FULLNAME";
    public final static String EXTRA_USER_EMAIL = "RTC_EXTRA_USER_EMAIL";
    public final static String EXTRA_INMOVABLE_LOCATION_ACTIVE = "RTC_EXTRA_INMOVABLE_LOCATION_ACTIVE";
    public final static String EXTRA_INMOVABLE_LOCATION_DATA = "RTC_EXTRA_INMOVABLE_LOCATION_DATA";
    public final static String EXTRA_INMOVABLE_DATA = "RTC_EXTRA_INMOVABLE_DATA";
    public final static String EXTRA_INMOVABLE_PHOTO = "RTC_EXTRA_INMOVABLE_PHOTO";

    // Códigos de petición para la interacción entre componentes de Android
    public static final int REQ_CODE_GPS_PERMISSIONS = 1001;
    public static final int REQ_CODE_GPS_ACTIVATE = 1002;
    public static final int REQ_CODE_GPS_GEOCODING = 1003; // TODO
    public static final int REQ_CODE_CAMERA_PERMISSIONS = 1011;
    public static final int REQ_CODE_CAMERA_INTENT = 1012;

    // FileProvider para esta aplicación
    public static final String FILE_PROVIDER = "pe.edu.pucp.a20190000.rebajatuscuentas.fileprovider";
}
