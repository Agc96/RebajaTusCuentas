package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import pe.edu.pucp.a20190000.rebajatuscuentas.features.base.IView;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.GeolocationService;

public interface IInmovableCreateView extends IView {
    IInmovableCreatePresenter getPresenter();
    GeolocationService getGeolocationService();
    void askForSavePhotoPermissions();
    void showInmovableSaveResult(int messageId);
}
