package com.kirabium.relayance

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.repository.CustomerRepository
import com.kirabium.relayance.viewmodels.DetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
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
class DetailsViewModelTest {

    @Mock
    private lateinit var repository: CustomerRepository

    private lateinit var viewModel: DetailsViewModel
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailsViewModel(repository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain() // Reset to the original Main dispatcher
        testScope.cleanupTestCoroutines() // Clean up test coroutines
    }

    @Test
    fun `loadCustomer should set customer when customer is found`() = testScope.runBlockingTest {
        // Given
        val customerId = 1
        val testCustomer = Customer(customerId, "John Doe", "john@example.com", Calendar.getInstance().time)
        `when`(repository.getCustomers()).thenReturn(listOf(testCustomer))

        // When
        viewModel.loadCustomer(customerId)

        // Then
        assertEquals(testCustomer, viewModel.customer.value)
    }

    @Test
    fun `loadCustomer should set customer to null when no customer is found`() = runBlocking {
        // Given
        val customerId = 1
        `when`(repository.getCustomers()).thenReturn(emptyList())

        // When
        viewModel.loadCustomer(customerId)

        // Then
        assertEquals(null, viewModel.customer.value)
    }
}