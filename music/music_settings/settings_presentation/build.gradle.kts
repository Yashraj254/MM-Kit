@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("mmkit.android.library")
    id("mmkit.android.library.compose")
    id("mmkit.android.hilt")
}

android {
    namespace = "com.yashraj.settings_presentation"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.compose.material.iconsExtended)

    implementation(libs.bundles.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)

    implementation(libs.accompanist.permissions)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.timber)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)
}
