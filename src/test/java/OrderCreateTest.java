import Client.UserClient;
import Client.OrderClient;
import Models.User;
import Models.Order;
import io.restassured.response.ValidatableResponse;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import org.junit.After;

import java.util.ArrayList;
import java.util.List;


public class OrderCreateTest {
    List<String> ingredients;
    Order order;
    String token;
    //Создание заказа авторизованным пользователем
    @Test
    public void createOrderWithAuthorization(){
        User user = new User("Lesha","aleksei@yandex.ru","123456");
        ValidatableResponse createUser = UserClient.createUser(user);
        token = createUser.extract().path("accessToken");
        User userForLogin = new User(user.getEmail(),user.getPassword());
        ValidatableResponse loginUser = UserClient.loginUser(userForLogin);
        ingredients = OrderClient.getListOfIngredients().extract().path("data._id");
        order = new Order(ingredients);
        ValidatableResponse createOrder = OrderClient.createOrder(order).log().all();
        createOrder
                .body("success", equalTo(true))
                .statusCode(200);
    }
    @Test
    public void createOrderWithAuthorizationWithoutIngredients(){
        User user = new User("Lesha","aleksei@yandex.ru","123456");
        ValidatableResponse createUser = UserClient.createUser(user);
        token = createUser.extract().path("accessToken");
        ValidatableResponse loginUser = UserClient.loginUser(user);
        order = new Order(ingredients);
        ValidatableResponse createOrder = OrderClient.createOrder(order).log().all();
        createOrder.body("message", equalTo("Ingredient ids must be provided"))
                .statusCode(400);
    }
    @Test
    public void createOrderWithoutAuthorization(){
    ingredients = OrderClient.getListOfIngredients().extract().path("data._id");
    order = new Order(ingredients);
    ValidatableResponse createOrder = OrderClient.createOrder(order).log().all();
    createOrder
            .body("success",equalTo(true))
            .statusCode(200);
    }

    @Test
    public void createOrderWithWrongIngredients(){
    ingredients = new ArrayList<>();
    ingredients.add("1234");
    ingredients.add("5678");
    order = new Order(ingredients);
    ValidatableResponse createOrder = OrderClient.createOrder(order).log().all();
    createOrder.statusCode(500);
    }

    @After
    public void shutDown() {
        if (token != null) {
            UserClient.deleteUser(token).log().all();
        }
    }
}
