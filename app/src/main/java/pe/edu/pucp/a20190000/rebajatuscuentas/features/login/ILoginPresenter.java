package pe.edu.pucp.a20190000.rebajatuscuentas.features.login;

import pe.edu.pucp.a20190000.rebajatuscuentas.features.base.IPresenter;

public interface ILoginPresenter extends IPresenter {
    boolean verifyLoginData(String username, String password);
    void loginRest(String username, String password);
    void loginOffline(String username, String password);
}
