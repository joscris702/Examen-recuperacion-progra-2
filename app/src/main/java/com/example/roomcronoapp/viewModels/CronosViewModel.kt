package com.example.roomcronoapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomcronoapp.model.Cronos
import com.example.roomcronoapp.repository.CronosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

//la anotacion HiltViewModel nos
// permitira inyectar el constructo
@HiltViewModel
class CronosViewModel @Inject constructor(private val repository: CronosRepository) : ViewModel() {

    private val _cronosList = MutableStateFlow<List<Cronos>>(emptyList())
    val cronosList = _cronosList.asStateFlow()

    //init se ejecuta cuando se crea la instancia del viewMODEL
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllCronos().collect() {item ->
                if (item.isEmpty()){
                    _cronosList.value = emptyList()
                }else{
                    _cronosList.value = item
                }
            }
        }
    }
    //creamos las funciones que invocaran a nuestro repository
    fun addCrono(crono: Cronos) = viewModelScope.launch { repository.addCrono(crono) }
    fun updateCrono(crono: Cronos) = viewModelScope.launch { repository.updateCrono(crono) }
    fun deleteCrono(crono: Cronos) = viewModelScope.launch { repository.deleteCrono(crono) }

}

