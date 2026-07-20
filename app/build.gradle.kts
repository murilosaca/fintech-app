plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Adiciona o plugin KAPT, essencial para o Room Database funcionar
    kotlin("kapt")
}

android {
    namespace = "com.seufintech.fintechapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.seufintech.fintechapp"
        minSdk = 28
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    // Habilita o ViewBinding, a forma correta e segura de usar os layouts XML
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // --- Dependências do Android Jetpack e UI (com nomes completos) ---
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")


    // --- Room Database (para salvar os dados permanentemente) ---
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    // Suporte a Coroutines com Room
    implementation("androidx.room:room-ktx:$roomVersion")


    // --- Testes (padrão) ---
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
