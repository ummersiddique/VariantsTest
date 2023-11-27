import com.android.build.gradle.internal.scope.publishBuildArtifacts

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.library.variants.demolib"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    val sdkVersion = "v2.1.2.3"
    buildTypes {
        getByName("debug") {
            buildConfigField(type = "String", name = "SDK_VERSION", value = "\"${sdkVersion}.debug\"")
        }

//        create("dev") {
//            buildConfigField(type = "String", name = "SDK_VERSION", value = "\"${sdkVersion}.dev\"")
//        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(type = "String", name = "SDK_VERSION", value = "\"$sdkVersion.release\"")
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    flavorDimensions += listOf("sdk")
//    productFlavors {
//        create("dev") {
//            dimension = "sdk"
//            versionNameSuffix = ".dev"
//        }
//        create("live") {
//            dimension = "sdk"
//            versionNameSuffix = ".live"
//        }
//    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

afterEvaluate {

    publishing {
        publications {
            // Creates a Maven publication called "debug".
            create<MavenPublication>("variants-debug") {
                from(components.getByName("debug"))
            }
            create<MavenPublication>("variants-release") {
                from(components.getByName("release"))
            }

            // Creates a Maven publication called "release".
            create<MavenPublication>("release") {
                // Applies the component for the release build variant.
                from(components.getByName("release"))
            }
        }

        repositories {
            maven {
                name = "myRepo"
                url = uri(layout.buildDirectory.dir("repo"))
            }
        }
    }
}