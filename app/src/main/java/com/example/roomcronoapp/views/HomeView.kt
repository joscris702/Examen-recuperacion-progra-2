package com.example.roomcronoapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.roomcronoapp.component.CronCard
import com.example.roomcronoapp.component.FloatButton
import com.example.roomcronoapp.component.MainTitle
import com.example.roomcronoapp.component.formatTiempo
import com.example.roomcronoapp.viewModels.CronosViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

//agregar el cronosVM para enviarlo al contentHomeView
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(cronosVM: CronosViewModel){
    Scaffold(
        topBar = {
            //clase de material que crea una barra en la parte superior
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Crono App") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ))
        },
        floatingActionButton = {
            //componente creado en la clase Botones
            FloatButton {
                navController.navigate("AddView")
            }
        }
    ) {
        ContentHomeView(it, navController, cronosVM )
    }
}

//agregamos el cronosVM como parametro a la funcion
@Composable
fun ContentHomeView(it: PaddingValues, navController: NavController, cronosVM: CronosViewModel){
    Column(
        modifier = Modifier.padding(it)
    ) {
        //creamos una variable con la que obtenemos el estado de la lista de elemementos en room
        val cronosList by cronosVM.cronosList.collectAsState();
        //mostramos lo elementos en una LazyColumn
        LazyColumn {
            items(cronosList){item ->

                val deleteAction = SwipeAction(
                    icon = rememberVectorPainter(Icons.Default.Delete),
                    background = Color.Red,
                    onSwipe = {cronosVM.deleteCrono(item)}
                )
                //endActions nos sirve para ejecutar una accion al arrastrar el box de
                // derecha a izquierda, tenemos un startActions que funciona de izquierda a derecha
                SwipeableActionsBox(endActions = listOf(deleteAction),
                    //nos permite establecer un recorrido minimo para ejecutar la accion
                    swipeThreshold = 150.dp) {
                    CronCard(titulo = item.title, crono = formatTiempo(tiempo = item.crono)) {
                        navController.navigate("EditView/${item.id}")
                    }
                }

            }
        }
    }

}


