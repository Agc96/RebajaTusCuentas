package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;

public class InmovableCreateTabAdapter extends FragmentPagerAdapter {
    private final static int FRAGMENT_COUNT = 3;
    private final static int FRAGMENT_MAIN = 0;
    private final static int FRAGMENT_LOCATION = 1;
    private final static int FRAGMENT_PHOTO = 2;
    private String[] mFragmentTitles;

    public InmovableCreateTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        mFragmentTitles = new String[FRAGMENT_COUNT];
        mFragmentTitles[FRAGMENT_MAIN] = context.getString(R.string.inm_create_tab_main);
        mFragmentTitles[FRAGMENT_LOCATION] = context.getString(R.string.inm_create_tab_location);
        mFragmentTitles[FRAGMENT_PHOTO] = context.getString(R.string.inm_create_tab_photos);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < FRAGMENT_COUNT) {
            return mFragmentTitles[position];
        }
        return null;
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
        return FRAGMENT_COUNT;
    }

}
