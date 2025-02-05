package com.kirabium.relayance

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.repository.CustomerRepository
import com.kirabium.relayance.viewmodels.CustomerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Calendar

@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
class CustomerViewModelTest {

    @Mock
    private lateinit var customerRepository: CustomerRepository

    private lateinit var viewModel: CustomerViewModel
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CustomerViewModel(customerRepository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `loadCustomers should populate customers when customers are found`() = testScope.runBlockingTest {
        // Given
        val testCustomers = listOf(
            Customer(1, "John Doe", "john@example.com", Calendar.getInstance().time),
            Customer(2, "Jane Doe", "jane@example.com", Calendar.getInstance().time)
        )
        `when`(customerRepository.getCustomers()).thenReturn(testCustomers)

        // When
        viewModel.loadCustomers()

        // Then
        assertEquals(testCustomers, viewModel.customers.value)
    }

    @Test
    fun `loadCustomers should return empty list when no customers are found`() = testScope.runBlockingTest {
        // Given
        `when`(customerRepository.getCustomers()).thenReturn(emptyList())

        // When
        viewModel.loadCustomers()

        // Then
        assertEquals(emptyList<Customer>(), viewModel.customers.value)
    }

    @Test
    fun `addCustomer should refresh customers list when a new customer is added`() = testScope.runBlockingTest {
        // Given
        val newCustomer = Customer(3, "Sam Smith", "sam@example.com", Calendar.getInstance().time)
        val existingCustomers = listOf(
            Customer(1, "John Doe", "john@example.com", Calendar.getInstance().time),
            Customer(2, "Jane Doe", "jane@example.com", Calendar.getInstance().time)
        )

        // Mock repository methods
        `when`(customerRepository.getCustomers()).thenReturn(existingCustomers)

        // When we add a customer
        viewModel.loadCustomers() // First load existing customers
        viewModel.addCustomer(newCustomer) // Now add the new customer

        // Then we expect the updated list including the new customer
        val expectedCustomers = existingCustomers + newCustomer
        assertEquals(expectedCustomers, viewModel.customers.value)
    }

}