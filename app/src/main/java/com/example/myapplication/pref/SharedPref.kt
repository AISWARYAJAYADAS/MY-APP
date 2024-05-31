package com.example.myapplication.pref

import android.content.SharedPreferences
import com.example.myapplication.utils.NetworkConstants
import com.example.myapplication.utils.PrefConstants.ACCESS_TOKEN
import com.example.myapplication.utils.PrefConstants.CONFIGURATION
import com.example.myapplication.utils.PrefConstants.DEVICE_ID
import com.example.myapplication.utils.PrefConstants.EMAIL
import com.example.myapplication.utils.PrefConstants.FCM_TOKEN
import com.example.myapplication.utils.PrefConstants.IS_CONFIG_UPDATED
import com.example.myapplication.utils.PrefConstants.IS_FCM_TOKEN_ADDED
import com.example.myapplication.utils.PrefConstants.IS_INITIAL_PROFILE_UPDATED
import com.example.myapplication.utils.PrefConstants.PR0FILE_INFO
import com.example.myapplication.utils.PrefConstants.REFRESH_TOKEN
import com.example.myapplication.utils.PrefConstants.ST_SEARCH_HISTORY
import com.example.myapplication.utils.PrefConstants.URL_CONFIG
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref @Inject constructor(private val sharedPref: SharedPreferences) {

    fun clearTokens() {
        sharedPref.edit().remove(ACCESS_TOKEN).remove(REFRESH_TOKEN).apply()
    }

    /**
     * Remove all user specific data in Shared preference.
     * */
    fun clear() {
        sharedPref.edit().run {
            remove(DEVICE_ID)
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            remove(PR0FILE_INFO)
            remove(IS_INITIAL_PROFILE_UPDATED)
            remove(IS_FCM_TOKEN_ADDED)
        }.apply()
    }

    //A map which contain headers for the API request.
    val headers
        get() = HashMap<String, String>().apply {
            this[NetworkConstants.AUTHORIZATION] = NetworkConstants.BEARER + getAccessToken()
        }

    /**
     * Save authToken in Shared preference.
     * */
    fun saveRefreshToken(token: String) {
        put(REFRESH_TOKEN, token)
    }

    /**
     * Get authToken from Shared preference.
     * */
    fun getRefreshToken(): String {
        return get(REFRESH_TOKEN, String::class.java)
    }

    /**
     * Save authToken in Shared preference.
     * */
    fun saveAccessToken(token: String) {
        put(ACCESS_TOKEN, token)
    }

    /**
     * Get authToken from Shared preference.
     * */
    fun getAccessToken(): String {
        return get(ACCESS_TOKEN, String::class.java)
    }


    /**
     * Get fcm token from Shared preference.
     * */
    fun getFcmToken(): String {
        return get(FCM_TOKEN, String::class.java)
    }

    /**
     * Save fcm token in Shared preference.
     * */
    fun saveFcmToken(fcmToken: String) {
        put(FCM_TOKEN, fcmToken)
    }


    /**
     * Save Configuration API response in Shared preference.
     * */
    fun saveConfiguration(configListJson: String) {
        put(CONFIGURATION, configListJson)
    }

    /**
     * Get Configuration API response from Shared preference.
     * */
    fun getConfiguration(): String {
        return get(CONFIGURATION, String::class.java)
    }


    /**
     * Save configuration data updated status in Shared preference.
     * @param status :  true, if config API call was success.
     *                  false, if config API call was failed.
     * */
    fun saveConfigUpdatedStatus(status: Boolean) {
        put(IS_CONFIG_UPDATED, status)
    }

    /**
     * Save fcm token in Shared preference.
     * */
    fun saveDeviceId(deviceId: String) {
        put(DEVICE_ID, deviceId)
    }

    /**
     * Get device id from Shared preference.
     * */
    fun getDeviceId(): String {
        return get(DEVICE_ID, String::class.java)
    }

    /**
     * Get configuration data updated status from Shared preference.
     * */
    fun isConfigUpdated(): Boolean {
        return get(IS_CONFIG_UPDATED, Boolean::class.java)
    }

    /**
     * Saves the status of whether the initial profile has been updated or not in the shared preferences.
     * It takes a boolean value as an argument, and stores it with the key "IS_INITIAL_PROFILE_UPDATED".
     * */
    fun saveInitialProfileUpdatedStatus(status: Boolean) {
        put(IS_INITIAL_PROFILE_UPDATED, status)
    }

    /**
     * This method returns a boolean value representing whether the user has completed the initial profile setup or not.
     * @return a boolean value indicating whether the initial profile setup is completed or not
     */
    fun isInitialProfileUpdated(): Boolean {
        return get(IS_INITIAL_PROFILE_UPDATED, Boolean::class.java)
    }

    /**
     * Get profile API response from Shared preference.
     * */
    fun getProfileInfo(): String {
        return get(PR0FILE_INFO, String::class.java)
    }

    /**
     * Save profile API response in Shared preference.
     * */
    fun saveProfileInfo(profileResponse: String) {
        put(PR0FILE_INFO, profileResponse)
    }

    /**
     * Returns the email of the user from the shared preferences.
     *  */
    fun getUserEmail(): String {
        return get(EMAIL, String::class.java)
    }

    /**
     * Save the email of the user to the shared preferences.
     *  */
    fun saveUserEmail(email: String) {
        put(EMAIL, email)
    }

    /**
     * Get staff search history from Shared preference.
     * */
    fun getStSearchHistory(): String {
        return get(ST_SEARCH_HISTORY, String::class.java)
    }

    /**
     * Save staff search history in Shared preference.
     * */
    fun saveStSearchHistory(history: String) {
        put(ST_SEARCH_HISTORY, history)
    }

    /**
     * Save url config API response in Shared preference.
     * */
    fun saveUrlConfigInfo(urlConfigResponse: String) {
        put(URL_CONFIG, urlConfigResponse)
    }

    /**
     * Get  url config API response from Shared preference.
     * */
    fun getUrlConfigInfo(): String {
        return get(URL_CONFIG, String::class.java)
    }

    fun getIsFCMTokenAdded(): Boolean {
        return get(IS_FCM_TOKEN_ADDED, Boolean::class.java)
    }

    fun saveIsFCMTokenAdded(isAdded: Boolean) {
        put(IS_FCM_TOKEN_ADDED, isAdded)
    }

    /**
     * Gets the value of the specified key from the shared preferences and converts it to the specified type.
     * @param key The key of the value to retrieve from the shared preferences.
     * @param clazz The class of the value to retrieve from the shared preferences.
     * @return The value retrieved from the shared preferences.
     */
    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class.java -> sharedPref.getLong(key, -1L)
            else -> null
        } as T


    /**
     * A generic function to save any data to the shared preferences
     * @param key The key to associate with the data
     * @param data The data to save in the shared preferences
     */
    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }
}