package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
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
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.Inmovable;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Constants;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.GeolocationService;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Permissions;

public class InmovableCreateActivity extends AppCompatActivity implements IInmovableCreateView,
        GeolocationService.OnUpdateLocationListener {

    private final static String TAG = "RTC_INM_CREATE_ACT";
    private IInmovableCreatePresenter mPresenter;
    private GeolocationService mService;
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
        mViewPager.setOffscreenPageLimit(InmovableCreateTabAdapter.PAGE_COUNT);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initializeLocationService(Bundle savedInstanceState) {
        // Por defecto el servicio está inactivo y la última ubicación es nula (no hay información)
        boolean active = false;
        Location lastLocation = null;
        Inmovable inmovable = null;
        // Verificar si se han persistido los datos, si es así obtenemos los datos
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(Constants.EXTRA_INMOVABLE_LOCATION_ACTIVE)) {
                active = savedInstanceState.getBoolean(Constants.EXTRA_INMOVABLE_LOCATION_ACTIVE);
            }
            if (savedInstanceState.keySet().contains(Constants.EXTRA_INMOVABLE_LOCATION_DATA)) {
                lastLocation = savedInstanceState.getParcelable(Constants.EXTRA_INMOVABLE_LOCATION_DATA);
            }
            if (savedInstanceState.keySet().contains(Constants.EXTRA_INMOVABLE_DATA)) {
                inmovable = savedInstanceState.getParcelable(Constants.EXTRA_INMOVABLE_DATA);
            }
        }
        // Inicializamos el servicio y el presentador, y actualizamos los componentes
        mPresenter = new InmovableCreatePresenter(this, inmovable);
        mService = new GeolocationService(this, active, lastLocation);
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
        outState.putParcelable(Constants.EXTRA_INMOVABLE_DATA, mPresenter.getInmovable());
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
            if (fragment instanceof InmovableCreatePhotoFragment) {
                Log.d(TAG, "Obtenidos datos del Fragment de la foto.");
                ((InmovableCreatePhotoFragment) fragment).setInmovablePhotoData();
            }
        }
        // Validar y guardar el inmueble de ser posible
        mPresenter.validateAndSaveInmovable();
    }

    @Override
    public void askForSavePhotoPermissions() {
        if (Permissions.checkFromActivity(this, Constants.REQ_CODE_SAVE_PERMISSIONS,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            mPresenter.saveInmovable(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (Permissions.checkFromResults(permissions, grantResults)) {
            mPresenter.saveInmovable(true);
        } else {
            // Mostrar cuadro de diálogo
            new AlertDialog.Builder(this)
                    .setMessage(R.string.feature_storage_msg_denied)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mPresenter.saveInmovable(false);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void showInmovableSaveResult(boolean saved, @StringRes int messageId) {
        // Crear un cuadro de diálogo para mostrar el mensaje de guardado.
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setMessage(messageId);
        // Se terminará el programa si es que se logró guardar en base de datos
        if (saved) {
            dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Terminar el servicio GPS y cerrar esta actividad.
                    mService.stopLocationUpdates();
                    finish();
                }
            });
        } else {
            dialog.setPositiveButton(android.R.string.ok, null);
        }
        // Mostrar el cuadro de diálogo
        dialog.show();
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
    public GeolocationService getGeolocationService() {
        return mService;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mService.onDestroy();
        mPresenter.onDestroy();
    }
}
