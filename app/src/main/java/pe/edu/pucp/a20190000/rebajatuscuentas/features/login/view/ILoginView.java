package pe.edu.pucp.a20190000.rebajatuscuentas.features.login.view;

import pe.edu.pucp.a20190000.rebajatuscuentas.features.base.IView;

public interface ILoginView extends IView {
    void askForLoginOffline();
    void goToHomePage(String names, String email);
    void showErrorDialog(String message);
}
