package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InmovableCreatePhotoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InmovableCreatePhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InmovableCreatePhotoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inmovable_create_photo, container, false);
    }

}