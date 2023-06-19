package market.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringContainer {
    public static final String EMPTY_PARAM = "";
    public static final String USER_DTO = "userDto";
    public static final String ADDRESS_ID = "addressId";
    public static final String ERRORS = "errors";
    public static final String COUNTRY = "country";
    public static final String CITY = "city";
    public static final String STREET = "street";
    public static final String HOUSE = "house";
    public static final String FLAT = "flat";
    public static final String SIZE_MODERATE_COMMENTS = "sizeModerateComments";
    public static final String ACCESSED_COMMENTS = "accessedComments";
    public static final String MODERATE_COMMENTS = "moderateComments";
    public static final String ACCESS = "access";
    public static final String COMMENTS = "comments";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String ROLES = "roles";
    public static final String GENDERS = "genders";
    public static final String PASSPORT_NO = "passportNo";
    public static final String EMAIL = "email";
    public static final String ROLE = "role";
    public static final String GENDER = "gender";
    public static final String USER_COMMENTS = "userComments";
    public static final String PRODUCTS_IN_BASKET = "productsInBasket";
    public static final String MESSAGE_IF_SEARCHED_PRODUCTS_EMPTY = "messageIfSearchedProductsEmpty";
    public static final String MESSAGE_IF_SEARCHED_PRODUCTS_EMPTY_TEXT = "There is no product according to your requests";
    public static final String ADDRESSES = "addresses";
    public static final String ID = "id";
    public static final String PRODUCTS = "products";
    public static final String FAVOURITES = "favourites";
    public static final String SEARCHED_PRODUCTS = "searchedProducts";
    public static final String CREATE_USER_PRODUCT_DTO = "createUserProductDto";
    public static final String NEW_LOGIN = "newName";
    public static final String NEW_EMAIL = "newEmail";
    public static final String NEW_PASSPORT_NO = "newPassportNo";
    public static final String NEW_PASSWORD = "newPassword";
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String MONEY = "money";
    public static final String PAGE = "page";
    public static final String ZERO = "0";
    public static final String MODELS = "models";
    public static final String COUNTRIES = "countries";
    public static final String BRANDS = "brands";
    public static final String COLORS = "colors";
    public static final String MODEL = "model";
    public static final String BRAND = "brand";
    public static final String COLOR = "color";
    public static final String MIN_PRICE = "minPrice";
    public static final String MAX_PRICE = "maxPrice";
    public static final String COUNT = "count";
    public static final String COST = "cost";
    public static final String BALANCE = "balance";
    public static final String BALANCE_MESSAGE = "Balance might not be less 0. ";
    public static final String COUNTRY_IS_NULL = "Country is null. ";
    public static final String CITY_IS_NULL = "City is null. ";
    public static final String STREET_IS_NULL = "Street is null. ";
    public static final String HOUSE_IS_NULL = "House is null. ";
    public static final String LOGIN = "login";
    public static final String LOGIN_MESSAGE = "Password or Login is wrong. ";
    public static final String EMAIL_IS_WRONG = "This email is wrong. ";
    public static final String EMAIL_IS_USED = "This email is already used. ";
    public static final String REGEX_FOR_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String NAME_IS_NULL = "Name is null. ";
    public static final String LOGIN_IS_USED = "This login is already used. ";
    public static final String PASSPORT_IS_INVALID = "Passport number is invalid. ";
    public static final String REGEX_FOR_PASSPORT = "^[a-zA-Z]{2}\\d{6}$";
    public static final String PASSWORD_IS_WRONG = "Password is wrong. ";
    public static final String PASSWORD_IS_INVALID = "Password is simple." +
                                                     " The password must be at least 8 characters " +
                                                     "long, contain at least 1 uppercase letter, 1 lowercase letter," +
                                                     " and EITHER a special character OR a number. ";
    public static final String REGEX_FOR_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}$";
    public static final String GENDER_IS_INVALID = "Gender is invalid. ";
    public static final String ROLE_IS_INVALID = "Role is invalid. ";
    public static final String BRAND_ENTERED_INVALID = "There is no entered brand. ";
    public static final String MODEL_ENTERED_INVALID = "There is no entered model. ";
    public static final String COLOR_ENTERED_INVALID = "There is no entered color. ";
    public static final String COUNTRY_ENTERED_INVALID = "There is no entered country. ";
    public static final String MONEY_LESS_ENTERED_INVALID = "Money might not be less 0. ";
    public static final String COUNT_ENTERED_INVALID = "Count might not be less 1. ";
    public static final String INVALID = "invalid.";
    public static final String STATUS = "status";

    public static final String SPRING = "spring";
    public static final String USER = "user";
    public static final String EXPRESSION_CREATE_COMMENT = "java(userRepository.findById(Long.valueOf(createCommentDto.getUserId())).orElse(null))";
    public static final String EXPRESSION_CREATE_UPDATE_ADDRESS = "java(userRepository.findById(Long.valueOf(createUpdateAddressDto.getUserId())).orElse(null))";
    public static final String EXPRESSION_CREATE_ADDRESS = "java(userRepository.findById(Long.valueOf(createAddressDto.getUserId())).orElse(null))";
    public static final String PRICE_QUERIES = "priceQueries";
    public static final String CHEAP_FIRST = "Cheap first";
    public static final String REACH_FIRST = "Reach first";
    public static final String SELECTED_PRICE_QUERY = "selectedPriceQuery";
    public static final String STORAGE_COUNT = "storageCount";
    public static final String PAGE_FB = "pageFB";
    public static final String ORDER = "order";
    public static final String PAGE_OR = "pageOr";
    public static final String LACK_OF_MONEY_INVALID = "Insufficient funds to pay.";
    public static final String MESSAGE = "message";
    public static final String AFTER_PAY_MESSAGE = "Your order has been sent for moderating.";
    public static final String PROMO_CODE = "promoCode";
    public static final String INCORRECT_PROMO_CODE = "This promo code does not exist.";
    public static final String DISCOUNTED_ORDER = "discountedOrder";
    public static final String FIRST_ORDER_PROMO_CODE = "FIRST";
    public static final String INCORRECT_FIRST_ORDER = "You already have orders. ";
    public static final String FAVOURITE_PRODUCTS = "favouriteProducts";
    public static final String PAGE_F = "pageF";
}
