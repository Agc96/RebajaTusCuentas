package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import pe.edu.pucp.a20190000.rebajatuscuentas.features.base.IPresenter;

public interface IInmovableCreatePresenter extends IPresenter {
    void setInmovableMainData(String name, Double price);
    void setInmovableLocationData(String department, String province, String district,
                                  String location, String reference);
    void setInmovableLocationExtra(Double latitude, Double longitude);
    void setInmovablePhoto();
    void saveInmovable();
}
