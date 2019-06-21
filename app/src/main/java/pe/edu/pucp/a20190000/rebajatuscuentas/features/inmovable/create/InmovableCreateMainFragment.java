package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;

public class InmovableCreateMainFragment extends Fragment {

    private static final String TAG = "RTC_INM_CREATE_MAIN_FRG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inmovable_create_main, container, false);
    }
}
