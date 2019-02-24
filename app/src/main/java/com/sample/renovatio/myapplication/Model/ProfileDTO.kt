package com.sample.renovatio.myapplication.Model

data class ProfileDTO(
    val age: String,
    val alcohol: String,
    val basic_occupation: String,
    val blood_type: String,
    val description: String,
    val distance: String,
    val education_level: String,
    val height: String,
    val is_verify_mobile: Boolean,
    val location: String,
    val name: String,
    val profile_images: List<String>,
    val religion: String,
    val smoke: String
) {
    companion object {
        val TAG = ProfileDTO::class.java.simpleName
    }
}