package com.kirabium.relayance.service

import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.flow.Flow

class CustomerFakeApi : CustomerApi {
    private val customerList = mutableListOf(
        Customer(1, "Alice Wonderland", "alice@example.com", DummyData.generateDate(12)),
        Customer(2, "Bob Builder", "bob@example.com", DummyData.generateDate(6)),
        Customer(3, "Charlie Chocolate", "charlie@example.com", DummyData.generateDate(3)),
        Customer(4, "Diana Dream", "diana@example.com", DummyData.generateDate(1)),
        Customer(5, "Evan Escape", "evan@example.com", DummyData.generateDate(0)),
        )

    override suspend fun getCustomer(): List<Customer> {
       return customerList
    }

   /* override suspend fun addCustomer(customer: Customer): Customer {
        customerList.add(customer)
        return customer
    }*/


    override suspend fun addCustomer(customer: Customer): Customer {
        val newCustomer = customer.copy(id = customerList.size + 1) // Assign a new ID
        customerList.add(newCustomer)
        return newCustomer
    }
}