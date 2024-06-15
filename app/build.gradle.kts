plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsKotlinAndroid)
  id("com.google.devtools.ksp")
  id("androidx.navigation.safeargs.kotlin")
}

android {
  namespace = "it.unibo.android.lab.newsapp"
  compileSdk = 34

  defaultConfig {
    applicationId = "it.unibo.android.lab.newsapp"
    minSdk = 28
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    viewBinding = true
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

  // Architectural Components
  implementation (libs.androidx.lifecycle.viewmodel.ktx)
  // Room
  implementation (libs.androidx.room.runtime)
  ksp (libs.androidx.room.compiler)
  // Kotlin Extensions and Coroutines support for Room
  implementation (libs.androidx.room.ktx)
  // Coroutines
  implementation (libs.kotlinx.coroutines.core)
  implementation (libs.kotlinx.coroutines.android)
  // Coroutine Lifecycle Scopes
  implementation (libs.androidx.lifecycle.viewmodel.ktx)
  implementation (libs.androidx.lifecycle.runtime.ktx)
  // Retrofit
  implementation (libs.retrofit)
  implementation (libs.converter.gson)
  implementation (libs.logging.interceptor)
  // Navigation Components
  implementation (libs.androidx.navigation.fragment.ktx)
  implementation (libs.androidx.navigation.ui.ktx)
  // Glide
  implementation (libs.glide)
  ksp (libs.compiler)
}