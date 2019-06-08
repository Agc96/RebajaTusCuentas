package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.list.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.create.view.InmovableCreateActivity;

public class InmovableListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmovable_list);
    }

    public void goToCreate(View v) {
        Intent intent = new Intent(this, InmovableCreateActivity.class);
        startActivity(intent);
    }
}
