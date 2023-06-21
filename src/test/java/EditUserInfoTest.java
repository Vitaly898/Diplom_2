import Client.UserClient;
import Models.User;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import org.junit.After;



public class EditUserInfoTest {
    User user;
    String token;

    @Test
    @Description("Редактирование пользователя с авторизацией")
    public void EditUserInfoWithAuthorizationTest(){
    user = new User("Mihail","michael@yandex.ru","123456");
    ValidatableResponse createUser = UserClient.createUser(user);
    token = createUser.extract().path("accessToken");
    User userForLogin = new User(user.getEmail(),user.getPassword());
    ValidatableResponse login = UserClient.loginUser(userForLogin);
    ValidatableResponse response = UserClient.editUser(user,token);
    response
            .statusCode(200)
            .body("success",equalTo(true));
    }

    @Test
    @Description("Редактирование пользователя без авторизации")
    public void EditUserInfoWithoutAuthorization(){
        user = new User("Mark","ark@yandex.ru","123456");
        ValidatableResponse response = UserClient.editUser(user,"");
        response.statusCode(401)
                .body("message",equalTo("You should be authorised"));
    }

    @After
    @Description("Удаление пользователя")
    public void shutDown(){
        if (token != null) {
            UserClient.deleteUser(token);
        }
    }
}
