plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildToolsVersion = "34.0.0"

    viewBinding {
        enable = true
    }
}

dependencies {
    implementation ("com.google.android.gms:play-services-maps:19.0.0")
    implementation ("androidx.core:core:1.13.1")
    implementation ("me.leolin:ShortcutBadger:1.1.22@aar")


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.recycler)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.converterGson)
    implementation(libs.firebase.database)
    implementation(libs.glide)
    implementation(libs.compose.theme.adapter)
    implementation(libs.google.material)
    implementation(libs.viewbinding)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}