package com.kirabium.relayance.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCustomerViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    fun addCustomer(customer: Customer, callback: (List<Customer>?, String?) -> Unit) {
        if (!isValidEmail(customer.email)) {
            callback(null, "Please enter a valid email address.")
            return
        }

        viewModelScope.launch {
            try {
                val updatedCustomer = customerRepository.addCustomer(customer)
                callback(updatedCustomer, null)
            } catch (e: Exception) {
                callback(null, "An error occurred: ${e.message}")
            }
        }
    }

     fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
