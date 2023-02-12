package com.rosewhat.temperature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.rosewhat.temperature.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            lifecycleScope.launch {
                loadData()
            }

        }
    }
    private suspend fun loadData()  {
        with(binding) {
            progressTemperature.isVisible = true
            buttonLoad.isEnabled = false
            val city = loadCity()
            tvCity.text = city
            val temp = loadTemperature()
            tvTemperature.text = temp.toString()
            progressTemperature.isVisible = false
            buttonLoad.isEnabled = true
        }

    }
    private suspend fun loadCity() : String {
        delay(2000)
        return "Moscow"
    }

    private suspend fun loadTemperature() : Int {
        Toast.makeText(this, LOAD_TEMPERATURE, Toast.LENGTH_SHORT).show()
        delay(2000)
        return 20
    }
    companion object {
        private const val LOAD_TEMPERATURE = "Loading temperature for city: {city}"
    }

}