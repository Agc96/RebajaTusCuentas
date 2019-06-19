package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.presenter;

import pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.view.IInmovableCreateView;

public class InmovableCreatePresenter implements IInmovableCreatePresenter {

    private IInmovableCreateView view;

    public InmovableCreatePresenter(IInmovableCreateView view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
