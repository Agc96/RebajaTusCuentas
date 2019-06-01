package pe.edu.pucp.a20190000.rebajatuscuentas.features.login.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.home.HomeActivity;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.login.presenter.ILoginPresenter;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.login.presenter.LoginPresenter;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private final static String TAG = "MFL_LOGIN_VIEW";
    public final static String LOGIN_EXTRA_FULLNAME = "LOGIN_EXTRA_FULLNAME";
    public final static String LOGIN_EXTRA_EMAIL = "LOGIN_EXTRA_EMAIL";

    private EditText mUsername;
    private EditText mPassword;
    private Button mSubmit;
    private ILoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = findViewById(R.id.login_ipt_username);
        mPassword = findViewById(R.id.login_ipt_password);
        mSubmit = findViewById(R.id.login_btn_submit);
        mPresenter = new LoginPresenter(this);
    }

    public void login(View v) {
        // Esconder el teclado
        Utilities.hideKeyboard(this);
        // Obtener datos de usuario
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        // Validar datos del usuario
        if (mPresenter.verifyLoginData(username, password)) {
            // Iniciar sesión y notificar al usuario que se está iniciando sesión
            mPresenter.loginRest(username, password);
            Utilities.showMessage(this, R.string.login_msg_loading);
        }
    }

    public void askForLoginOffline() {
        // Preguntar al usuario
        new AlertDialog.Builder(this)
                .setTitle(R.string.login_dlg_offline_title)
                .setMessage(R.string.login_dlg_offline_msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Obtener datos de usuario
                        String username = mUsername.getText().toString();
                        String password = mPassword.getText().toString();
                        // Iniciar sesión sin conexión
                        mPresenter.loginOffline(username, password);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.login_dlg_error_title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    public void goToHomePage(String fullName, String email) {
        // Iniciar la actividad principal
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(LOGIN_EXTRA_FULLNAME, fullName);
        intent.putExtra(LOGIN_EXTRA_EMAIL, email);
        startActivity(intent);
        // Cerrar esta actividad
        finish();
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
