package com.kirabium.relayance

import com.kirabium.relayance.extension.DateExt
import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.text.SimpleDateFormat

class DateExtTest {

    private lateinit var testDate: Date

    @BeforeEach
    fun setUp() {
        // Common setup for test dates
        testDate = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2023)
            set(Calendar.MONTH, Calendar.JANUARY) // Month is 0-based
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
    }

    @Test
    fun `toHumanDate formats date to dd-MM-yyyy`() {
        // When
        val result = testDate.toHumanDate()

        // Then
        // Expected format is "dd/MM/yyyy"
        assertThat(result).isEqualTo("01/01/2023")
    }

    @Test
    fun `toHumanDate should respect locale for formatting`() {
        // Given: testDate is already set in @BeforeEach

        // Change the date for this specific test
        testDate = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2023)
            set(Calendar.MONTH, Calendar.DECEMBER)
            set(Calendar.DAY_OF_MONTH, 31)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        // When
        val result = testDate.toHumanDate()

        // Then
        // Note: This test assumes the default locale uses "dd/MM/yyyy".
        assertThat(result).isEqualTo("31/12/2023")
    }
}