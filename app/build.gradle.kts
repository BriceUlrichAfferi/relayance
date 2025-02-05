import com.android.build.gradle.BaseExtension

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("jacoco")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
}
tasks.withType<Test> {
    extensions.configure(JacocoTaskExtension::class) {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
    useJUnitPlatform() // Configures Gradle to use JUnit Platform for running tests

}

android {
    namespace = "com.kirabium.relayance"
    compileSdk = 34

    testCoverage {
        version = "0.8.8"
    }

    defaultConfig {
        applicationId = "com.kirabium.relayance"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    //This Ensure that the resources folder is properly included in the build
    sourceSets {
        getByName("test") {
           resources.srcDirs("src/androidTest/resources/features")
        }
    }

}

val androidExtension = extensions.getByType<BaseExtension>()

val jacocoTestReport by tasks.registering(JacocoReport::class) {
    dependsOn("testDebugUnitTest", "createDebugCoverageReport")
    group = "Reporting"
    description = "Generate Jacoco coverage reports"

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val debugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug")
    val mainSrc = androidExtension.sourceSets.getByName("main").java.srcDirs

    classDirectories.setFrom(debugTree)
    sourceDirectories.setFrom(files(mainSrc))
    executionData.setFrom(fileTree(buildDir) {
        include("**/*.exec", "**/*.ec")
    })
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
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // DI (Hilt)
    implementation("com.google.dagger:hilt-android:2.55")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    androidTestImplementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")


    kapt("com.google.dagger:hilt-android-compiler:2.55")


    // JUnit 5 dependencies for unit tests
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")

    testImplementation("org.mockito:mockito-core:5.15.2")

    testImplementation("org.mockito:mockito-junit-jupiter:5.15.2")
    // Hilt Testing
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.55")

    testImplementation ("io.mockk:mockk:1.12.3")
    androidTestImplementation ("io.mockk:mockk-android:1.12.3")
    androidTestImplementation ("org.mockito:mockito-android:3.12.4")
    testImplementation("org.mockito:mockito-core:5.15.2")

    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    // AssertJ for assertions
    testImplementation("org.assertj:assertj-core:3.27.3")

    //Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.1") {
        exclude(group = "com.android.support", module = "appcompat-v7")
        exclude(group = "com.android.support", module = "support-v4")
        exclude(group = "com.android.support", module = "support-annotations")
        exclude(group = "com.android.support", module = "support-v13")
        exclude(group = "com.android.support", module = "recyclerview-v7")
    }
    // Android Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

   /* // Cucumber dependencies for JUnit 5
    implementation("io.cucumber:cucumber-java:7.20.1")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.21.0") // For JUnit 5
    androidTestImplementation("io.cucumber:cucumber-android:7.18.1")
    testImplementation("io.cucumber:cucumber-junit:7.21.0")*/

    androidTestImplementation("io.cucumber:cucumber-android:7.18.1")
    androidTestImplementation("io.cucumber:cucumber-junit:7.21.0") // JUnit 4 version
    androidTestImplementation("io.cucumber:cucumber-java:7.21.0")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}