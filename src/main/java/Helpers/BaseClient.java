package Helpers;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.filter.log.LogDetail;

public class BaseClient {
    public static RequestSpecification getSpec(){
        return new RequestSpecBuilder()
                .setBaseUri("https://stellarburgers.nomoreparties.site")
                .build();
    }
}
