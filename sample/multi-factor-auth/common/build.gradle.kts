@file:OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
import org.jetbrains.compose.compose

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.compose.plugin.get().pluginId)
    alias(libs.plugins.compose.compiler)
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
    alias(libs.plugins.kotlinx.plugin.serialization)
}

group = "io.github.jan.supabase"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    jvm("desktop") {
        jvmToolchain(8)
    }
    js(IR) {
        browser()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.materialIconsExtended)
                api(libs.bundles.supabase)
                api(libs.koin.core)
            }
        }
        val nonJsMain by creating {
            dependencies {
                api(libs.ktor.cio)
            }
        }
        val androidMain by getting {
            dependsOn(nonJsMain)
            dependencies {
                api(libs.androidx.compat)
                api(libs.androidx.core)
                api(libs.koin.android)
                api(libs.androidx.lifecycle.viewmodel.ktx)
                api(libs.androidx.lifecycle.viewmodel.compose)
                api(libs.bundles.coil)
            }
        }
        val desktopMain by getting {
            dependsOn(nonJsMain)
            dependencies {
                api(compose.preview)
            }
        }
        val jsMain by getting {
            dependencies {
                api(libs.ktor.js)
            }
        }
    }
}

android {
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    namespace = "io.github.jan.supabase.common"
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
