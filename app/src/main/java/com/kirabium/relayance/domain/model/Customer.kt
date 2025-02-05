package com.kirabium.relayance.domain.model

import java.util.Calendar
import java.util.Date

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Customer(
    val id: Int,
    val name: String,
    val email: String,
    val createdAt: Date
) : Parcelable {
    fun isNewCustomer(): Boolean {
        val today = Calendar.getInstance()
        val createdAtCalendar = Calendar.getInstance().apply {
            time = createdAt
        }
        today.add(Calendar.MONTH, -3)
        return !createdAtCalendar.before(today)
    }
}
