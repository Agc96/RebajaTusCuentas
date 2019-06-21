package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import pe.edu.pucp.a20190000.rebajatuscuentas.features.base.IPresenter;

public interface IInmovableCreatePresenter extends IPresenter {
    void setInmovableMainData(String name, double price);
    void setInmovableLocationData(String department, String province, String district,
                                  String location, String reference);
    void setInmovableLocationExtra(double latitude, double longitude);
    void setInmovablePhoto();
    void saveInmovable();
}
