package Helpers;

import groovyjarjarasm.asm.commons.StaticInitMerger;
import io.restassured.RestAssured;

public class Constants {
    public static final String CREATE_USER = "/api/auth/register";
    public static final String LOGIN_USER = "/api/auth/login";
    public static final String EDIT_USER = "/api/auth/user";
    public  static final String LOG_OUT = "/api/auth/logout";

    public static final String DELETE_USER = "/api/auth/user";

    public static final String GET_USER_INFO = "/api/auth/user";

    public static final String CREATE_ORDER = "/api/orders";

    public static final String LIST_OF_INGREDIENTS = "/api/ingredients";

    public static final String LIST_OF_ORDERS = "/api/orders";


}
