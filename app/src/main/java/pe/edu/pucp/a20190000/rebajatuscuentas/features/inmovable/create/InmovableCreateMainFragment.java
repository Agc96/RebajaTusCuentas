package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;

public class InmovableCreateMainFragment extends Fragment {
    private static final String TAG = "RTC_INM_CREATE_MAIN_FRG";
    private IInmovableCreateView mView;
    private EditText mName;
    private EditText mPrice;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IInmovableCreateView) {
            mView = (IInmovableCreateView) context;
        } else {
            throw new RuntimeException("El Activity debe implementar la interfaz IInmovableCreateView.");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inmovable_create_main, container, false);
        mName = view.findViewById(R.id.inm_create_main_ipt_name);
        mPrice = view.findViewById(R.id.inm_create_main_ipt_price);
        return view;
    }

    public void setInmovableMainData() {
        // Obtener los datos principales
        String name = mName.getText().toString();
        Double price = null;
        try {
            price = Double.parseDouble(mPrice.getText().toString());
        } catch (NumberFormatException ex) {
            // Nada por ahora, esto será validado en el presentador.
        }
        // Actualizar los datos principales en el presentador
        mView.getPresenter().setInmovableMainData(name, price);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mView = null;
    }
}
