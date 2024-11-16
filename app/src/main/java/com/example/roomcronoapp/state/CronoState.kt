package com.example.roomcronoapp.state

//agregamos los atributos que serviran para
//manipular nuestro cronometro
data class CronoState(
    val cronometroActivo : Boolean = false,
    val showSaveButton: Boolean = false,
    val showTextField : Boolean = false,
    val title : String = ""
)
