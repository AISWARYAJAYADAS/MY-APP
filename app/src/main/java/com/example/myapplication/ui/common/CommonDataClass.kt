package com.example.myapplication.ui.common

data class ConfigApiResponse(
    val `data`: List<Config>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)

data class Config(
    val id: Int,
    val inputType: String,
    val labelEn: String,
    val labelJp: String,
    val options: List<Option>,
    val type: String
)

data class Option(
    val code: String,
    val id: Int,
    val labelEn: String,
    val labelJp: String,
    val value: String
)

/**
 * Data class representing a single item in a dropdown list.
 * @property itemCode The code representing the selected item in the dropdown list.
 * @property itemName The name of the selected item in the dropdown list.
 */
data class DropDownModel(
    val itemCode: String? = null,
    val itemName: String? = null,
)
