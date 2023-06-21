import Client.UserClient;
import Client.OrderClient;
import Models.User;
import Models.Order;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import org.junit.After;


import java.util.List;


public class GetListOfIngredientsTest {
    User user;
    String token;
    Order order;
    List<String> ingredients;


    @Test
    @Description("Получение списка заказов без авторизации")
    public void getListOfOrdersWithoutAuthorization(){
        User user = new User("Lesha","alekseiev@yandex.ru","123456");
        ValidatableResponse createUser = UserClient.createUser(user);
        token = "";
        ValidatableResponse response = UserClient.getInfoAboutUser(token).log().all();
        response
                .body("success",equalTo(false))
                        .statusCode(401);
    }

    @Test
    @Description("Получение списка заказов с авторизацией")
    public void getListOfOrdersWithAuthorization(){
        user = new User("Egor","yogo@yandex.ru","12345");
        ValidatableResponse response = UserClient.createUser(user).log().all();
        token = response.extract().path("accessToken");
        User userForLoginTest = new User(user.getEmail(), user.getPassword());
        ValidatableResponse loginUser = UserClient.loginUser(userForLoginTest);
        ingredients = OrderClient.getListOfIngredients().extract().path("data._id");
        order= new Order(ingredients);
        ValidatableResponse createOrderForTest = OrderClient.createOrder(order).log().all();
        ValidatableResponse getInfoAboutUser = UserClient.getInfoAboutUser(token).log().all();
        getInfoAboutUser.statusCode(200)
                .body("success",equalTo(true));

    }

    @After
    @Description("Удаление пользователя")
    public void shutDown() {
        if (token != null) {
            UserClient.deleteUser(token).log().all();
        }
    }
}
