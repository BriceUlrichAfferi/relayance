package com.kirabium.relayance

import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.service.CustomerFakeApi
import com.kirabium.relayance.service.CustomerRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.`when`

@ExtendWith(MockitoExtension::class) // Extension for Mockito
class CustomerRepositoryTest {

    private lateinit var customerRepository: CustomerRepository

    @Mock
    private lateinit var customerFakeApi: CustomerFakeApi

    @BeforeEach
    fun setUp() {
        // Create an instance of CustomerRepository with mocked CustomerFakeApi
        customerRepository = CustomerRepository(customerFakeApi)
    }

    @Test
    fun `test getCustomers should return list of customers`() = runBlocking {
        // Given
        val expectedCustomers = listOf(
            Customer(1, "Alice Wonderland", "alice@example.com", DummyData.generateDate(12)),
            Customer(2, "Bob Builder", "bob@example.com", DummyData.generateDate(6)),
            Customer(3, "Charlie Chocolate", "charlie@example.com", DummyData.generateDate(3)),
            Customer(4, "Diana Dream", "diana@example.com", DummyData.generateDate(1)),
            Customer(5, "Evan Escape", "evan@example.com", DummyData.generateDate(0))
        )

        // Using Mockito's standard `when` instead of `whenever`
        `when`(customerFakeApi.getCustomer()).thenReturn(expectedCustomers)

        // When
        val customers = customerRepository.getCustomers()

        // Then
        assertEquals(expectedCustomers, customers)
    }

    @Test
    fun `test addCustomer should return updated list of customers`() = runBlocking {
        // Given
        val newCustomer = Customer(0, "New Customer", "new@example.com", DummyData.generateDate(7))
        val expectedCustomers = listOf(
            Customer(1, "Alice Wonderland", "alice@example.com", DummyData.generateDate(12)),
            Customer(2, "Bob Builder", "bob@example.com", DummyData.generateDate(6)),
            Customer(3, "Charlie Chocolate", "charlie@example.com", DummyData.generateDate(3)),
            Customer(4, "Diana Dream", "diana@example.com", DummyData.generateDate(1)),
            Customer(5, "Evan Escape", "evan@example.com", DummyData.generateDate(0)),
            Customer(6, "New Customer", "new@example.com", DummyData.generateDate(7)) // New customer with assigned ID
        )

        // Using Mockito's standard `when` instead of `whenever`
        `when`(customerFakeApi.addCustomer(newCustomer)).thenReturn(newCustomer)
        `when`(customerFakeApi.getCustomer()).thenReturn(expectedCustomers)

        // When
        val customers = customerRepository.addCustomer(newCustomer)

        // Then
        assertEquals(expectedCustomers, customers)
    }
}
