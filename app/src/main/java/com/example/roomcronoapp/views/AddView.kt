package com.example.roomcronoapp.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomcronoapp.repository.CronosRepository
import com.example.roomcronoapp.state.CronoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CronometroViewModel @Inject constructor(
    private val repository: CronosRepository
) : ViewModel() {

    // Estado del cronómetro
    var state by mutableStateOf(CronoState())
        private set

    // Trabajo actual del cronómetro
    private var cronoJob: Job? = null

    // Tiempo transcurrido en milisegundos
    var tiempo by mutableStateOf(0L)
        private set

    /**
     * Actualiza el título del cronómetro.
     */
    fun onValue(value: String) {
        state = state.copy(title = value)
    }

    /**
     * Inicia el cronómetro.
     */
    fun iniciar() {
        if (!state.cronometroActivo) {
            state = state.copy(cronometroActivo = true)
            iniciarCronometro()
        }
    }

    /**
     * Pausa el cronómetro.
     */
    fun pausar() {
        if (state.cronometroActivo) {
            state = state.copy(
                cronometroActivo = false,
                showSaveButton = true
            )
            detenerCronometro()
        }
    }

    /**
     * Detiene el cronómetro y reinicia el estado.
     */
    fun detener() {
        detenerCronometro()
        tiempo = 0L
        state = state.copy(
            cronometroActivo = false,
            showSaveButton = false,
            showTextField = false,
            title = ""
        )
    }

    /**
     * Muestra el campo de texto para ingresar un título.
     */
    fun showTextField() {
        state = state.copy(showTextField = true)
    }

    /**
     * Obtiene un cronómetro de la base de datos por su ID y actualiza el estado.
     */
    fun getCronoById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCronoById(id).collect { item ->
                tiempo = item.crono
                state = state.copy(title = item.title)
            }
        }
    }

    /**
     * Inicia el trabajo del cronómetro en segundo plano.
     */
    private fun iniciarCronometro() {
        detenerCronometro() // Asegurarse de que no haya trabajos activos.
        cronoJob = viewModelScope.launch {
            while (state.cronometroActivo) {
                delay(1000L) // Espera 1 segundo.
                tiempo += 1000L // Incrementa el tiempo en 1 segundo.
            }
        }
    }


    private fun detenerCronometro() {
        cronoJob?.cancel()
        cronoJob = null
    }
}
