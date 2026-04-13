package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojos.OrderBodyModel;

import static data.TestData.ORDERCREATION_ENDPOINT;
import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Order creation authorized")
    public static Response createAuthorizedOrder(OrderBodyModel order,String token){
        return given()
                .log().all()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(ORDERCREATION_ENDPOINT);
    }
    @Step("Order creation unauthorized")
    public static Response createUnauthorizedOrder(OrderBodyModel order){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(ORDERCREATION_ENDPOINT);
    }
}
