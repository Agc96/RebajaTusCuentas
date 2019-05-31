package pe.edu.pucp.a20190000.rebajatuscuentas.features.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.login.LoginActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView mFullName;
    private TextView mEmail;
    private Button mLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFullName = findViewById(R.id.home_txt_fullname);
        mEmail = findViewById(R.id.home_txt_email);
        mLogout = findViewById(R.id.home_btn_logout);
        showUserDetails();
    }

    private void showUserDetails() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Obtener los valores enviados por el Intent
            String fullName = extras.getString(LoginActivity.LOGIN_EXTRA_FULLNAME);
            String email = extras.getString(LoginActivity.LOGIN_EXTRA_EMAIL);
            // Colocar los valores en el TextView
            mFullName.setText(fullName);
            mEmail.setText(email);
        }
    }

    public void logout(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
