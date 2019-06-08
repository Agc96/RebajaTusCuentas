package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.presenter.InmovableCreateTabAdapter;

public class InmovableCreateActivity extends AppCompatActivity {

    private final static String TAG = "RTC_INM_CREATE_ACT";
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmovable_create);

        mViewPager = findViewById(R.id.inm_create_lyt_pager);
        setUpTabs();
    }

    private void setUpTabs() {
        InmovableCreateTabAdapter mTabAdapter = new InmovableCreateTabAdapter(getSupportFragmentManager());
        mTabAdapter.addFragment(new InmovableCreateMainFragment(), getString(R.string.inm_create_tab_main));
        mTabAdapter.addFragment(new InmovableCreateLocationFragment(), getString(R.string.inm_create_tab_location));
        mTabAdapter.addFragment(new InmovableCreatePhotoFragment(), getString(R.string.inm_create_tab_photos));
        mViewPager.setAdapter(mTabAdapter);

        TabLayout tabLayout = findViewById(R.id.inm_create_lyt_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }
}
