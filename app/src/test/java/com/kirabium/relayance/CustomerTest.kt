package com.kirabium.relayance

import com.kirabium.relayance.domain.model.Customer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Calendar
import java.util.Date

class CustomerTest {
    private  lateinit var customer: Customer

    @BeforeEach
    fun setUp(){

        customer = Customer(
            id = 1,
            name = "Jody",
            email = "jody@gmail.com",
            createdAt = Date()

        )
    }

    @Test
    fun `isNewCustomer should return true for customers created within last 3 months`() {
        // Given
        assertThat(customer.isNewCustomer()).isTrue()
    }

    @Test
    fun `isNewCustomer should return true for customers created more than 3 months`() {
        //Given
        val oldDate = Calendar.getInstance().apply {
            add(Calendar.MONTH, -4)
        }.time

        //When
        customer = Customer(
            id = 2,
            name = "Jody",
            email = "essai2@yahoo.com",
            createdAt = oldDate
        )
        //Then

        assertThat(customer.isNewCustomer()).isFalse()

    }

    @Test
    fun `isNewCustomer should return true for customers created exactly 3 months ago`(){

        //When
        val exactDate = Calendar.getInstance().apply {
            add(Calendar.MONTH, -3)
        }.time

        //Given, That
        customer = Customer(
            id = 3,
            name = "Domi",
            email = "domi@yahoo.fr",
            createdAt = exactDate
        )
        assertThat(customer.isNewCustomer()).isTrue()
    }


}