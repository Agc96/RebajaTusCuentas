package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Constants;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.LocationService;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class InmovableCreateActivity extends AppCompatActivity implements IInmovableCreateView,
        LocationService.OnUpdateLocationListener {

    private final static String TAG = "RTC_INM_CREATE_ACT";
    private IInmovableCreatePresenter mPresenter;
    private LocationService mService;
    private ViewPager mViewPager;
    private InmovableCreateTabAdapter mTabAdapter;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmovable_create);

        mToolbar = findViewById(R.id.inm_create_lyt_toolbar);
        mViewPager = findViewById(R.id.inm_create_lyt_pager);
        mTabLayout = findViewById(R.id.inm_create_lyt_tabs);
        mPresenter = new InmovableCreatePresenter(this);

        initializeComponents(savedInstanceState);
        initializeLocationService(savedInstanceState);
    }

    /**
     * Inicializa los componentes del layout, en este caso se inicializa la barra superior (Toolbar)
     * y los Fragments ubicados en las pestañas (Tabs) a mostrarse en el TabLayout.
     */
    private void initializeComponents(Bundle savedInstanceState) {
        // Configurar la barra de la aplicación
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Configurar botón para regresar al Activity anterior
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Configurar el ViewPager y el TabLayout
        mTabAdapter = new InmovableCreateTabAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mTabAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initializeLocationService(Bundle savedInstanceState) {
        // Por defecto el servicio está inactivo y la última ubicación es nula (no hay información)
        boolean active = false;
        Location lastLocation = null;
        // Verificar si se han persistido los datos, si es así obtenemos los datos
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(Constants.EXTRA_INMOVABLE_LOCATION_ACTIVE)) {
                active = savedInstanceState.getBoolean(Constants.EXTRA_INMOVABLE_LOCATION_ACTIVE);
            }
            if (savedInstanceState.keySet().contains(Constants.EXTRA_INMOVABLE_LOCATION_DATA)) {
                lastLocation = savedInstanceState.getParcelable(Constants.EXTRA_INMOVABLE_LOCATION_DATA);
            }
        }
        // Inicializamos el servicio y actualizamos los componentes
        mService = new LocationService(this, active, lastLocation);
        // La actualización de los valores en el Presenter y el Fragment se hará en onResume().
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
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onUpdateLocation(boolean active, Location lastLocation) {
        // Actualizar el Fragment
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment instanceof InmovableCreateLocationFragment) {
                Log.d(TAG, "Se encontró el Fragment de ubicaciones, actualizando valores...");
                ((InmovableCreateLocationFragment) fragment).showInmovableLocationComponents(active,
                        lastLocation);
                return;
            }
        }
        // Actualizar el Presenter
        if (lastLocation != null) {
            double latitude = lastLocation.getLatitude();
            double longitude = lastLocation.getLongitude();
            mPresenter.setInmovableLocationExtra(latitude, longitude);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Constants.EXTRA_INMOVABLE_LOCATION_ACTIVE, mService.isActive());
        outState.putParcelable(Constants.EXTRA_INMOVABLE_LOCATION_DATA, mService.getLastLocation());
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
        onUpdateLocation(mService.isActive(), mService.getLastLocation());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Añadir los items al action bar si es que está presente.
        getMenuInflater().inflate(R.menu.menu_inmovable_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.inm_create_tlb_save) {
            prepareSaveInmovable();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareSaveInmovable() {
        // Obtener datos de los inputs desde los Fragments
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof InmovableCreateMainFragment) {
                ((InmovableCreateMainFragment) fragment).setInmovableMainData();
                Log.d(TAG, "Obtenidos datos del Fragment de los datos principales.");
            }
            if (fragment instanceof InmovableCreateLocationFragment) {
                Log.d(TAG, "Obtenidos datos del Fragment de la ubicación.");
                ((InmovableCreateLocationFragment) fragment).setInmovableLocationData();
            }
        }
        // Validar y guardar el inmueble de ser posible
        mPresenter.saveInmovable();
    }

    @Override
    public void showInmovableSaveResult(boolean saved) {
        if (saved) {
            // Mostrar mensaje de éxito
            Utilities.showMessage(this, R.string.inm_create_msg_success);
            // Terminar el servicio GPS y cerrar esta actividad.
            mService.stopLocationUpdates();
            finish();
        } else {
            // Mostrar cuadro de diálogo que indica una falla al guardar en base de datos.
            new AlertDialog.Builder(this)
                    .setMessage(R.string.inm_create_msg_failure)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }

    @Override
    public IInmovableCreatePresenter getPresenter() {
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
        super.onDestroy();
        mService.onDestroy();
        mPresenter.onDestroy();
    }
}
