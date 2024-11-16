package com.example.roomcronoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomcronoapp.model.Cronos

//agregamos las entidades que forman parte de nuestra db
//en este caso solo existe la entidad Cronos
@Database(entities = [Cronos::class], version = 1, exportSchema = false)
//hereda de RoomDataBase()
abstract class CronosDataBase: RoomDatabase() {
    //se define una funcion abstracta que retorne el dao
    abstract fun cronosDao(): CronosDatabaseDao
}

