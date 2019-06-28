package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.Inmovable;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.base.IPresenter;

public interface IInmovableCreatePresenter extends IPresenter {
    void setInmovableMainData(String name, double price);
    void setInmovableLocationData(String department, String province, String district,
                                  String direction, String reference);
    void setInmovableLocationExtra(double latitude, double longitude);
    void setInmovablePhoto(String photoPath);
    Inmovable getInmovable();
    void validateAndSaveInmovable();
    void saveInmovable(boolean savePhoto);
}
