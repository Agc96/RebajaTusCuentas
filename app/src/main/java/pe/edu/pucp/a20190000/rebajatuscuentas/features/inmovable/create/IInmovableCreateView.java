package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import pe.edu.pucp.a20190000.rebajatuscuentas.features.base.IView;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.LocationService;

public interface IInmovableCreateView extends IView {
    LocationService getLocationService();
}
