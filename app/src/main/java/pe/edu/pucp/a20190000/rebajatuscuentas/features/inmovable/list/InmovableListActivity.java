package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.list;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.InmovableMainData;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.InmovableCreateActivity;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class InmovableListActivity extends AppCompatActivity {
    private final static String TAG = "RTC_INM_LIST_ACT";
    private Toolbar mToolbar;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private TextView mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmovable_list);

        mToolbar = findViewById(R.id.inm_list_lyt_toolbar);
        mRecycler = findViewById(R.id.inm_list_lyt_list);
        mStatus = findViewById(R.id.inm_list_txt_status);
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
        // Buscar en la base de datos
        new InmovableListTask(this).execute();
    }

    public void initializeList(List<InmovableMainData> inmovables) {
        if (Utilities.isEmpty(inmovables)) {
            // Si no existen inmuebles, mostramos un mensaje al respecto
            mStatus.setText(R.string.inm_list_txt_empty);
        } else {
            // Usamos un LinearLayoutManager para mostrar en orden vertical los datos de los inmuebles
            mManager = new LinearLayoutManager(this);
            mRecycler.setLayoutManager(mManager);
            // Especificar el adaptador para el RecyclerView
            mAdapter = new InmovableListAdapter(inmovables);
            mRecycler.setAdapter(mAdapter);
            // Escondemos el mensaje de carga
            mStatus.setVisibility(View.GONE);
        }
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
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
