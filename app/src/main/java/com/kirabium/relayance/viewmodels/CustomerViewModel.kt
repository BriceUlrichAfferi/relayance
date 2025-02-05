package com.kirabium.relayance.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _customers = MutableStateFlow<List<Customer>>(emptyList()) // StateFlow instead of LiveData
    val customers: StateFlow<List<Customer>> = _customers.asStateFlow()
    init {
        loadCustomers()
    }

    fun loadCustomers() = viewModelScope.launch {
        _customers.value = customerRepository.getCustomers()
    }


    fun addCustomer(customer: Customer) = viewModelScope.launch {
        customerRepository.addCustomer(customer)
        _customers.update { it + customer }
    }

}