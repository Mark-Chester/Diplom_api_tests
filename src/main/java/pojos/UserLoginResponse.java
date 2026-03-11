package pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginResponse {
    private boolean success;
    @Getter private String accessToken;
    private String refreshToken;
    private UserModel user;

}
