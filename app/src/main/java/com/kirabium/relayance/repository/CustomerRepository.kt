package com.kirabium.relayance.repository

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.service.CustomerApi
import java.util.concurrent.Flow
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val customerApi: CustomerApi
) {
    suspend fun getCustomers(): List<Customer> {
        return customerApi.getCustomer()
    }

    suspend fun addCustomer(customer: Customer): List<Customer> {
        customerApi.addCustomer(customer)
        return getCustomers()
    }
}
