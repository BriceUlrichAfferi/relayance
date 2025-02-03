package com.kirabium.relayance.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCustomerViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    fun addCustomer(customer: Customer, callback: (List<Customer>) -> Unit) = viewModelScope.launch {
        val updatedCustomer = customerRepository.addCustomer(customer) // Returns the customer with new ID
        callback(updatedCustomer) // Here, updatedCustomer should be the full list or just the updated customer
    }
}