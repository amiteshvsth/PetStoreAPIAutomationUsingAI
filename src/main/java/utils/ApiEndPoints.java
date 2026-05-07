package utils;

public class ApiEndPoints {
    public static final String PETSTORE_BASE_URL = "https://petstore.swagger.io/v2";

    // Pet endpoints
    public static final String PET_POST_CREATE_PET = "/pet";
    public static final String PET_PUT_UPDATE_PET = "/pet";
    public static final String PET_GET_FIND_BY_STATUS = "/pet/findByStatus";
    public static final String PET_GET_FIND_BY_TAGS = "/pet/findByTags";
    public static final String PET_GET_PET_BY_ID = "/pet/{petId}";
    public static final String PET_POST_UPDATE_PET_FORM = "/pet/{petId}";
    public static final String PET_DELETE_PET = "/pet/{petId}";
    public static final String PET_POST_UPLOAD_IMAGE = "/pet/{petId}/uploadImage";

    // Store endpoints
    public static final String STORE_GET_INVENTORY = "/store/inventory";
    public static final String STORE_POST_ORDER = "/store/order";
    public static final String STORE_GET_ORDER_BY_ID = "/store/order/{orderId}";
    public static final String STORE_DELETE_ORDER = "/store/order/{orderId}";

    // User endpoints
    public static final String USER_POST_CREATE_USER = "/user";
    public static final String USER_POST_CREATE_WITH_ARRAY = "/user/createWithArray";
    public static final String USER_POST_CREATE_WITH_LIST = "/user/createWithList";
    public static final String USER_GET_LOGIN = "/user/login";
    public static final String USER_GET_LOGOUT = "/user/logout";
    public static final String USER_GET_USER_BY_NAME = "/user/{username}";
    public static final String USER_PUT_UPDATE_USER = "/user/{username}";
    public static final String USER_DELETE_USER = "/user/{username}";
}

// ModuleName_MethodType_APIName