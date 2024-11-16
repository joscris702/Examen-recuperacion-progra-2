package com.example.roomcronoapp.repository

import com.example.roomcronoapp.model.Cronos
import com.example.roomcronoapp.room.CronosDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

//declaramos un constructor inyectado, el cual recibira como parametro
//el cronosDataBaseDao que nos proporciona la clase AppModule
class CronosRepository @Inject constructor(private val cronosDataBaseDao: CronosDatabaseDao) {

    //creamos funciones que invocaran a las funciones de nuestra interfaz dao
    suspend fun addCrono(crono: Cronos) = cronosDataBaseDao.insert(crono)
    suspend fun updateCrono(crono: Cronos) = cronosDataBaseDao.update(crono)
    suspend fun deleteCrono(crono: Cronos) = cronosDataBaseDao.delete(crono)

    //esta funcion no es suspend por estar retornando un Flow
    //es importante asignare el dispatcher ya que es una corrutina
    //conflate = combinar los flujos de emision de las corrutinas
    fun getAllCronos():Flow<List<Cronos>> = cronosDataBaseDao.getCronos().flowOn(Dispatchers.IO).conflate()
    fun getCronoById(id :Long):Flow<Cronos> = cronosDataBaseDao.getCronosById(id).flowOn(Dispatchers.IO).conflate()
}

