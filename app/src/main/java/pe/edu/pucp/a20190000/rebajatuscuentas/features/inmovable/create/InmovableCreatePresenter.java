package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.util.Log;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.Inmovable;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class InmovableCreatePresenter implements IInmovableCreatePresenter {
    private final static String TAG = "RTC_INM_CREATE_PRES";
    private IInmovableCreateView mView;
    private Inmovable mInmovable;
    private String mPhotoPath;

    public InmovableCreatePresenter(IInmovableCreateView view, Inmovable inmovable) {
        this.mView = view;
        this.mInmovable = (inmovable != null) ? inmovable : new Inmovable();
        this.mPhotoPath = null;
    }

    @Override
    public void setInmovableMainData(String name, double price) {
        mInmovable.setName(name);
        mInmovable.setPrice(price);
    }

    @Override
    public void setInmovableLocationData(String department, String province, String district,
                                         String location, String reference) {
        mInmovable.setDepartment(department);
        mInmovable.setProvince(province);
        mInmovable.setDistrict(district);
        mInmovable.setLocation(location);
        mInmovable.setReference(reference);
    }

    @Override
    public void setInmovableLocationExtra(double latitude, double longitude) {
        mInmovable.setLatitude(latitude);
        mInmovable.setLongitude(longitude);
    }

    @Override
    public void setInmovablePhoto(String photoPath) {
        this.mPhotoPath = photoPath;
    }

    @Override
    public Inmovable getInmovable() {
        return mInmovable;
    }

    @Override
    public void validateAndSaveInmovable() {
        if (validateInmovableData()) {
            if (Utilities.isEmpty(mPhotoPath)) {
                saveInmovable(true);
            } else {
                mView.askForSavePhotoPermissions();
            }
        }
    }

    private boolean validateInmovableData() {
        if (Utilities.isEmpty(mInmovable.getName())) {
            Utilities.showMessage(mView.getContext(), R.string.inm_create_msg_name_empty);
            return false;
        }
        if (mInmovable.getPrice() <= 0) {
            Utilities.showMessage(mView.getContext(), R.string.inm_create_msg_price_empty);
            return false;
        }
        if (Utilities.isEmpty(mInmovable.getDepartment())) {
            Utilities.showMessage(mView.getContext(), R.string.inm_create_msg_department_empty);
            return false;
        }
        if (Utilities.isEmpty(mInmovable.getProvince())) {
            Utilities.showMessage(mView.getContext(), R.string.inm_create_msg_province_empty);
            return false;
        }
        if (Utilities.isEmpty(mInmovable.getDistrict())) {
            Utilities.showMessage(mView.getContext(), R.string.inm_create_msg_district_empty);
            return false;
        }
        if (Utilities.isEmpty(mInmovable.getLocation())) {
            Utilities.showMessage(mView.getContext(), R.string.inm_create_msg_location_empty);
            return false;
        }
        return true;
    }

    @Override
    public void saveInmovable(boolean savePhoto) {
        if (validateInmovableData()) {
            Log.d(TAG, "Guardando datos del inmueble...");
            new InmovableCreateSaveTask(mView, mInmovable, mPhotoPath, savePhoto).execute();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
