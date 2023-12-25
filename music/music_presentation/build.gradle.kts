@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("mmkit.android.library")
    id("mmkit.android.library.compose")
    id("mmkit.android.hilt")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.yashraj.music_presentation"
}

dependencies {
    implementation(project(":music:music_domain"))
    implementation(project(":core:utils"))
    implementation(project(":core:ui"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.compose.material.iconsExtended)

    implementation(libs.bundles.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.common)
    implementation(libs.androidx.media3.session)


    implementation(libs.github.skydoves.landscapist.glide)
    implementation(libs.coil)

    implementation(libs.accompanist.permissions)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.timber)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.ui.graphics)
    implementation(platform(libs.androidx.compose.bom))

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)
}
