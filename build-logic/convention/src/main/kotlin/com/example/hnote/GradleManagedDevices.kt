package com.example.hnote

import com.android.build.api.dsl.CommonExtension
import org.gradle.kotlin.dsl.invoke

internal fun configureGradleManagedDevices(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    val pixel9 = DeviceConfig(
        device = "Pixel 9",
        apiLevel = 36,
        systemImageSource = "google"
    )
    val pixelTablet = DeviceConfig(
        device = "Pixel Tablet",
        apiLevel = 36,
        systemImageSource = "google"
    )

    commonExtension.testOptions {
        managedDevices {
            localDevices {
                create(pixel9.taskName) {
                    device = pixel9.device
                    apiLevel = pixel9.apiLevel
                    systemImageSource = pixel9.systemImageSource
                }
                create(pixelTablet.taskName) {
                    device = pixelTablet.device
                    apiLevel = pixelTablet.apiLevel
                    systemImageSource = pixelTablet.systemImageSource
                }
            }
        }
    }
}

private data class DeviceConfig(
    val device: String,
    val apiLevel: Int,
    val systemImageSource: String,
) {
    val taskName = buildString {
        append(device.lowercase().replace(" ", ""))
        append("api")
        append(apiLevel.toString())
        append(systemImageSource.replace("-", ""))
    }
}