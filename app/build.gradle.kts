plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.gurman.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.gurman.myapplication"
        minSdk = 26
        targetSdk = 35
        versionCode = 60
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
//        create("release") {
//            val keystorePropertiesFile = rootProject.file("signkey.properties")
//            val properties = Properties().apply {
//                load(FileInputStream(keystorePropertiesFile))
//            }
//            storeFile = File(properties.getProperty("storeFile"))
//            storePassword = properties.getProperty("storePassword")
//            keyPassword = properties.getProperty("keyPassword")
//            keyAlias = "warehouse"
//        }
//        create("sign_alpha") {
//            storeFile = File("./keystore/debug.keystore")
//            storePassword = "android"
//            keyAlias = "androiddebugkey"
//            keyPassword = "android"
//        }
    }

    buildTypes {
//        release {
//            isMinifyEnabled = true
//            isShrinkResources = true
//            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//            signingConfig = signingConfigs.getByName("release")
//        }
//        create("alpha") {
//            isMinifyEnabled = true
//            isShrinkResources = true
//            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//            signingConfig = signingConfigs.getByName("sign_alpha")
//            applicationIdSuffix = ".alpha"
//        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            applicationIdSuffix = ".debug"
        }
    }

    applicationVariants.configureEach {
        outputs.configureEach {
            if (buildType.isMinifyEnabled) {
                val outputName = "${project.rootDir.absolutePath}/_Releases"
                val outputFileName = "Gurman-${buildType.name}-$versionName($versionCode)"
                assembleProvider.get().doLast {
                    copy {
                        from(outputFile)
                        into(outputName)
                        rename {
                            "$outputFileName.apk"
                        }
                    }
                }
                mappingFileProvider.get().files.forEach { mappingFile ->
                    copy {
                        from(mappingFile)
                        into(outputName)
                        rename {
                            "${outputFileName}_mappindg.txt"
                        }
                    }
                }
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.15"
//    }
    kotlin {
        compilerOptions {
            freeCompilerArgs.add("-Xlambdas=class")
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
    implementation(libs.androidx.compose.foundation)

    implementation(libs.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.wear.compose.material)
    implementation(libs.androidx.wear.compose.material3)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
//    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.compose.ui.text)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("com.jakewharton.timber:timber:5.0.1")

    //Image
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

    implementation("io.coil-kt.coil3:coil-compose:3.2.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.2.0")
    implementation("io.coil-kt.coil3:coil-gif:3.2.0")

    // voyager
    val voyagerVersion = "1.1.0-beta02"
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-hilt:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-livedata:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")

    // Gson
    implementation(libs.gson)
}