package com.kirabium.relayance

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Calendar
import com.kirabium.relayance.data.DummyData

class DummyDataTest {

    private lateinit var dummyData: DummyData

    @BeforeEach
    fun setUp() {
        //Setting up for test
        dummyData = DummyData
    }

    @Test
    fun `generateDate should return a date months back from now`() {
        // Given
        val monthsBack = 5
        val now = Calendar.getInstance().time
        val expectedDate = Calendar.getInstance().apply { add(Calendar.MONTH, -monthsBack) }.time

        // When
        val result = dummyData.generateDate(monthsBack)

        // Then
        assertThat(result)
            .isBeforeOrEqualTo(now)
            .isAfterOrEqualTo(expectedDate)
        assertThat(result.time) // For Long comparison
            .isCloseTo(expectedDate.time, Offset.offset(24_000L * 60 * 60)) // Assert within a day's difference
    }

    @Test
    fun `generateDate with 0 months should return current date`() {
        // Given
        val monthsBack = 0
        val now = Calendar.getInstance().time

        // When
        val result = dummyData.generateDate(monthsBack)

        // Then
        assertThat(result) // For Date comparison, directly use long for delta
            .isCloseTo(now, 60L * 60 * 1000) // Assert within an hour's difference
    }
}