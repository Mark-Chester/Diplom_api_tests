import jdk.jfr.Description;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import pojos.UserCreationModel;
import pojos.UserLoginBodyModel;
import pojos.UserLoginResponse;

import static data.TestData.*;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.UserSteps.*;

public class UserCreationTests extends BaseApiTest{
@Test
    @DisplayName("Success User Creation")
    @Description("Can create user, success response ok 200, success true  ")
    public void successUserCreationTest(){
    UserCreationModel user = new UserCreationModel(EMAIL,PASSWORD,USERNAME);
    userCreationResponse(user)
            .then()
            .log().all()
            .statusCode(SC_OK)
            .body("success",equalTo(true));
}
@Test
@DisplayName("cant create registered user")
@Description("cant create registered user, response 403 FORBIDDEN, message: User already exists")
public void cantCreateRegisteredUserTest(){
    UserCreationModel user = new UserCreationModel(EMAIL,PASSWORD,USERNAME);
    userCreationResponse(user)
            .then()
            .log().all()
            .statusCode(SC_OK)
            .body("success",equalTo(true));
    userCreationResponse(user)
            .then()
            .log().all()
            .statusCode(SC_FORBIDDEN)
            .body("message",equalTo("User already exists"));
}
@Test
@DisplayName("Cant create user without email")
@Description("Cant create user, response 403 FORBIDDEN, message:  Email, password and name are required fields ")
public void cantCreateUserWithoutEmailTest(){
        UserCreationModel user = new UserCreationModel(null,PASSWORD,USERNAME);
        userCreationResponse(user)
                .then()
                .log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Cant create user without password")
    @Description("Cant create user, response 403 FORBIDDEN, message:  Email, password and name are required fields ")
    public void cantCreateUserWithoutPasswordTest(){
        UserCreationModel user = new UserCreationModel(EMAIL,null,USERNAME);
        userCreationResponse(user)
                .then()
                .log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Cant create user without username")
    @Description("Cant create user, response 403 FORBIDDEN, message:  Email, password and name are required fields ")
    public void cantCreateUserWithoutUsernameTest(){
        UserCreationModel user = new UserCreationModel(EMAIL,PASSWORD,null);
        userCreationResponse(user)
                .then()
                .log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("Email, password and name are required fields"));
    }
@After
    public void shtDwn(){
    UserLoginBodyModel user = new UserLoginBodyModel(EMAIL,PASSWORD);
    if (userLogin(user).then().extract().response().as(UserLoginResponse.class).getAccessToken() != null) {
        userDeleteAfterLogin(user);
    }
}
}
