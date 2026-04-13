import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import pojos.OrderBodyModel;
import pojos.UserCreationModel;
import pojos.UserLoginBodyModel;


import static data.TestData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.OrderSteps.*;
import static steps.UserSteps.*;

public class OrderCreationTests extends BaseApiTest{
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
    @DisplayName("Success order creation authorized user")
    @Description("Can create order with authorized user, status code ok 200, success: true")
    public void successOrderAuthorizedTest(){
        UserLoginBodyModel user = new UserLoginBodyModel(EMAIL,PASSWORD);
        OrderBodyModel order = new OrderBodyModel(INGREDIENTS);
        createAuthorizedOrder(order, getAccessToken(user))
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }
    @Test
    @DisplayName("Success order creation unauthorized user")
    @Description("Can create order with unauthorized user, status code ok 200, success: true")
    public void successOrderUnauthorizedTest(){
        OrderBodyModel order = new OrderBodyModel(INGREDIENTS);
        createUnauthorizedOrder(order)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("success",equalTo(true));
    }
    @Test
    @DisplayName("Success order creation with 2 ingredients")
    @Description("Can create order with 2 ingredients, status code ok 200, success: true")
    public void successOrderWithTwoIngredientsTest(){
        UserLoginBodyModel user = new UserLoginBodyModel(EMAIL,PASSWORD);
        OrderBodyModel order = new OrderBodyModel(INGREDIENTS);
        createAuthorizedOrder(order, getAccessToken(user))
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }
    @Test
    @DisplayName("cant create order without ingredients")
    @Description("Cant create order without ingredients, status code 400 bad request, message: Ingredient ids must be provided")
    public void cantCreateOrderWithoutIngredientsTest(){
        UserLoginBodyModel user = new UserLoginBodyModel(EMAIL,PASSWORD);
        OrderBodyModel order = new OrderBodyModel(null);
        createAuthorizedOrder(order, getAccessToken(user))
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));
    }
    @Test
    @DisplayName("cant create order with invalid ingredient hash")
    @Description("Cant create order with invalid ingredient hash, status code 500 internal server error")
    public void cantCreateOrderInvIngrHashTest(){
        UserLoginBodyModel user = new UserLoginBodyModel(EMAIL,PASSWORD);
        OrderBodyModel order = new OrderBodyModel(INGREDIENT);
        createAuthorizedOrder(order, getAccessToken(user))
                .then()
                .log().all()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
    @After
    public void shtDwn(){
        UserLoginBodyModel user = new UserLoginBodyModel(EMAIL,PASSWORD);
        userDeleteAfterLogin(user);
    }
}
