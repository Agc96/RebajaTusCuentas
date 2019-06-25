package pe.edu.pucp.a20190000.rebajatuscuentas.features.base;

import android.content.Context;

public interface IView {
    Context getContext();
    void onDestroy();
}
