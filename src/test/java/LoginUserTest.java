import Client.UserClient;
import Models.User;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import org.junit.After;


public class LoginUserTest {

    User user;
    String token;


    @Test
    @Description("Логин существующего пользователя")
    public void loginExistingUserTest(){
        user = new User("Egor","egoego@yandex.ru","12345");
        ValidatableResponse response = UserClient.createUser(user);
        token = response.extract().path("accessToken");
        User userForLoginTest = new User(user.getEmail(), user.getPassword());
        ValidatableResponse loginUser = UserClient.loginUser(userForLoginTest);
        loginUser
                .statusCode(200)
                .body("success",equalTo(true));


    }
    @Test
    @Description("Логин пользователя с неверным E-mail")
    public void loginUserWithWrongEmail(){
        user = new User("Egor","egor2@yandex.ru","12345");
        ValidatableResponse response = UserClient.createUser(user);
        token = response.extract().path("accessToken");
        User userWithIncorrectEmail = new User("asddfa", user.getPassword());
        ValidatableResponse loginUserWithIncorrectEmail = UserClient.loginUser(userWithIncorrectEmail);
        loginUserWithIncorrectEmail
                .statusCode(401)
                .body("message",equalTo("email or password are incorrect"));
    }
    @Test
    @Description("Логин пользователя с неверным паролем")
    public void loginUserWithWrongPassword(){
        user = new User("Egor","vanivanchich@yandex.ru","12345");
        ValidatableResponse response = UserClient.createUser(user);
        token = response.extract().path("accessToken");
        User userWithIncorrectPassword = new User(user.getEmail(), "asdsfgh");
        ValidatableResponse loginUserWithIncorrectEmail = UserClient.loginUser(userWithIncorrectPassword);
        loginUserWithIncorrectEmail
                .statusCode(401)
                .body("message",equalTo("email or password are incorrect"));
    }


    @After
    @Description("Удаление пользователя")
    public void shutDown(){
        UserClient.deleteUser(token);
    }

}
