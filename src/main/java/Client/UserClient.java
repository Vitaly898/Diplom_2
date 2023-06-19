package Client;
import Models.User;
import Helpers.Constants;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static Helpers.BaseClient.getSpec;
import static io.restassured.RestAssured.given;



public class UserClient {

    //Ручка для создания пользователя
    @Step("Cоздание пользователя")
    public static ValidatableResponse createUser(User user){
        return given()
                .spec(getSpec())
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(Constants.CREATE_USER)
                .then();
    };

    //Ручка логина пользователя
    @Step
    public static ValidatableResponse loginUser(User user){
        return given()
                .spec(getSpec())
                .header("Content-type","application/json")
                .and()
                .body(user)
                .when()
                .post(Constants.LOGIN_USER)
                .then();

    }

    //Ручка для удаления пользователя
    @Step
    public static ValidatableResponse deleteUser(String token){
        return given()
                .spec(getSpec())
                .header("Authorization",token)
                .when()
                .delete(Constants.DELETE_USER)
                .then();
    }
    //Ручка редактирования пользователя
    @Step
    public static ValidatableResponse editUser(User user, String token){
        return given()
                .spec(getSpec())
                .header("Authorization",token)
                .and()
                .body(user)
                .when()
                .patch(Constants.EDIT_USER)
                .then();
    }

    @Step
    public static ValidatableResponse getInfoAboutUser(String token){
        return given()
                .spec(getSpec())
                .header("Authorization",token)
                .when()
                .get(Constants.GET_USER_INFO)
                .then();
    }



}
