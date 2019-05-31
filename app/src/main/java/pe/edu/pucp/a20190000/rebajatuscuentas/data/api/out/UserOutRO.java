package pe.edu.pucp.a20190000.rebajatuscuentas.data.api.out;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import pe.edu.pucp.a20190000.rebajatuscuentas.data.api.base.BaseOutRO;

@JsonRootName("userOutRO")
public class UserOutRO extends BaseOutRO {

    private int userId;
    private String fullName;
    private String email;

    @JsonCreator
    public UserOutRO(@JsonProperty("errorCode") int errorCode, @JsonProperty("message") String message,
                     @JsonProperty("userId") int userId, @JsonProperty("fullName") String fullName,
                     @JsonProperty("email") String email) {
        super(errorCode, message);
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }
}
