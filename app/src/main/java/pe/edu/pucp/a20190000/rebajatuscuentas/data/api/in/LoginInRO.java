package pe.edu.pucp.a20190000.rebajatuscuentas.data.api.in;

import com.fasterxml.jackson.annotation.JsonRootName;

import pe.edu.pucp.a20190000.rebajatuscuentas.data.api.base.BaseInRO;

@JsonRootName("loginInRO")
public class LoginInRO extends BaseInRO {

    private String username;
    private String password;

    public LoginInRO(String applicationName, String username, String password) {
        super(applicationName);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
