package com.example.roomcronoapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomcronoapp.model.Cronos
import kotlinx.coroutines.flow.Flow

//observador al acceso de datos
@Dao
interface CronosDatabaseDao {

    //listar cronos
    @Query("Select * from cronos ")
    fun getCronos():Flow<List<Cronos>>

    //buscar un crono por el id
    @Query("SELECT * FROM cronos WHERE id = :id")
    fun getCronosById(id: Long):Flow<Cronos>

    //Crear un crono
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crono: Cronos)

    //actualizar un crono
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(crono: Cronos)

    //borrar un crono
    @Delete
    suspend fun delete(crono: Cronos)

}

