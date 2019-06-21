package pe.edu.pucp.a20190000.rebajatuscuentas.features.base;

import android.content.Context;

public interface IView {
    IPresenter getPresenter();
    Context getContext();
    void onDestroy();
}
