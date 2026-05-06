package utils;

public class ApiEndPoints {
    public static final String PETSTORE_BASE_URL = "https://petstore.swagger.io/v2";

    // Pet endpoints
    public static final String PET_BASE = "/pet";
    public static final String ADD_PET = "/pet";
    public static final String UPDATE_PET = "/pet";
    public static final String GET_PET = "/pet/{petId}";
    public static final String DELETE_PET = "/pet/{petId}";
    public static final String FIND_PETS_BY_STATUS = "/pet/findByStatus";
    public static final String FIND_PETS_BY_TAGS = "/pet/findByTags";
    public static final String UPDATE_PET_FORM = "/pet/{petId}";
    public static final String UPLOAD_IMAGE = "/pet/{petId}/uploadImage";
}

// ModuleName_MethodType_APIName