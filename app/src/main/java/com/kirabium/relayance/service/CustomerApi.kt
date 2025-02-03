package com.kirabium.relayance.service

import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CustomerApi {
   suspend fun getCustomer(): List<Customer>
   suspend fun addCustomer(customer: Customer): Customer // Return the customer with the new ID
}

class CustomerRepository @Inject constructor(
   private val customerApi: CustomerApi
) {
   suspend fun getCustomers(): List<Customer> {
      return customerApi.getCustomer()
   }

   suspend fun addCustomer(customer: Customer): List<Customer> {
      val newCustomer = customerApi.addCustomer(customer) // This should return the updated customer with ID
      return getCustomers() // Fetch the latest list
   }
}