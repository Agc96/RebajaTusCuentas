package pe.edu.pucp.a20190000.rebajatuscuentas.utils;

public final class Constants {
    // Llaves para el envío de pares llave-valor entre componentes de Android (usando Bundles)
    public final static String EXTRA_USER_FULLNAME = "RTC_EXTRA_USER_FULLNAME";
    public final static String EXTRA_USER_EMAIL = "RTC_EXTRA_USER_EMAIL";
    public final static String EXTRA_LOCATION_ACTIVE = "RTC_EXTRA_LOCATION_ACTIVE";
    public final static String EXTRA_LOCATION_DATA = "RTC_EXTRA_LOCATION_DATA";

    // Códigos de petición para la interacción entre componentes de Android
    public static final int REQ_CODE_GPS_PERMISSIONS = 1;
    public static final int REQ_CODE_GPS_ACTIVATE = 2;
    public static final int REQ_CODE_GPS_GEOCODING = 3; // TODO
    public static final int REQ_CODE_CAMERA_PERMISSIONS = 11;
    public static final int REQ_CODE_CAMERA_RESULT = 12;
}
