package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.presenter.IInmovableCreatePresenter;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.presenter.InmovableCreatePresenter;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.presenter.InmovableCreateTabAdapter;

public class InmovableCreateActivity extends AppCompatActivity implements IInmovableCreateView {

    private final static String TAG = "RTC_INM_CREATE_ACT";
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private IInmovableCreatePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmovable_create);

        mToolbar = findViewById(R.id.inm_create_lyt_toolbar);
        mViewPager = findViewById(R.id.inm_create_lyt_pager);
        mPresenter = new InmovableCreatePresenter(this);

        setUpToolbar();
        setUpTabs();
    }

    private void setUpToolbar() {
        // Configurar la barra de la aplicación
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Configurar botón para regresar al Activity anterior
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Añadir los items al action bar si es que está presente.
        getMenuInflater().inflate(R.menu.menu_inmovable_create, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult se va al activity.");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
