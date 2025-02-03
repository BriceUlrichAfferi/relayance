package com.kirabium.relayance.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _customers = MutableLiveData<List<Customer>>()
    val customers: LiveData<List<Customer>> = _customers

    init {
        loadCustomers()
    }

    fun loadCustomers() = viewModelScope.launch {
        _customers.value = customerRepository.getCustomers()
    }

    fun addCustomer(customer: Customer) = viewModelScope.launch {
        customerRepository.addCustomer(customer)
        loadCustomers() // Refresh the list after adding a customer
    }
}