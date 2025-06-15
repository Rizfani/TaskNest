plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    buildFeatures {
        viewBinding=true
        dataBinding=true
    }
    namespace = "com.dicoding.latihan_praktikum_10"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dicoding.latihan_praktikum_10"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:33.15.0"))
    implementation(platform("com.google.firebase:firebase-auth:23.2.1"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-database")

    // Google Sign In (yang lama, sesuai kode kamu)
    implementation("com.google.android.gms:play-services-auth:21.0.0")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment:2.7.5")
    implementation("androidx.navigation:navigation-ui:2.7.5")

    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // Lifecycle ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

    // Retrofit + Gson Converter
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Room Database
    implementation("androidx.room:room-runtime:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.5.2")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("com.google.android.material:material:1.11.0")
    implementation ("com.google.firebase:firebase-firestore:24.10.3")
    implementation ("com.google.android.material:material:1.11.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("com.google.firebase:firebase-auth:22.3.0")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")

    implementation("com.google.firebase:firebase-firestore-ktx:24.10.0")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    // Unit Test
    testImplementation ("junit:junit:4.13.2")

// Instrumented Test
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

// Untuk ActivityScenarioRule
    androidTestImplementation ("androidx.test:rules:1.5.0")




}