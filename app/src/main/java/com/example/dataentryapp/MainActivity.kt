package com.example.dataentryapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextWhatsAppNumber: EditText
    private lateinit var spinnerLanguage: Spinner
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextWhatsAppNumber = findViewById(R.id.editTextWhatsAppNumber)
        spinnerLanguage = findViewById(R.id.spinnerLanguage)
        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            submitData()
        }
    }

    private fun submitData() {
        val name = editTextName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val phoneNumber = editTextWhatsAppNumber.text.toString().trim()
        val language = spinnerLanguage.selectedItem.toString()
        val message = editTextMessage.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || language.isEmpty() || message.isEmpty()) {
            Toast.makeText(this@MainActivity, "All fields are required", Toast.LENGTH_LONG).show()
            return
        }

        val url = "https://a9b9-103-249-83-234.ngrok-free.app/sound/"
        val queue: RequestQueue = Volley.newRequestQueue(this)

        val postRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->
                Toast.makeText(this@MainActivity, "Data submitted successfully", Toast.LENGTH_LONG)
                    .show()
                // Clear entry fields
                editTextName.text.clear()
                editTextEmail.text.clear()
                editTextWhatsAppNumber.text.clear()
                editTextMessage.text.clear()
            },
            Response.ErrorListener { error ->
                val errorMessage = error.networkResponse?.data?.let { String(it) } ?: error.message
                Log.e("Volley Error", errorMessage ?: "Unknown error")
                Toast.makeText(
                    this@MainActivity,
                    "Error submitting data: $errorMessage",
                    Toast.LENGTH_LONG
                ).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = name
                params["email"] = email
                params["phone_number"] = phoneNumber
                params["language"] = language
                params["content"] = message
                return params
            }
        }

        queue.add(postRequest)
    }
}