plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.movies"
    compileSdk = 35

    defaultConfig {
        buildFeatures { buildConfig = true }
        applicationId = "com.example.movies"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug{
            buildConfigField("String", "ApiKey", "KP_API_KEY")
        }
        release {
            buildConfigField("String", "ApiKey", "KP_API_KEY")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation (libs.retrofitConvector)
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation(libs.gson)
    implementation(libs.room)
    annotationProcessor(libs.room.annotation.processor)
    implementation(libs.room.rxjava3)
    implementation(libs.adapterRxjava3)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}