package pe.edu.pucp.a20190000.rebajatuscuentas.utils;

public final class Constants {
    // Constantes para la conexión a los servicios REST
    public static final String API_APP_NAME = "RTC_MOVIL";
    public static final String API_BASE_URL = "https://demo2340870.mockable.io/rtc-rest-2/remote/";
    public static final int API_TIMEOUT = 90; // segundos

    // Constantes para los servicios de ubicación (GPS)
    public static final int LOCATION_INTERVAL = 10000; // milisegundos (= 10 segundos)
    public static final int LOCATION_FAST_INTERVAL = 5000; // milisegundos (= 5 segundos)

    // Llaves para el envío de pares llave-valor entre componentes de Android (usando Bundles)
    public final static String EXTRA_USER_FULLNAME = "RTC_EXTRA_USER_FULLNAME";
    public final static String EXTRA_USER_EMAIL = "RTC_EXTRA_USER_EMAIL";
    public final static String EXTRA_LOCATION_ACTIVE = "RTC_EXTRA_LOCATION_ACTIVE";
    public final static String EXTRA_LOCATION_DATA = "RTC_EXTRA_LOCATION_DATA";

    // Códigos de petición para la interacción entre componentes de Android
    public static final int REQ_CODE_GPS_PERMISSIONS = 1;
    public static final int REQ_CODE_GPS_ACTIVATE = 2;
    public static final int REQ_CODE_GPS_SERVICE = 3; // TODO: Usar solo si se va a activar
    public static final int REQ_CODE_CAMERA_PERMISSIONS = 11;
    public static final int REQ_CODE_CAMERA_RESULT = 12;
}
