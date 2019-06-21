package pe.edu.pucp.a20190000.rebajatuscuentas.features.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.list.InmovableListActivity;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.login.LoginActivity;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Constants;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final static String TAG = "RTC_HOME_ACT";
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = findViewById(R.id.home_lyt_toolbar);
        mDrawerLayout = findViewById(R.id.home_lyt_drawer);
        mNavigationView = findViewById(R.id.home_lyt_nav_view);

        initializeComponents();
        showUserDetails();
    }

    private void initializeComponents() {
        // Configurar la barra de la aplicación
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Configurar botón del menú principal
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_toolbar_menu);
        }
        // Configurar el Navigation View
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void showUserDetails() {
        Bundle extras = getIntent().getExtras();
        View navigationHeader = mNavigationView.getHeaderView(0);
        if (extras != null && navigationHeader != null) {
            // Obtener y mostrar el nombre completo del usuario
            if (extras.keySet().contains(Constants.EXTRA_USER_FULLNAME)) {
                TextView fullNameView = navigationHeader.findViewById(R.id.home_txt_fullname);
                fullNameView.setText(extras.getString(Constants.EXTRA_USER_FULLNAME));
            }
            // Obtener y mostrar el correo electrónico del usuario
            if (extras.keySet().contains(Constants.EXTRA_USER_EMAIL)) {
                TextView emailView =  navigationHeader.findViewById(R.id.home_txt_email);
                emailView.setText(extras.getString(Constants.EXTRA_USER_EMAIL));
            }
        }
    }

    public void goToInmovable(View v) {
        Intent intent = new Intent(this, InmovableListActivity.class);
        startActivity(intent);
    }

    public void goToClients(View v) {
        // TODO: Ir al Activity de listado de cartera de clientes
    }

    public void goToRequests(View v) {
        // TODO: Ir al Activity de listado de solicitudes de dinero
    }

    public void goToProfile(View v) {
        // TODO: Ir al Activity de configuración del perfil de usuario
    }

    public void logout(View v) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.home_dlg_logout_msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cerrar este Activity e iniciar el de inicio de sesión
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Realizar acciones dependiendo del item presionado
        switch (menuItem.getItemId()) {
            case R.id.home_menu_inmovables:
                goToInmovable(null);
                break;
            case R.id.home_menu_clients:
                goToClients(null);
                break;
            case R.id.home_menu_requests:
                goToRequests(null);
                break;
            case R.id.home_menu_profile:
                goToProfile(null);
                break;
            case R.id.home_menu_logout:
                logout(null);
                break;
        }
        // Cerrar el Navigation Drawer cuando se haga clic en un item
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Configurar acción del botón del menú
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            // Si está abierto el NavigationView, cerrar el DrawerLayout
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // Si está cerrado el NavigationView, salir de la aplicación
            super.onBackPressed();
        }
    }
}
