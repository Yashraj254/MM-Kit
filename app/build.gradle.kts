@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("mmkit.android.application")
    id("mmkit.android.application.compose")
    id("mmkit.android.hilt")
}

android {
    namespace = "com.yashraj.mmkit"
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":music:music_navigations"))
    implementation(project(":music:music_data"))
    implementation(project(":music:music_domain"))
    implementation(project(":music:music_presentation"))
    implementation(project(":core:di"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.bundles.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)

    implementation(libs.lottie.compose)
    implementation(libs.accompanist.permissions)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.timber)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)
}
