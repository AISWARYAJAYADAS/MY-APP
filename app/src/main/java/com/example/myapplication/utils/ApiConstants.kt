package com.example.myapplication.utils


object NetworkConstants {
    /**
     * Constants for the OkHttpClient
     * used to set the request read and connection timeout in minutes
     * for each request
     */
    const val READ_TIMEOUT: Long = 3
    const val CONNECTION_TIMEOUT: Long = 3
    const val WRITE_TIMEOUT: Long = 3
    const val AUTHORIZATION = "Authorization"
    const val BEARER = "Bearer "
    const val MAXIMUM_API_RETRY_COUNT = 3
    const val STATUS_CODE = "statusCode"
    const val STATUS_MESSAGE = "message"
    const val ERROR_MESSAGE_FROM_API = "errorMessage"
    const val ANDROID: String = "android"
    const val HTTP = "http"
    const val HTTPS = "https"
    const val ID = "id"
    const val COMMENT_ID = "commentId"
    const val CONTENT_TYPE = "Content-Type"
    const val POSTAL_CODE = "postalCode"
    const val ENTITY_TYPE = "entityType"
    const val MASTER_TAG_ID = "masterTagId"
    const val TAG_NAME = "tagName"
    const val LIMIT = "limit"
    const val SORT_BY = "sortBy"
    const val OFFSET = "offset"
    const val ITEM_CODE = "itemCode"
    const val COLOR_CODE = "colorCode"
    const val JAN_CODE = "code"
    const val COUNT = "count"
    const val BRAND_CODE = "brandCode"
    const val CATEGORY_CODE = "categoryCode"
    const val SUB_CATEGORY_CODE = "subCategoryCode"
    const val PRODUCT_NAME = "productName"
    const val POST_STATUS = "postStatus"
    const val POST_ID = "postId"
    const val REVIEW_PENDING = "reviewPending"

    const val ITEM_NAME = "productName"
    const val LISTING_ID = "listing_id"
    const val SEARCH_QUERY = "search_query"
    const val MESSAGE_ID = "messageId"
    const val ORDER_ID = "orderId"
    const val DEVICE_ID = "deviceId"
    const val CATEGORY = "category"
    const val IS_CUSTOM_BRAND = "isCustomBrand"
    const val TYPE = "type"
    const val SIZE_CODE = "sizeCode"
    const val GENRE_CODE = "genreCode"

}

object StatusCode {
    //const val NO_PERMISSION: Int = 1070 //todo need to change to correct status code
    //const val INACTIVE_USER: Int = 1060 //todo need to change to correct status code
    const val INVALID_REFRESH_TOKEN: Int = 1008

    const val AUTHENTICATION_ERROR: Int = 1003

    //const val ASCII_ERROR: Int = 1002
    const val T_ACCOUNT_LOGIN: Int = 1005

    const val INTERNAL_ERROR: Int = 1000 //as per staff board project
    const val IN_ACTIVE_USER: Int = 1012
    const val BLACK_LISTED_USER: Int = 1013

    const val LOGIN_BLOCKED: Int = 1011

    const val EMAIL_ALREADY_EXIST: Int = 1014
    const val INVALID_POSTAL_OR_PROVINCE: Int = 1055
    const val POST_ALREADY_DELETED: Int = 1059

}

object CreateListingStatus {
    const val STATUS_PUBLISHED = "PUBLISHED"
    const val STATUS_DRAFT= "DRAFT"
}

object CommonConstants {
    const val PASSWORD_MAX_LENGTH = 12
}


object ApiEndPoint {
    const val LOGIN = "v1/auth/login"
    const val REFRESH = "v1/auth/refresh"
    const val CONFIGURATION = "v1/configuration/option"
    const val URL_CONFIGURATION = "v1/configuration/url"
    const val GET_USER_DETAILS = "v1/user"
    const val LOGOUT = "v1/auth/logout"
    const val USER_IMAGE_UPDATE = "v1/user-image"
    const val USER_IMAGE_UPLOAD_SUCCESS = "v1/user-image/success"
    const val UPDATE_PROFILE_BASIC_INFO = "v1/user/basic"
    const val UPDATE_USER_DETAILED_INFO = "v1/user/detailed"
    const val UPDATE_USER_ADDRESS_INFO = "v1/user/address"
    const val GET_PROVINCE = "v1/configuration/province"
    const val GET_POSTAL_CODE_DETAILS = "v1/configuration/postal-code/{postalCode}"
    const val UPDATE_INITIAL_PROFILE_INFO = "v1/user/confirm-profile-info"
    const val GET_TAG = "v1/tag"
    const val GET_PRODUCT_DETAILS_WITH_SKU = "v1/catalog/product/{itemCode}"
    const val VALIDATE_BARCODE = "v1/catalog/barcode/{code}"
    const val PRODUCT_SEARCH_CONFIGURATION = "v1/catalog/configuration"
    const val PRODUCT_BASIC_INFO_CONFIGURATION = "v1/brand/list"
    const val BRAND_INFO = "v1/catalog/brand"
    const val PRODUCTS = "v1/catalog/product-name"
    const val POST_LISTING = "v1/user/post/list"
    const val DELETE_DRAFT = "v1/post/bulk-delete"
    const val PUBLISH_UN_PUBLISH_POST = "v1/post/{postId}/status"
    const val DELETE_PUBLISHED_POST = "v1/post/{id}"
    const val LISTING_MEDIA_UPLOAD = "v1/post-image"
    const val LISTING = "v1/post"
    const val POST_IMAGE_UPLOAD_SUCCESS = "v1/post-image/success"
    const val GET_POST_DETAIL = "v1/post/{postId}"
    const val GET_STRIPE_REG_URLS = "v1/user/seller-account-onboarding"
    const val GET_MENTIONS = "v1/post-comment/{postId}/users"
    const val GET_COMMENTS = "v1/post-comment/{postId}"
    const val ADD_COMMENT = "v1/post-comment"
    const val DELETE_COMMENT = "v1/post-comment/{id}"
    const val LIKE_OR_UNLIKE_COMMENT = "v1/post-comment/{commentId}/like"
    const val HIDE_COMMENT = "v1/post-comment/hide"
    const val UN_HIDE_COMMENT = "v1/post-comment/unhide"
    const val GET_TRANSACTION_DETAIL = "v1/shipments/{postId}/status"
    const val GET_TRANSACTION_DETAIL_BASED_ON_ORDER_ID = "v1/shipments/order/{orderId}/status"
    const val CREATE_DIRECT_MESSAGE = "v1/message"
    const val DELETE_DIRECT_MESSAGE = "v1/message/{messageId}"
    const val LIKE_UNLIKE_DIRECT_MESSAGE = "v1/message/{messageId}/like"
    const val PRE_SIGN_MESSAGE = "v1/message/image"
    const val GET_ALL_MESSAGES = "v1/message/list/{orderId}"

    const val GET_BARCODE_FOR_TRANSACTION = "v1/shipments/qr-code"
    const val GET_TRANSACTION_REVIEW = "v1/order/{orderId}/review"
    const val ORDER_CONFIRMATION = "v1/shipments/order-confirmation"
    const val GET_SHIPMENTS = "v1/configuration/shipment"
    const val UPDATE_ORDER_STATUS_TO_SHIPPED = "v1/shipments/status/shipped"
    const val NOTIFICATION_LISTING = "v1/user/notifications"

    const val ADD_SELLER_REVIEW = "v1/order/review"
    const val GET_NOTIFICATION_COUNT = "v1/user/notifications/unread"
    const val APP_VERSION_CHECK = "v1/configuration/app-version?appType=android"


    const val UPDATE_USER_BRAND = "v1/user/brand"
    const val UPDATE_NOTIFICATION_READ_STATUS = "v1/user/notifications/{id}/read"
    const val READ_ALL_NOTIFICATION = "v1/user/notifications/read"
    const val GET_NEWS_DETAILS = "v1/user/notifications/news/{id}"


    const val ADD_REMOVE_DEVICE = "v1/device-data/{deviceId}"
    const val GET_FOLLOWERS_COUNT = "v1/user/followers/count"
    const val RESET_SHIPPING_PACKAGE_DETAILS = "v1/shipments/{orderId}/package-size"
    const val PRODUCT_SIZE_LIST = "v1/size"
    const val VALIDATE_PRODUCT_INFO = "v1/post/valid-product-details"

}

/**
 * Constants used in logout broadcast receiver to distinguish the flow.
 */
object LogoutFlow {
    const val REFRESH_TOKEN_EXPIRED: Int = 1
    const val NORMAL_LOGOUT: Int = 2
    const val INACTIVE_USER: Int = 3
}



