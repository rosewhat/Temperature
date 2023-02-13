package com.rosewhat.temperature

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.rosewhat.temperature.databinding.ActivityMainBinding
import kotlinx.coroutines.async
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
            binding.progressTemperature.isVisible = true
            binding.buttonLoad.isEnabled = false
            val deferredCity = lifecycleScope.async {
                val city = loadCity()

                city
            }
            val deferredTemp = lifecycleScope.async {
                val temp = loadTemperature()
                temp
            }

            lifecycleScope.launch {
                // пока работа не выполнена, перейдем к следующему объекту
                val city = deferredCity.await()
                val temp = deferredTemp.await()
                binding.tvCity.text = city
                binding.tvTemperature.text = temp.toString()
                Toast.makeText(this@MainActivity, "City: $city Temp: $temp", Toast.LENGTH_SHORT)
                    .show()

                binding.progressTemperature.isVisible = false
                binding.buttonLoad.isEnabled = true
            }

        }
    }

    private suspend fun loadData() {
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

    private suspend fun loadCity(): String {
        delay(2000)
        return "Moscow"
    }

    private suspend fun loadTemperature(): Int {
        delay(2000)
        return 20
    }

    companion object {
        private const val LOAD_TEMPERATURE = "Loading temperature for city: {city}"
    }

}