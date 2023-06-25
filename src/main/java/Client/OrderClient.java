package Client;
import Models.Order;
import Helpers.Constants;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


import static Helpers.BaseClient.getSpec;
import static io.restassured.RestAssured.given;

public class OrderClient {
    //Ручка создания заказа
    @Step("Создание заказа")
    public static ValidatableResponse createOrder(Order order){
        return given()
                .spec(getSpec())
                .header("Content-type","application/json")
                .and()
                .body(order)
                .when()
                .post(Constants.CREATE_ORDER)
                .then();
    }

    //Ручка получения ингредиентов
    @Step("Получить список ингредиентов")
    public static ValidatableResponse getListOfIngredients(){
        return given()
                .spec(getSpec())
                .header("Content-type","application/json")
                .when()
                .get(Constants.LIST_OF_INGREDIENTS)
                .then();
    }

    //Ручка получения списка заказов
    @Step("Получить список заказов")
    public static ValidatableResponse getListOfOrders(String token){
        return given()
                .spec(getSpec())
                .header("Authorization",token)
                .when()
                .get(Constants.LIST_OF_ORDERS)
                .then();
    }

}
