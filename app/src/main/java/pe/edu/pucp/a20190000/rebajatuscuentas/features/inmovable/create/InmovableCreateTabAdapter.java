package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.lang.ref.WeakReference;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;

public class InmovableCreateTabAdapter extends FragmentPagerAdapter {
    public final static int PAGE_COUNT = 3;
    private final static int FRAGMENT_MAIN = 0;
    private final static int FRAGMENT_LOCATION = 1;
    private final static int FRAGMENT_PHOTO = 2;
    private WeakReference<Context> mContext;

    public InmovableCreateTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = new WeakReference<>(context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Obtener la referencia al contexto de la aplicación
        Context context = mContext.get();
        if (context == null) return null;
        // Mostrar el título de la página
        switch (position) {
            case FRAGMENT_MAIN:
                return context.getString(R.string.inm_create_tab_main);
            case FRAGMENT_LOCATION:
                return context.getString(R.string.inm_create_tab_location);
            case FRAGMENT_PHOTO:
                return context.getString(R.string.inm_create_tab_photos);
            default:
                return null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case FRAGMENT_MAIN:
                return new InmovableCreateMainFragment();
            case FRAGMENT_LOCATION:
                return new InmovableCreateLocationFragment();
            case FRAGMENT_PHOTO:
                return new InmovableCreatePhotoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
