package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.util.Log;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.Inmovable;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class InmovableCreatePresenter implements IInmovableCreatePresenter {
    private final static String TAG = "RTC_INM_CREATE_PRES";
    private IInmovableCreateView view;
    private Inmovable inmovable;

    public InmovableCreatePresenter(IInmovableCreateView view) {
        this.view = view;
        this.inmovable = new Inmovable();
    }

    @Override
    public void setInmovableMainData(String name, Double price) {
        inmovable.setName(name);
        inmovable.setPrice(price);
    }

    @Override
    public void setInmovableLocationData(String department, String province, String district,
                                         String location, String reference) {
        inmovable.setDepartment(department);
        inmovable.setProvince(province);
        inmovable.setDistrict(district);
        inmovable.setLocation(location);
        inmovable.setReference(reference);
    }

    @Override
    public void setInmovableLocationExtra(Double latitude, Double longitude) {
        inmovable.setLatitude(latitude);
        inmovable.setLongitude(longitude);
    }

    @Override
    public void setInmovablePhoto() {
        // TODO
    }

    @Override
    public void saveInmovable() {
        if (validateData()) {
            Log.d(TAG, "Guardando inmueble en base de datos...");
            new InmovableCreateSaveTask(view, inmovable).execute();
        }
    }

    private boolean validateData() {
        if (Utilities.isEmpty(inmovable.getName())) {
            Utilities.showMessage(view.getContext(), R.string.inm_create_msg_name_empty);
            return false;
        }
        if (inmovable.getPrice() == null) {
            Utilities.showMessage(view.getContext(), R.string.inm_create_msg_price_empty);
            return false;
        }
        if (inmovable.getPrice() <= 0) {
            Utilities.showMessage(view.getContext(), R.string.inm_create_msg_price_invalid);
            return false;
        }
        if (Utilities.isEmpty(inmovable.getDepartment())) {
            Utilities.showMessage(view.getContext(), R.string.inm_create_msg_department_empty);
            return false;
        }
        if (Utilities.isEmpty(inmovable.getProvince())) {
            Utilities.showMessage(view.getContext(), R.string.inm_create_msg_province_empty);
            return false;
        }
        if (Utilities.isEmpty(inmovable.getDistrict())) {
            Utilities.showMessage(view.getContext(), R.string.inm_create_msg_district_empty);
            return false;
        }
        if (Utilities.isEmpty(inmovable.getLocation())) {
            Utilities.showMessage(view.getContext(), R.string.inm_create_msg_location_empty);
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
