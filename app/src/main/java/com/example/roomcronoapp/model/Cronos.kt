package com.example.roomcronoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//una entidad representa una tabla en base de datos
@Entity(tableName = "cronos")
data class Cronos(
    //definimos una llave primaria
    @PrimaryKey(autoGenerate = true)
    id: Long = 0,
    //column info representa un campo en nuestra tabla
    @ColumnInfo(name = "title")
    title: String,
    @ColumnInfo(name = "crono")
    crono: Long
)

