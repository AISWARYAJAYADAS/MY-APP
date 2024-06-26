package com.example.myapplication.utils

object INDEX {
    const val INDEX_0 = 0
    const val INDEX_1 = 1
    const val INDEX_2 = 2
}

object PrefConstants {
    const val PREF = "DOT_C_SHARED_PREF"
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"
    const val FCM_TOKEN = "fcm_token"
    const val CONFIGURATION = "configuration"
    const val IS_CONFIG_UPDATED = "is_config_updated"
    const val DEVICE_ID = "device_id"
    const val PR0FILE_INFO = "profile_info"
    const val IS_INITIAL_PROFILE_UPDATED = "is_initial_profile_updated"
    const val EMAIL = "email"
    const val ST_SEARCH_HISTORY = "st_search_history"
    const val URL_CONFIG = "url_configuration"
    const val IS_FCM_TOKEN_ADDED = "fcm_token_added"
}

object ListingStatus {
    const val DRAFT = "DRAFT"
    const val PUBLISHED = "PUBLISHED"
    const val ORDER_PLACED = "ORDER_PLACED"
    const val ORDER_COMPLETED = "ORDER_COMPLETED"
    const val ORDER_CANCELED = "ORDER_CANCELED"
}

object ListingSortType {
    const val NEWEST = "NEWEST"
    const val MOST_LIKED = "MOST_LIKED"
    const val OLDEST = "OLDEST"
    const val PV_COUNT = "VIEW_COUNT"
}

object AppConstants{
    const val API_LISTING_LIMIT = 20
}