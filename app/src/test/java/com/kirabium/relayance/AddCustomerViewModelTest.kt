package com.kirabium.relayance


import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.repository.CustomerRepository
import com.kirabium.relayance.viewmodels.AddCustomerViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import java.util.Date

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ensures @BeforeAll and @AfterAll run
class AddCustomerViewModelTest {

    private lateinit var viewModel: AddCustomerViewModel
    private lateinit var mockRepository: CustomerRepository

    private val testDispatcher = StandardTestDispatcher() // For coroutines

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Set Main dispatcher for tests
        mockRepository = mockk()
        viewModel = AddCustomerViewModel(mockRepository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain() // Reset Main dispatcher
    }

    @Test
    fun `isValidEmail should return true for valid email`() {
        assertThat(viewModel.isValidEmail("test@example.com")).isTrue()
    }

    @Test
    fun `isValidEmail should return false for invalid email`() {
        assertThat(viewModel.isValidEmail("invalid-email")).isFalse()
    }

    @Test
    fun `addCustomer with valid email should invoke repository addCustomer`() = runTest {
        // Given
        val customer = Customer(id = -1, name = "John Doe", email = "john@doe.com", createdAt = Date())
        val updatedCustomers = listOf(customer.copy(id = 1))
        coEvery { mockRepository.addCustomer(customer) } returns updatedCustomers

        // When
        var resultCustomers: List<Customer>? = null
        var errorMessage: String? = null
        viewModel.addCustomer(customer) { customers, error ->
            resultCustomers = customers
            errorMessage = error
        }

        testDispatcher.scheduler.advanceUntilIdle() // Ensures coroutine completes execution

        // Then
        assertEquals(updatedCustomers, resultCustomers)
        assertEquals(null, errorMessage)
    }

    @Test
    fun addCustomer_with_invalid_email_should_not_invoke_repository_and_should_return_error() = runTest {
        // Given
        val customer = Customer(id = -1, name = "John Doe", email = "invalid-email", createdAt = Date())

        // When
        var resultCustomers: List<Customer>? = null
        var errorMessage: String? = null
        viewModel.addCustomer(customer) { customers, error ->
            resultCustomers = customers
            errorMessage = error
        }

        // Wait for the coroutine to complete
        advanceUntilIdle() // Ensures all coroutines finish before assertions

        // Then
        assertNull(resultCustomers) // Verify no customers were returned
        assertEquals("Please enter a valid email address.", errorMessage) // Check if the correct error message was returned
    }

}
