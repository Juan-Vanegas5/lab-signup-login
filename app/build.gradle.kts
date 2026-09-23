plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "co.edu.unipiloto.signuplogin"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "co.edu.unipiloto.signuplogin"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
}
