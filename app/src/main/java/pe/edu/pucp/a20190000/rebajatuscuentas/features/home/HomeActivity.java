package pe.edu.pucp.a20190000.rebajatuscuentas.features.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.list.view.InmovableListActivity;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.login.view.LoginActivity;

public class HomeActivity extends AppCompatActivity {

    // private Button mInmovable;
    // private Button mLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Configurar la barra de la aplicación
        Toolbar toolbar = findViewById(R.id.home_lyt_toolbar);
        setSupportActionBar(toolbar);

        // mInmovable = findViewById(R.id.home_btn_inmovable);
        // mLogout = findViewById(R.id.home_btn_logout);
    }

    public void goToInmovable(View v) {
        Intent intent = new Intent(this, InmovableListActivity.class);
        startActivity(intent);
    }

    public void logout(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
