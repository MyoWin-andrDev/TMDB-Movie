plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}
val a23bCx12A67: String by project
android {
    namespace = "com.learning.tmdb_movie"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.learning.tmdb_movie"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildFeatures {
            buildConfig = true
        }

        buildConfigField("String", "a23bCx12A67", "\"$a23bCx12A67\"")

    }



    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        viewBinding {
            enable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Retrofit Dependency
    implementation(libs.gson)
    implementation(libs.retrofit.v290)
    implementation(libs.converter.gson.v290)
    //ViewModel Dependency
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Glide for image loading
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    //Arrow
    implementation(platform(libs.arrow.stack))
    implementation(libs.arrow.core)
    //OKHTTP Interceptor(To Know Error On Network Calls)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.datastore.preferences)


}