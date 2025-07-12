package com.example.hnote.benchmark

import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.TraceSectionMetric

object BaselineProfileMetrics {

    @OptIn(ExperimentalMetricApi::class)
    val jitCompilationMetric = TraceSectionMetric("JIT Compiling %", label = "JIT compilation")

    @OptIn(ExperimentalMetricApi::class)
    val classInitMetric = TraceSectionMetric("L%/%;", label = "ClassInit")

    val startupTimingMetric = StartupTimingMetric()

    @OptIn(ExperimentalMetricApi::class)
    val allMetrics = listOf(startupTimingMetric, jitCompilationMetric, classInitMetric)
}