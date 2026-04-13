package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import pojos.UserCreationModel;
import pojos.UserLoginBodyModel;
import pojos.UserLoginResponse;

import static data.TestData.*;
import static io.restassured.RestAssured.given;


public class UserSteps {
    @Step("UserCreation")
    public static Response createUserResponse(UserCreationModel user){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(USERCREATION_ENDPOINT);

    }
    @Step("User Created Response")
    public static Response userCreationResponse(UserCreationModel user){
        return createUserResponse(user)
                .then()
                .extract().response();
    }
    @Step("User delete")
    public static void deleteUser(String token){
        given()
                .log().all()
                .header("Authorization", token)
                .when()
                .delete(USERDELETE_ENDPOINT);
    }
    @Step("User login")
    public static Response userLogin(UserLoginBodyModel user){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(USERLOGIN_ENDPOINT);
    }
    @Step("User delete after login")
    public static void userDeleteAfterLogin(UserLoginBodyModel user){
        UserLoginResponse response = userLogin(user).then().extract().response().as(UserLoginResponse.class);
        String token = response.getAccessToken();
        deleteUser(token);
    }
    @Step("Get access token")
    public static String getAccessToken(UserLoginBodyModel user) {
       return userLogin(user).then().extract().response().as(UserLoginResponse.class).getAccessToken();
    }
}
