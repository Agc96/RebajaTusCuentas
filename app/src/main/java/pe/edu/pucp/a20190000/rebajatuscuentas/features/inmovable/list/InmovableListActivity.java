package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.list;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.InmovableCreateActivity;

public class InmovableListActivity extends AppCompatActivity {
    private final static String TAG = "RTC_INM_LIST_ACT";
    private Toolbar mToolbar;
    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmovable_list);

        mToolbar = findViewById(R.id.inm_list_lyt_toolbar);
        mList = findViewById(R.id.inm_list_lyt_list);
        initializeComponents();
    }

    private void initializeComponents() {
        // Configurar la barra de la aplicación
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Configurar botón para regresar al Activity anterior
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Configurar el RecyclerView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Añadir los items al action bar si es que está presente.
        getMenuInflater().inflate(R.menu.menu_inmovable_list, menu);
        return true;
    }

    public void goToCreate(View v) {
        Intent intent = new Intent(this, InmovableCreateActivity.class);
        startActivity(intent);
    }
}
