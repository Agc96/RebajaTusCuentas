package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Constants;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Permissions;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class InmovableCreateLocationFragment extends Fragment {

    private static final String TAG = "RTC_INM_CREATE_LOC_FRG";

    private IInmovableCreateView mView;
    private EditText mDepartment;
    private EditText mProvince;
    private EditText mDistrict;
    private EditText mLocation;
    private EditText mReference;
    private EditText mLatitude;
    private EditText mLongitude;
    private Button mActivateButton;
    private Button mDeactivateButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IInmovableCreateView) {
            mView = (IInmovableCreateView) context;
        } else {
            throw new RuntimeException("El Activity debe implementar la interfaz IInmovableCreateView.");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragment
        View view = inflater.inflate(R.layout.fragment_inmovable_create_location, container, false);
        // Obtener los componentes
        mDepartment = view.findViewById(R.id.inm_create_loc_ipt_department);
        mProvince = view.findViewById(R.id.inm_create_loc_ipt_province);
        mDistrict = view.findViewById(R.id.inm_create_loc_ipt_district);
        mLocation = view.findViewById(R.id.inm_create_loc_ipt_location);
        mReference = view.findViewById(R.id.inm_create_loc_ipt_reference);
        mActivateButton = view.findViewById(R.id.inm_create_loc_btn_activate_gps);
        mDeactivateButton = view.findViewById(R.id.inm_create_loc_btn_deactivate_gps);
        // Inicializar los componentes
        initializeComponents();
        return view;
    }

    private void initializeComponents() {
        // Configurar el botón de activar GPS
        mActivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestGeolocation();
            }
        });
        // Configurar el botón de desactivar GPS
        mDeactivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mView.getLocationUtility().isActive()) {
                    mView.getLocationUtility().stopLocationUpdates();
                }
            }
        });
    }

    private void requestGeolocation() {
        Context context = mView.getContext();
        // Verificar si contamos con GPS
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (lm == null) {
            Utilities.showMessage(context, R.string.gps_msg_unavailable);
            return;
        }
        // Solicitar permisos a la aplicación para iniciar el servicio de geolocalización
        String[] permissions = new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        if (Permissions.checkFromFragment(this, Constants.REQ_CODE_GPS_PERMISSIONS, permissions)) {
            mView.getLocationUtility().startLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Verificar si se aceptaron los permisos para el uso de GPS
        if (requestCode == Constants.REQ_CODE_GPS_PERMISSIONS) {
            if (Permissions.checkFromResults(permissions, grantResults)) {
                mView.getLocationUtility().startLocationUpdates();
            } else {
                Utilities.showMessage(mView.getContext(), R.string.gps_msg_permissions);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void setInmovableLocationData() {
        IInmovableCreatePresenter presenter = (IInmovableCreatePresenter) mView.getPresenter();
        // Obtener y actualizar los datos principales
        String department = mDepartment.getText().toString();
        String province = mProvince.getText().toString();
        String district = mDistrict.getText().toString();
        String location = mLocation.getText().toString();
        String reference = mReference.getText().toString();
        presenter.setInmovableLocationData(department, province, district, location, reference);
        // TODO: Ver si desde aquí actualizo los valores de latitud y longitud
    }
}
