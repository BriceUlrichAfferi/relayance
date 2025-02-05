package com.kirabium.relayance.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kirabium.relayance.databinding.ActivityAddCustomerBinding
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.viewmodels.AddCustomerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class AddCustomerActivity : AppCompatActivity() {

     lateinit var binding: ActivityAddCustomerBinding
    lateinit var viewModel: AddCustomerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupToolbar()
        viewModel = ViewModelProvider(this).get(AddCustomerViewModel::class.java)
        setupAddCustomerButton()
    }


     fun setupAddCustomerButton() {
        binding.saveFab.setOnClickListener {
            val customer = Customer(
                id = -1, // We'll update this with the actual ID from the backend
                name = binding.nameEditText.text.toString(),
                email = binding.emailEditText.text.toString(),
                createdAt = Date()
            )
            viewModel.addCustomer(customer) { updatedCustomers, error ->
                if (error == null) {
                    Toast.makeText(this, "Customer added successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Show the error message in a Toast
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

     fun setupBinding() {
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}