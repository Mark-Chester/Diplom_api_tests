import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import pojos.UserCreationModel;
import pojos.UserLoginBodyModel;


import static data.TestData.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.UserSteps.*;

public class UserLoginTests extends BaseApiTest{
    @Before
    public void userCreation(){
        UserCreationModel user = new UserCreationModel(EMAIL,PASSWORD,USERNAME);
        userCreationResponse(user)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("success",equalTo(true));
    }
    @Test
    @DisplayName("Success User Login")
    @Description("Can login with registered User, status code 200ok, success true")
    public void successUserLoginTest(){
        UserLoginBodyModel user = new UserLoginBodyModel(EMAIL,PASSWORD);
        userLogin(user)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }
    @Test
    @DisplayName("Cant login user without email")
    @Description("Cant login registered User without email, status code 401 Unauthorized, message: email or password are incorrect")
    public void cantLoginWithoutEmailTest(){
        UserLoginBodyModel user = new UserLoginBodyModel(null,PASSWORD);
        userLogin(user)
                .then()
                .log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
    }
    @Test
    @DisplayName("Cant login user without password")
    @Description("Cant login registered User without password, status code 401 Unauthorized, message: email or password are incorrect")
    public void cantLoginWithoutPasswordTest(){
        UserLoginBodyModel user = new UserLoginBodyModel(EMAIL,null);
        userLogin(user)
                .then()
                .log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
    }
    @After
    public void shtDwn(){
        UserLoginBodyModel user = new UserLoginBodyModel(EMAIL,PASSWORD);
        userDeleteAfterLogin(user);
    }
}
