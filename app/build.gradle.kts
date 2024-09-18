plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.arshia.moviedatademo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.arshia.moviedatademo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.compose.bom)
    androidTestImplementation(libs.androidx.compose.bom)
    implementation(libs.material3)
    implementation(libs.ui)


    implementation(libs.androidx.animation)

    implementation(libs.ui.tooling.preview)
    debugImplementation(libs.ui.tooling)

    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation("androidx.compose.material3:material3-window-size-class")

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.runtime.rxjava2)


    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)


    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)

    implementation(libs.logging.interceptor)


    implementation(libs.androidx.paging.runtime)

    testImplementation(libs.androidx.paging.common)

    implementation(libs.androidx.paging.rxjava2)

    implementation(libs.androidx.paging.rxjava3)

    implementation(libs.androidx.paging.guava)

    implementation(libs.androidx.paging.compose)


    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)


    implementation(libs.coil.compose)

    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    implementation(libs.compose)

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)

    kapt(libs.androidx.room.compiler)

    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.room.rxjava2)

    implementation(libs.androidx.room.rxjava3)

    implementation(libs.androidx.room.guava)

    testImplementation(libs.androidx.room.testing)

    implementation(libs.androidx.room.paging)

    implementation(libs.core.splashscreen)
}

kapt {
    correctErrorTypes = true
}