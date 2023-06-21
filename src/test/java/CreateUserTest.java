import Client.UserClient;
import Models.User;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
import io.qameta.allure.Description;



public class CreateUserTest {

   String token;


    @Test
    @Description("Создание уникального пользователя")
    public void createUniqueUserTest(){
        User user = new User("Ivan","petrov@yandex.ru","12345");
        ValidatableResponse createUser = UserClient.createUser(user);
        token = createUser.extract().path("accessToken");
        createUser.log().all()
                .statusCode(200)
                .assertThat()
                .body("success",equalTo(true));

    }
    @Test
    @Description("Повторное создание пользователя")
    public void createUserThatWasCreatedTest(){
        User user = new User("Ivan","ivanperov@yandex.ru","12345");
        ValidatableResponse createUser = UserClient.createUser(user);
        ValidatableResponse createSameUser = UserClient.createUser(user);
        createSameUser.log().all()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }
    @Test
    @Description("Создание пользователя без E-mail")
    public void createUserWithoutEmailTest(){
        User user = new User("Ivan",null,"12345");
        ValidatableResponse createUser = UserClient.createUser(user);
        createUser.log().all()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @Description("Создание поьзователя без пароля")
    public void createUserWithoutPasswordTest(){
        User user = new User("Ivan","ivarov@yandex.ru",null);
        ValidatableResponse createUser = UserClient.createUser(user);
        createUser.log().all()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }


    @After
    @Description("Удаление пользователя")
    public void shutDown() {
        if (token != null) {
            UserClient.deleteUser(token).log().all();
        }
    }
}
