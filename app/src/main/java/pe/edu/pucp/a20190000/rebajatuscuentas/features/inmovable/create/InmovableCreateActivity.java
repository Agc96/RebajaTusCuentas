package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.base.IPresenter;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Constants;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.LocationService;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.TabAdapter;

public class InmovableCreateActivity extends AppCompatActivity implements IInmovableCreateView,
        LocationService.ILocationListener {

    private final static String TAG = "RTC_INM_CREATE_ACT";
    private IInmovableCreatePresenter mPresenter;
    private LocationService mService;
    private ViewPager mViewPager;
    private TabAdapter mTabAdapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmovable_create);

        mToolbar = findViewById(R.id.inm_create_lyt_toolbar);
        mViewPager = findViewById(R.id.inm_create_lyt_pager);
        mPresenter = new InmovableCreatePresenter(this);

        initializeComponents();
        initializeLocationService(savedInstanceState);
    }

    /**
     * Inicializa los componentes del layout, en este caso se inicializa la barra superior (Toolbar)
     * y los Fragments ubicados en las pestañas (Tabs) a mostrarse en el TabLayout.
     */
    private void initializeComponents() {
        // Configurar la barra de la aplicación
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Configurar botón para regresar al Activity anterior
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Configurar las pestañas a mostrarse
        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        mTabAdapter.addFragment(new InmovableCreateMainFragment(), getString(R.string.inm_create_tab_main));
        mTabAdapter.addFragment(new InmovableCreateLocationFragment(), getString(R.string.inm_create_tab_location));
        mTabAdapter.addFragment(new InmovableCreatePhotoFragment(), getString(R.string.inm_create_tab_photos));
        mViewPager.setAdapter(mTabAdapter);
        // Configurar el TabLayout
        TabLayout tabLayout = findViewById(R.id.inm_create_lyt_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void initializeLocationService(Bundle savedInstanceState) {
        // Por defecto el servicio está inactivo y la última ubicación es nula (no hay información)
        boolean active = false;
        Location location = null;
        // Verificar si se han persistido los datos, si es así obtenemos los datos
        if (savedInstanceState != null) {
            active = savedInstanceState.getBoolean(Constants.EXTRA_LOCATION_ACTIVE);
            location = savedInstanceState.getParcelable(Constants.EXTRA_LOCATION_DATA);
        }
        // Inicializamos el servicio
        mService = new LocationService(this, active, location);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQ_CODE_GPS_ACTIVATE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Log.d(TAG, "El usuario accedió a la activación del dispositivo GPS.");
                    mService.startLocationUpdates();
                    break;
                case Activity.RESULT_CANCELED:
                    Log.d(TAG, "El usuario decidió no activar el dispositivo GPS.");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onUpdate(boolean active, Location lastLocation) {
        // Actualizar el Fragment
        InmovableCreateLocationFragment fragment = (InmovableCreateLocationFragment) mTabAdapter.getItem(1);
        fragment.showInmovableLocationComponents(active, lastLocation);
        // Actualizar el Presenter
        if (active && lastLocation != null) {
            double latitude = lastLocation.getLatitude();
            double longitude = lastLocation.getLongitude();
            mPresenter.setInmovableLocationExtra(latitude, longitude);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.EXTRA_LOCATION_ACTIVE, mService.isActive());
        outState.putParcelable(Constants.EXTRA_LOCATION_DATA, mService.getLastLocation());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mService.stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mService.isActive()) {
            mService.startLocationUpdates();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Añadir los items al action bar si es que está presente.
        getMenuInflater().inflate(R.menu.menu_inmovable_create, menu);
        return true;
    }

    @Override
    public IPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public LocationService getLocationService() {
        return mService;
    }

    @Override
    public void onDestroy() {
        mService.onDestroy();
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
